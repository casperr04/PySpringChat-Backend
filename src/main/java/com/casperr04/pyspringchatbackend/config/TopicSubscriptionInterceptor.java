package com.casperr04.pyspringchatbackend.config;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.Objects;

@SuppressWarnings("NullableProblems")
@Service
@AllArgsConstructor
public class TopicSubscriptionInterceptor implements ChannelInterceptor {
    private final Logger logger = LoggerFactory.getLogger(TopicSubscriptionInterceptor.class);

    private WebsocketSessionUsers websocketSessionUsers;

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(message);

        if (StompCommand.CONNECT.equals(headerAccessor.getCommand())) {
            return message;
        }

        String destinationUrl = Objects.requireNonNull(headerAccessor.getNativeHeader("channel")).get(0);

        if (StompCommand.SUBSCRIBE.equals(headerAccessor.getCommand())) {
            Principal userPrincipal = headerAccessor.getUser();
            if (!validateSubscription(userPrincipal, headerAccessor.getDestination())) {
                throw new IllegalArgumentException("No permission for this topic");
            }
        }
        if (StompCommand.SEND.equals(headerAccessor.getCommand())) {
            return message;
        }
        String username = Objects.requireNonNull(headerAccessor.getUser()).getName();
        if(websocketSessionUsers.checkChannelSessionExists(destinationUrl)) {
            websocketSessionUsers.addUserToChannelSession(
                    destinationUrl,
                    username);
        } else {
            websocketSessionUsers.createChannelSession(destinationUrl, headerAccessor.getUser().getName());
        }
        return message;
    }

    private boolean validateSubscription(Principal principal, String topicDestination) {
        if (principal == null) {
            logger.debug("User is unauthenticated");
            return false;
        }
        logger.debug("Validate subscription for {} to topic {}", principal.getName(), topicDestination);
        // Additional validation logic coming here
        return true;
    }
}