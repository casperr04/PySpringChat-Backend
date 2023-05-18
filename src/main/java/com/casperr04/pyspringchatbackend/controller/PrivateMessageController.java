package com.casperr04.pyspringchatbackend.controller;

import com.casperr04.pyspringchatbackend.config.WebsocketSessionUsers;
import com.casperr04.pyspringchatbackend.model.WebSocketUser;
import lombok.AllArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class PrivateMessageController {

    private SimpMessagingTemplate messagingTemplate;
    private WebsocketSessionUsers websocketSessionUsers;
    @MessageExceptionHandler(AccessDeniedException.class)
    public void handleAccessDeniedException(AccessDeniedException ex) {
    }
    @MessageMapping("/chat/{id}")
    public void Greetings(@Payload String message, @DestinationVariable String id){
        for (WebSocketUser user: websocketSessionUsers.getUserListFromChannel(id)) {
            messagingTemplate.convertAndSendToUser(user.getUsername(), "/queue/chat/" + id, message + " RECEIVED!");
        }
    }
}
