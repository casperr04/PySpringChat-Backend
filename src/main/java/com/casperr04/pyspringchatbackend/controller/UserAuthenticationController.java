package com.casperr04.pyspringchatbackend.controller;

import com.casperr04.pyspringchatbackend.model.dto.AuthResponse;
import com.casperr04.pyspringchatbackend.model.dto.ExceptionResponseModel;
import com.casperr04.pyspringchatbackend.model.dto.UserLoginDto;
import com.casperr04.pyspringchatbackend.model.dto.UserRegisterDto;
import com.casperr04.pyspringchatbackend.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;

@RestController
@AllArgsConstructor
@RequestMapping("/v1/auth/")
public class UserAuthenticationController {
    private final UserService userService;
    @PostMapping("register")
    public ResponseEntity<?> register(@RequestBody UserRegisterDto registerDto) {
        AuthResponse returnDto;
        try {
            returnDto = userService.registerUser(registerDto);
        } catch (RuntimeException runtimeException) {
            return ResponseEntity.badRequest()
                    .body(new ExceptionResponseModel(runtimeException.getMessage(), Instant.now()));
        }
        return ResponseEntity.ok(returnDto);
    }
    @PostMapping("login")
    public ResponseEntity<?> login(@RequestBody UserLoginDto userLoginDto) {
        AuthResponse returnDto;
        try {
            returnDto = userService.authenticate(userLoginDto);
        } catch (RuntimeException runtimeException) {
            return ResponseEntity.badRequest()
                    .body(new ExceptionResponseModel(runtimeException.getMessage(), Instant.now()));
        }
        return ResponseEntity.ok(returnDto);
    }
}
