package com.casperr04.pyspringchatbackend.service.impl;

import com.casperr04.pyspringchatbackend.model.dto.WebsocketMessageDto;
import com.casperr04.pyspringchatbackend.service.WebsocketMessagingHandler;
import lombok.AllArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class WebsocketMessagingHandlerImpl implements WebsocketMessagingHandler {
    private SimpMessagingTemplate messagingTemplate;

    @Override
    public void sendEventMessage(String message, String username, String eventType, String author) {
        LocalDateTime now = LocalDateTime.now();
        WebsocketMessageDto serverEventDto = WebsocketMessageDto.builder()
                .author(author)
                .date(now)
                .eventType(eventType)
                .message(message)
                .build();
//        String preparedMessage = "SERVER" + "\n" +
//                eventType + "\n" +
//                now + "\n" +
//                message;
        messagingTemplate.convertAndSendToUser(username, "/queue/event", serverEventDto);
    }

    public void sendChannelMessage(String message, String username, String eventType, String author, String id) {
        LocalDateTime now = LocalDateTime.now();
        WebsocketMessageDto channelMessageDto = WebsocketMessageDto.builder()
                .author(author)
                .date(now)
                .eventType(eventType)
                .message(message)
                .build();
        messagingTemplate.convertAndSendToUser(username, "/queue/chat/" + id, channelMessageDto);
    }
}
