package com.casperr04.pyspringchatbackend.config;

import com.casperr04.pyspringchatbackend.model.WebSocketUser;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;


/**
 * Stores current users connected to a channel in a websocket.
 * Used to keep track of which users should get messages from channels,
 * This implementation instead of regular @SendTo allows for kicking or deafening users from channels
 */
@ComponentScan
@Component
@AllArgsConstructor
public class WebsocketSessionUsers {
    private ApplicationPropertiesConstants constants;
    private final ConcurrentHashMap<String, ArrayList<WebSocketUser>> sessions = new ConcurrentHashMap<>();

    /**
     * Creates channel session to store users.
     * @param channel Channel ID
     * @param usernames Usernames of users in channel
     */
    public void createChannelSession(String channel, List<String> usernames) {
        ArrayList<WebSocketUser> webSocketUsers = new ArrayList<>();
        for(String username : usernames) {
            webSocketUsers.add(WebSocketUser.builder()
                    .username(username)
                    .heartbeat(Instant.now().plusSeconds(constants.getWEBSOCKET_USER_HEARTBEAT()))
                    .build());
        }
        sessions.put(channel, webSocketUsers);
    }

    /**
     * Creates channel session to store users.
     * @param channel Channel ID
     * @param username Username of user in channel
     */
    public void createChannelSession(String channel, String username) {
        ArrayList<WebSocketUser> users = new ArrayList<>();
        users.add(WebSocketUser.builder()
                .username(username)
                .heartbeat(Instant.now().plusSeconds(constants.getWEBSOCKET_USER_HEARTBEAT()))
                .build());
        sessions.put(channel, users);
    }

    /**
     * Adds a user to channel, updates heartbeat if user is already in it.
     * @param channel  Channel ID
     * @param username Username of user to add
     * @throws NullPointerException if usernames or channel is null
     */
    public void addUserToChannelSession (String channel, String username) throws NullPointerException {
        ArrayList<WebSocketUser> users = sessions.get(channel);
        users.removeIf(user -> user.getUsername().equals(username));
        WebSocketUser newUser = WebSocketUser.builder()
                .username(username)
                .heartbeat(Instant.now().plusSeconds(constants.getWEBSOCKET_USER_HEARTBEAT()))
                .build();
        users.add(newUser);
    }

    /**
     * Removes channel
     * @param channel Channel ID
     */
    public void removeChannelSession(String channel) {
        sessions.remove(channel);
    }

    /**
     * @param channel Channel ID
     * @param username Username of user to remove
     * @throws NullPointerException if channel or username is null
     */
    public void removeUserFromChannelSession(String channel, String username) throws NullPointerException {
        sessions.get(channel).removeIf(user -> user.getUsername().equals(username));
    }

    /**
     * @param channel Channel ID
     * @return Users from channel, null if channel not found.
     */
    public List<WebSocketUser> getUserListFromChannel(String channel) {
        return sessions.get(channel);
    }

    /**
     * @param channel Channel ID
     * @return true if session exists
     */
    public boolean checkChannelSessionExists(String channel) {
        return sessions.containsKey(channel);
    }

    /**
     * Returns the session HashMap
     *
     * @return Sessions HashMap
     */
    public ConcurrentMap<String, ArrayList<WebSocketUser>> getWebSocketChannels() {
        return this.sessions;
    }
}

