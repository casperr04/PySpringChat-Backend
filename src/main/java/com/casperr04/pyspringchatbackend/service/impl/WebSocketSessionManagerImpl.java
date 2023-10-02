package com.casperr04.pyspringchatbackend.service.impl;

import com.casperr04.pyspringchatbackend.config.WebsocketSessionUsers;
import com.casperr04.pyspringchatbackend.model.WebSocketUser;
import com.casperr04.pyspringchatbackend.service.WebSocketSessionManager;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@AllArgsConstructor
public class WebSocketSessionManagerImpl implements WebSocketSessionManager {
    private WebsocketSessionUsers websocketSessionUsers;
    @Override
    @Scheduled(timeUnit = TimeUnit.MINUTES, fixedDelay = 30)
    @Async
    public void RemoveEmptyWebSocketChannels() {
        Logger logger = LoggerFactory.getLogger(WebSocketSessionManagerImpl.class);
        AtomicInteger removalCount = new AtomicInteger();
        websocketSessionUsers.getWebSocketChannels().forEach((key, value) -> {
            if (value.isEmpty()) {
                websocketSessionUsers.removeChannelSession(key);
                removalCount.addAndGet(1);
            }
        });
        logger.info("Removed {} empty websocket channels", removalCount.get());
    }

    @SuppressWarnings("WhileLoopReplaceableByForEach")
    @Override
    @Scheduled(timeUnit = TimeUnit.MINUTES, fixedDelay = 30)
    @Async
    public void RemoveIdleWebSocketUsers() {
        Logger logger = LoggerFactory.getLogger(WebSocketSessionManagerImpl.class);
        AtomicInteger removalCount = new AtomicInteger();
        websocketSessionUsers.getWebSocketChannels().forEach((key, value) -> {
            Iterator<WebSocketUser> iter = value.iterator();
            while (iter.hasNext()) {
                WebSocketUser user = iter.next();
                if (user.getHeartbeat().isBefore(Instant.now())) {
                    websocketSessionUsers.removeUserFromChannelSession(key, user.getUsername());
                    removalCount.addAndGet(1);
                }
            }
        });
        logger.info("Removed {} idle websocket users", removalCount.get());
    }
}
