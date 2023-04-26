package com.casperr04.pyspringchatbackend.service;

import com.casperr04.pyspringchatbackend.model.dto.AuthResponse;
import com.casperr04.pyspringchatbackend.model.dto.UserLoginDto;
import com.casperr04.pyspringchatbackend.model.dto.UserRegisterDto;

public interface UserService {
    AuthResponse registerUser(UserRegisterDto userRegisterDto);
    AuthResponse authenticate(UserLoginDto userLoginDto);
}
