package com.casperr04.pyspringchatbackend.model;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.Instant;

/**
 * User POJO for WebsocketSessionUsers
 */
@Data
@Builder
@EqualsAndHashCode
public class WebSocketUser {

    /**
     * Username of a user
     */
    private String username;

    /**
     * Websocket heartbeat, renewed whenever a user sends a message in a topic.
     * Scheduled function will remove the expired objects from the hashmap, so we don't get memory problems.
     */
    private Instant heartbeat;
}
