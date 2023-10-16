package com.casperr04.pyspringchatbackend.service.impl;

import com.casperr04.pyspringchatbackend.config.ApplicationPropertiesConstants;
import com.casperr04.pyspringchatbackend.exception.MissingEntityException;
import com.casperr04.pyspringchatbackend.model.dto.*;
import com.casperr04.pyspringchatbackend.model.entity.AuthToken;
import com.casperr04.pyspringchatbackend.model.entity.UserEntity;
import com.casperr04.pyspringchatbackend.model.entity.enums.UserRoles;
import com.casperr04.pyspringchatbackend.repository.AuthTokenRepository;
import com.casperr04.pyspringchatbackend.repository.UserRepository;
import com.casperr04.pyspringchatbackend.service.BearerTokenService;
import com.casperr04.pyspringchatbackend.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final BearerTokenService bearerTokenService;
    private final AuthTokenRepository authTokenRepository;
    private final ApplicationPropertiesConstants applicationPropertiesConstants;

    public AuthResponse registerUser(UserRegisterDto userRegisterDto) throws IllegalArgumentException {
        if (userRegisterDto.getUsername() == null || userRegisterDto.getUsername().isBlank()) {
            throw new IllegalArgumentException("Username empty in request.");
        }
        if (userRepository.findUserByUsername(userRegisterDto.getUsername()).isPresent()) {
            throw new IllegalArgumentException("User already exists by username");
        }

        Pattern pattern = Pattern.compile(applicationPropertiesConstants.getUSERNAME_VALIDATION_REGEX());
        Matcher matcher = pattern.matcher(userRegisterDto.getUsername());
        if(!matcher.find()){
            throw new IllegalArgumentException("Username does not fit constraints");
        }

        UserEntity user = UserEntity.builder()
                .username(userRegisterDto.getUsername())
                .encryptedPassword(passwordEncoder.encode(userRegisterDto.getPassword()))
                .role(UserRoles.USER)
                .build();
        UserEntity savedUser = userRepository.save(user);

        AuthToken authToken = authTokenRepository.save(bearerTokenService.generateToken(user));

        return AuthResponse.builder()
                .username(userRegisterDto.getUsername())
                .id(savedUser.getId())
                .accountDateOfCreation(savedUser.getCreationDate())
                .token(authToken.getToken())
                .tokenExpirationDate(authToken.getExpirationDate())
                .build();
    }

    @Override
    public AuthResponse authenticate(UserLoginDto userLoginDto) throws MissingEntityException, AuthenticationException {
        // Will throw bad credentials exception if password or username is invalid
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        userLoginDto.getUsername(),
                        userLoginDto.getPassword()
                )
        );

        UserEntity user = userRepository.findUserByUsername(userLoginDto.getUsername())
                .orElseThrow(() -> new MissingEntityException("No user found"));

        AuthToken authToken = bearerTokenService.generateToken(user);
        authTokenRepository.save(authToken);

        return AuthResponse.builder()
                .id(user.getId())
                .accountDateOfCreation(user.getCreationDate())
                .username(userLoginDto.getUsername())
                .token(authToken.getToken())
                .tokenExpirationDate(authToken.getExpirationDate())
                .build();
    }

    @Override
    public AuthResponse authenticate(TokenDto token) throws MissingEntityException, AuthenticationException {
        var authToken = authTokenRepository.findAuthTokenByToken(token.getToken()).orElseThrow(() -> new MissingEntityException("Invalid token"));

        if(authToken.getExpirationDate().isBefore(Instant.now())) {
            throw new MissingEntityException("Invalid token");
        }

        UserEntity user = authToken.getUser();

        AuthToken newToken = bearerTokenService.generateToken(user);
        authTokenRepository.save(newToken);

        return AuthResponse.builder()
                .id(user.getId())
                .accountDateOfCreation(user.getCreationDate())
                .username(user.getUsername())
                .token(authToken.getToken())
                .tokenExpirationDate(authToken.getExpirationDate())
                .build();
    }

    @Override
    public UserPublicDto receiveUserInfo(Long id) {
        if (id == null){
            throw new IllegalArgumentException("Missing ID in request");
        }
        UserEntity user = userRepository.findById(id)
                .orElseThrow(() -> new MissingEntityException("User not found"));

        return UserPublicDto.builder()
                .Id(user.getId())
                .dateOfCreation(user.getCreationDate())
                .name(user.getUsername())
                .build();
    }

    @Override
    public UserPublicDto receiveUserInfo(String username) {
        if (username == null || username.isBlank()){
            throw new IllegalArgumentException("Missing username in request");
        }
        UserEntity user = userRepository.findUserByUsername(username)
                .orElseThrow(() -> new MissingEntityException("User not found"));

        return UserPublicDto.builder()
                .Id(user.getId())
                .dateOfCreation(user.getCreationDate())
                .name(user.getUsername())
                .build();
    }
}
