package com.casperr04.pyspringchatbackend.service;

public interface UserEventHandler {
    /** Service to send events to users subscribed to the event topic in real time.
     *
     * @param message Message to be sent to the user
     * @param username Username of the user
     * @param event Type of event
     */
    void sendEventMessage(String message, String username, String event);
}
