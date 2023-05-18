package com.casperr04.pyspringchatbackend.service;


/**
 * Manages Websocket Channels, removing empty sessions and inactive users from session
 */
public interface WebSocketSessionManager {
    void RemoveEmptyWebSocketChannels();
    void RemoveIdleWebSocketUsers();
}
