package com.casperr04.pyspringchatbackend.service;

public interface WebsocketMessagingHandler {
    /**
     * Service to send events to users subscribed to the event topic in real time.
     *
     * @param message  Message to be sent to the user
     * @param username Username of the user
     * @param event    Type of event
     */
    void sendEventMessage(String message, String username, String event, String author);

    void sendChannelMessage(String message, String username, String eventType, String author, String id);
}