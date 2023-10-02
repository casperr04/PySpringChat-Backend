package com.casperr04.pyspringchatbackend.config;

import com.casperr04.pyspringchatbackend.repository.PrivateChannelRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.stereotype.Service;

import java.util.Objects;

@SuppressWarnings("NullableProblems")
@Service
@AllArgsConstructor
public class TopicSubscriptionInterceptor implements ChannelInterceptor {
    private final Logger logger = LoggerFactory.getLogger(TopicSubscriptionInterceptor.class);

    private WebsocketSessionUsers websocketSessionUsers;

    private PrivateChannelRepository privateChannelRepository;

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {

        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(message);

        if (StompCommand.CONNECT.equals(headerAccessor.getCommand())) {
            return message;
        }

        if (StompCommand.SUBSCRIBE.equals(headerAccessor.getCommand()) || StompCommand.SEND.equals(headerAccessor.getCommand())) {
            String destinationId = Objects.requireNonNull(headerAccessor.getNativeHeader("channel")).get(0);
            String destinationUrl = headerAccessor.getDestination();
            String username = Objects.requireNonNull(headerAccessor.getUser()).getName();
            assert destinationUrl != null;
            if(StompCommand.SUBSCRIBE.equals(headerAccessor.getCommand()) && !destinationUrl.contains("/user/" + username)){
                    logger.debug(("User {} not subscribing to authenticated username ( {} )"), username, destinationUrl);
                    throw new IllegalArgumentException("User not subscribing to authenticated username");
            }

            if(!validateSubscription(username, destinationId, destinationUrl)){
                logger.debug("User {} not eligible to subscribe to topic", username);
                throw new IllegalArgumentException("User not eligible to subscribe to topic");
            }

            logger.info(headerAccessor.getDestination());
            setWebsocketSessionUsers(username, destinationId);
        }
        return message;
    }

    private boolean validateSubscription(String username, String topicID, String topicUrl) {
        if(topicUrl.endsWith("event")){
            return true;
        }

        if (username == null) {
            logger.debug("Unauthenticated user tried to subscribe to topic");
            return false;
        }

        var privateMessageChannelEntity = privateChannelRepository.findChannelEntityById(Long.valueOf(topicID));
        if(privateMessageChannelEntity == null){
            logger.debug("No channel found for {}, {} ", username, topicID);
            return false;
        }

        return (Objects.equals(privateMessageChannelEntity.getRecipient().getUsername(), username)
                || Objects.equals(privateMessageChannelEntity.getSender().getUsername(), username));
    }

    private void setWebsocketSessionUsers(String username, String url){
        if (websocketSessionUsers.checkChannelSessionExists(url)) {
            websocketSessionUsers.addUserToChannelSession(url, username);
        } else {
            websocketSessionUsers.createChannelSession(url, username);
        }
    }
}