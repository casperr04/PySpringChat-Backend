package com.casperr04.pyspringchatbackend.service.impl;

import com.casperr04.pyspringchatbackend.config.ApplicationPropertiesConstants;
import com.casperr04.pyspringchatbackend.model.entity.AuthToken;
import com.casperr04.pyspringchatbackend.model.entity.UserEntity;
import com.casperr04.pyspringchatbackend.service.BearerTokenService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
@AllArgsConstructor
public class BearerTokenServiceImpl implements BearerTokenService {

    @Autowired
    private ApplicationPropertiesConstants applicationPropertiesConstants;
    @Override
    public AuthToken generateToken(UserEntity user) {
        return AuthToken.builder()
                .token(UUID.randomUUID().toString())
                .expirationDate(Instant.now()
                        .plusSeconds(applicationPropertiesConstants.getBEARER_TOKEN_EXPIRATION_LENGTH()))
                .isValid(true)
                .user(user)
                .build();
    }
}
