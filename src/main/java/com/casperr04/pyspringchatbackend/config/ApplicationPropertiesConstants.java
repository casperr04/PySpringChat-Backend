package com.casperr04.pyspringchatbackend.config;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@RequiredArgsConstructor
public class ApplicationPropertiesConstants {
    @Value("${bearer_token_expiration_length:84600}")
    private int BEARER_TOKEN_EXPIRATION_LENGTH;

    @Value("${websocket_user_heartbeat:84600}")
    private int WEBSOCKET_USER_HEARTBEAT;

    private String USERNAME_VALIDATION_REGEX = "^[a-z0-9_-]{3,15}$";

}
