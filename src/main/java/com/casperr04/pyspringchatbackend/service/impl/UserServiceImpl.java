package com.casperr04.pyspringchatbackend.service.impl;

import com.casperr04.pyspringchatbackend.model.dto.AuthResponse;
import com.casperr04.pyspringchatbackend.model.dto.UserLoginDto;
import com.casperr04.pyspringchatbackend.model.dto.UserRegisterDto;
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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private AuthenticationManager authenticationManager;
    private BearerTokenService bearerTokenService;
    private AuthTokenRepository authTokenRepository;

    public AuthResponse registerUser(UserRegisterDto userRegisterDto) throws RuntimeException {
        if (userRegisterDto.getUsername() == null || userRegisterDto.getUsername().isBlank()) {
            throw new RuntimeException("Username empty in request.");
        }
        if (userRepository.findUserByUsername(userRegisterDto.getUsername()).isPresent()) {
            throw new RuntimeException("User already exists by username");
        }

        UserEntity user = UserEntity.builder()
                .username(userRegisterDto.getUsername())
                .encryptedPassword(passwordEncoder.encode(userRegisterDto.getPassword()))
                .role(UserRoles.USER)
                .build();
        AuthToken authToken = bearerTokenService.generateToken(user);
        authTokenRepository.save(authToken);
        return AuthResponse.builder()
                .username(userRegisterDto.getUsername())
                .id(userRepository
                        .findUserByUsername(userRegisterDto.getUsername()).get().getId())
                .dateOfCreation(userRepository
                        .findUserByUsername(userRegisterDto.getUsername()).get().getCreationDate())
                .token(authToken.getToken())
                .build();
    }

    @Override
    public AuthResponse authenticate(UserLoginDto userLoginDto) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        userLoginDto.getUsername(),
                        userLoginDto.getPassword()
                )
        );
        UserEntity user = userRepository.findUserByUsername(userLoginDto.getUsername())
                .orElseThrow(() -> new RuntimeException("No user found"));
        AuthToken token = bearerTokenService.generateToken(user);
        authTokenRepository.save(token);
        return AuthResponse.builder()
                .id(user.getId())
                .dateOfCreation(user.getCreationDate())
                .username(userLoginDto.getUsername())
                .token(token.getToken())
                .build();
    }
}