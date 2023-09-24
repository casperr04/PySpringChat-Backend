package com.casperr04.pyspringchatbackend.controller.websocket;

import lombok.AllArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.time.LocalDateTime;

@RestController
@AllArgsConstructor
public class UserEventController {
        private SimpMessagingTemplate messagingTemplate;

        @MessageExceptionHandler(AccessDeniedException.class)
        public void handleAccessDeniedException(AccessDeniedException ex) {
        }
        @MessageMapping("/event")
        public void Event(@Payload(required = false, value = "") String message, Principal principal){

            if(message == null){
                return;
            }

            if(message.equals("PING CHANNEL")){
                LocalDateTime now = LocalDateTime.now();
                String preparedMessage = "SERVER" + "\n" +
                        "N/A" + "\n" +
                        now + "\n" +
                        "pong!";
                messagingTemplate.convertAndSendToUser(principal.getName(), "/queue/event", preparedMessage);
            }
        }
    }

