package com.casperr04.pyspringchatbackend.service;

import com.casperr04.pyspringchatbackend.exception.MissingEntityException;
import com.casperr04.pyspringchatbackend.model.dto.*;
import org.springframework.security.core.AuthenticationException;

public interface UserService {
    AuthResponse registerUser(UserRegisterDto userRegisterDto) throws IllegalArgumentException;
    AuthResponse authenticate(UserLoginDto userLoginDto) throws IllegalArgumentException, AuthenticationException;

    AuthResponse authenticate(TokenDto token) throws MissingEntityException, AuthenticationException;

    /**
     * @param id - User ID
     * @return Returns public user information by user id
     */
    UserPublicDto receiveUserInfo(Long id) throws MissingEntityException, IllegalArgumentException;

    /**
     * @param username - Username
     * @return Returns public user information by username
     */
    UserPublicDto receiveUserInfo(String username) throws MissingEntityException, IllegalArgumentException;
}
