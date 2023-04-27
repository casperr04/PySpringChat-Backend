package com.casperr04.pyspringchatbackend.service.impl;

import com.casperr04.pyspringchatbackend.model.dto.AuthResponse;
import com.casperr04.pyspringchatbackend.model.dto.UserLoginDto;
import com.casperr04.pyspringchatbackend.model.dto.UserPublicDto;
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
        UserEntity savedUser = userRepository.save(user);

        AuthToken authToken = bearerTokenService.generateToken(user);

        return AuthResponse.builder()
                .username(userRegisterDto.getUsername())
                .id(savedUser.getId())
                .accountDateOfCreation(savedUser.getCreationDate())
                .token(authToken.getToken())
                .tokenExpirationDate(authToken.getExpirationDate())
                .build();
    }

    @Override
    public AuthResponse authenticate(UserLoginDto userLoginDto) {
        // Will throw bad credentials exception if password or username is invalid
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        userLoginDto.getUsername(),
                        userLoginDto.getPassword()
                )
        );

        UserEntity user = userRepository.findUserByUsername(userLoginDto.getUsername())
                .orElseThrow(() -> new RuntimeException("No user found"));

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
    public UserPublicDto receiveUserInfo(Long id) {
        if (id == null){
            throw new RuntimeException("Missing ID in request");
        }
        UserEntity user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return UserPublicDto.builder()
                .Id(user.getId())
                .dateOfCreation(user.getCreationDate())
                .name(user.getUsername())
                .build();
    }

    @Override
    public UserPublicDto receiveUserInfo(String username) {
        if (username == null || username.isBlank()){
            throw new RuntimeException("Missing username in request");
        }
        UserEntity user = userRepository.findUserByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return UserPublicDto.builder()
                .Id(user.getId())
                .dateOfCreation(user.getCreationDate())
                .name(user.getUsername())
                .build();
    }
}
