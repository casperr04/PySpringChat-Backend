package com.casperr04.pyspringchatbackend.service;

import com.casperr04.pyspringchatbackend.config.ApplicationPropertiesConstants;
import com.casperr04.pyspringchatbackend.model.entity.AuthToken;
import com.casperr04.pyspringchatbackend.model.entity.UserEntity;
import com.casperr04.pyspringchatbackend.service.impl.BearerTokenServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class BearerTokenServiceImplTest {
    @Spy
    ApplicationPropertiesConstants applicationPropertiesConstants;

    @InjectMocks
    @Autowired
    BearerTokenServiceImpl bearerTokenService;

    @Test
    void GenerateAuthToken_ValidUserPassed_ReturnsAuthToken() {
        UserEntity user = UserEntity.builder()
                .username("Test")
                .encryptedPassword("12345")
                .creationDate(Instant.now())
                .isBanned(false)
                .build();
        AuthToken authToken = bearerTokenService.generateToken(user);
        assertNotNull(authToken);
        assertNotNull(authToken.getToken());
        assertNotNull(authToken.getUser());
    }

    // Validation should be handled by Hibernate Validator, in case user is null
}
