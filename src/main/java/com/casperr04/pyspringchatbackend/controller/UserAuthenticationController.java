package com.casperr04.pyspringchatbackend.controller;

import com.casperr04.pyspringchatbackend.model.dto.UserLoginDto;
import com.casperr04.pyspringchatbackend.model.dto.UserRegisterDto;
import com.casperr04.pyspringchatbackend.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.security.sasl.AuthenticationException;

@RestController
@AllArgsConstructor
@RequestMapping("/v1/auth/")
public class UserAuthenticationController {

    private final UserService userService;

    @PostMapping("register")
    public ResponseEntity<?> register(@RequestBody UserRegisterDto registerDto) {
        var returnDto = userService.registerUser(registerDto);
        return ResponseEntity.ok(returnDto);
    }
    @PostMapping("login")
    public ResponseEntity<?> login(@RequestBody UserLoginDto userLoginDto) throws AuthenticationException {
        var returnDto = userService.authenticate(userLoginDto);
        return ResponseEntity.ok(returnDto);
    }
}
