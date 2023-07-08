package com.casperr04.pyspringchatbackend.controller;

import com.casperr04.pyspringchatbackend.config.WebsocketSessionUsers;
import com.casperr04.pyspringchatbackend.model.WebSocketUser;
import com.casperr04.pyspringchatbackend.model.entity.PrivateMessageEntity;
import com.casperr04.pyspringchatbackend.model.entity.UserEntity;
import com.casperr04.pyspringchatbackend.repository.PrivateChannelRepository;
import com.casperr04.pyspringchatbackend.repository.PrivateMessageEntityRepository;
import com.casperr04.pyspringchatbackend.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.time.Instant;

@RestController
@AllArgsConstructor
public class PrivateMessageController {

    private SimpMessagingTemplate messagingTemplate;
    private WebsocketSessionUsers websocketSessionUsers;
    private PrivateChannelRepository privateChannelRepository;
    private UserRepository userRepository;
    private final PrivateMessageEntityRepository privateMessageEntityRepository;

    @MessageExceptionHandler(AccessDeniedException.class)
    public void handleAccessDeniedException(AccessDeniedException ex) {
    }
    @MessageMapping("/chat/{id}")
    public void Greetings(@Payload(required = false, value = "") String message, @DestinationVariable String id, Principal principal){
        UserEntity userEntity = userRepository.findUserByUsername(principal.getName()).get();

        if(message == null){
            return;
        }

        String username = userEntity.getUsername();
        var channel = privateChannelRepository.findChannelEntityById(Long.valueOf(id));

        PrivateMessageEntity privateMessage = PrivateMessageEntity.builder()
                .user(userEntity)
                .channel(channel)
                .message(message)
                .dateOfCreation(Instant.now())
                .build();

        var savedMessage = privateMessageEntityRepository.save(privateMessage);

        String preparedMessage = "MSG-ID: " + savedMessage.getId() + "\n" +
                "DATE: " + privateMessage.getDateOfCreation() + "\n" +
                username + " SENT: " + message;


        for (WebSocketUser user: websocketSessionUsers.getUserListFromChannel(id)) {
            messagingTemplate.convertAndSendToUser(user.getUsername(), "/queue/chat/" + id, preparedMessage);
        }
    }
}
