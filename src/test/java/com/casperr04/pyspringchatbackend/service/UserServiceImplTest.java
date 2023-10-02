package com.casperr04.pyspringchatbackend.service;

import com.casperr04.pyspringchatbackend.config.ApplicationPropertiesConstants;
import com.casperr04.pyspringchatbackend.exception.MissingEntityException;
import com.casperr04.pyspringchatbackend.model.dto.AuthResponse;
import com.casperr04.pyspringchatbackend.model.dto.UserLoginDto;
import com.casperr04.pyspringchatbackend.model.dto.UserPublicDto;
import com.casperr04.pyspringchatbackend.model.dto.UserRegisterDto;
import com.casperr04.pyspringchatbackend.model.entity.AuthToken;
import com.casperr04.pyspringchatbackend.model.entity.UserEntity;
import com.casperr04.pyspringchatbackend.model.entity.enums.UserRoles;
import com.casperr04.pyspringchatbackend.repository.AuthTokenRepository;
import com.casperr04.pyspringchatbackend.repository.UserRepository;
import com.casperr04.pyspringchatbackend.service.impl.UserServiceImpl;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.Instant;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;


@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class UserServiceImplTest {
    @Mock
    UserRepository userRepository;

    @Spy
    PasswordEncoder passwordEncoder;

    @Mock
    AuthenticationManager authenticationManager;

    @Mock
    BearerTokenService bearerTokenService;

    @Mock
    AuthTokenRepository authTokenRepository;

    @Spy
    ApplicationPropertiesConstants applicationPropertiesConstants;

    @InjectMocks
    @Autowired
    UserServiceImpl userService;

    UserRegisterDto mockDto = UserRegisterDto.builder()
            .password("123")
            .username("abc")
            .build();

    UserLoginDto mockLoginDto = UserLoginDto.builder()
            .password("123")
            .username("abc")
            .build();

    UserEntity mockUser = UserEntity.builder()
            .username("MyUser")
            .encryptedPassword("ABC123")
            .role(UserRoles.USER)
            .creationDate(Instant.now())
            .build();

    AuthToken mockToken = AuthToken.builder()
            .token("ABC!123")
            .expirationDate(Instant.now().plusSeconds(600))
            .creationDate(Instant.now())
            .user(mockUser)
            .build();

    @Test
    void RegisterUser_NoUsernameInDto_ThrowsIllegalArgumentException(){
        UserRegisterDto invalidDto = UserRegisterDto.builder()
                .password("123")
                .username("")
                .build();
        assertThrows(IllegalArgumentException.class, () -> userService.registerUser(invalidDto));
    }
    @Test
    void RegisterUser_UsernameExistsInDb_ThrowsIllegalArgumentException(){
        when(userRepository.findUserByUsername(anyString())).thenReturn(Optional.of(new UserEntity()));
        assertThrows(IllegalArgumentException.class, () -> userService.registerUser(mockDto));
    }

    @Test
    void RegisterUser_PasswordDoesntFitConstraints_ThrowsConstraintViolationException(){
        when(userRepository.save(any())).thenThrow(ConstraintViolationException.class);
        assertThrows(ConstraintViolationException.class, () -> userService.registerUser(mockDto));
    }

    @Test
    void AuthenticateUser_ValidCredentials_ReturnsAuthResponse() {
        when(userRepository.findUserByUsername(any())).thenReturn(Optional.ofNullable(mockUser));
        when(bearerTokenService.generateToken(any())).thenReturn(mockToken);
        AuthResponse authResponse = userService.authenticate(mockLoginDto);
        assertEquals(authResponse.getUsername(), mockLoginDto.getUsername());
        assertEquals(authResponse.getId(), mockUser.getId());
        assertEquals(authResponse.getToken(), mockToken.getToken());
    }

    @Test
    void AuthenticateUser_NoUserFound_ThrowMissingEntityException(){
        when(userRepository.findUserByUsername(anyString())).thenThrow(MissingEntityException.class);
        assertThrows(MissingEntityException.class, () -> userService.authenticate(mockLoginDto));
    }

    @Test
    void receiveUserInfoByUsername_UserFound_ReturnUserPublicDto(){
        when(userRepository.findUserByUsername(any())).thenReturn(Optional.ofNullable(mockUser));
        mockUser.setId(123L);
        UserPublicDto returnDto = userService.receiveUserInfo(mockUser.getUsername());
        assertEquals(returnDto.getName(), mockUser.getUsername());
    }

    @Test
    void receiveUserInfoById_UserFound_ReturnUserPublicDto(){
        when(userRepository.findById(any())).thenReturn(Optional.ofNullable(mockUser));
        mockUser.setId(123L);
        UserPublicDto returnDto = userService.receiveUserInfo(mockUser.getId());
        assertEquals(returnDto.getName(), mockUser.getUsername());
    }


    @Test
    void receiveUserInfoByUsername_UserNotFound_ThrowMissingEntityException(){
        when(userRepository.findUserByUsername(any())).thenThrow(MissingEntityException.class);
        assertThrows(MissingEntityException.class, () -> userService.receiveUserInfo("ABC123"));
    }
    @Test
    void receiveUserInfoById_UserNotFound_ThrowMissingEntityException(){
        when(userRepository.findById(any())).thenThrow(MissingEntityException.class);
        assertThrows(MissingEntityException.class, () -> userService.receiveUserInfo(123L));
    }
}
