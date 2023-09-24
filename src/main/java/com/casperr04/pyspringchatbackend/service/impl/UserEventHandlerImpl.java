package com.casperr04.pyspringchatbackend.service.impl;

import com.casperr04.pyspringchatbackend.service.UserEventHandler;
import lombok.AllArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class UserEventHandlerImpl implements UserEventHandler {
    private SimpMessagingTemplate messagingTemplate;

    @Override
    public void sendEventMessage(String message, String username, String eventType) {
        LocalDateTime now = LocalDateTime.now();
        String preparedMessage = "SERVER" + "\n" +
                eventType + "\n" +
                now + "\n" +
                message;
        messagingTemplate.convertAndSendToUser(username, "/queue/event", preparedMessage);
    }
}
