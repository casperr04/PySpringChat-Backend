package com.casperr04.pyspringchatbackend.controller;


import com.casperr04.pyspringchatbackend.model.dto.ExceptionResponseModel;
import com.casperr04.pyspringchatbackend.model.dto.UserPublicDto;
import com.casperr04.pyspringchatbackend.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;

/**
 * This controller is for unauthenticated endpoints that server public user information.
 */
@RestController
@AllArgsConstructor
@RequestMapping("/v1/user/info")
public class UserPublicController {
    private UserService userService;

    @GetMapping("/id/{id}")
    public ResponseEntity<?> getPublicUserInformationById(@PathVariable("id") Long id) {
        UserPublicDto returnDto;
        try {
            returnDto = userService.receiveUserInfo(id);
        } catch (RuntimeException runtimeException) {
            return ResponseEntity.badRequest()
                    .body(new ExceptionResponseModel(runtimeException.getMessage(), Instant.now()));
        }
        return ResponseEntity.ok(returnDto);
    }

    @GetMapping("/name/{username}")
    public ResponseEntity<?> getPublicUserInformationById(@PathVariable("username") String username) {
        UserPublicDto returnDto;
        try {
            returnDto = userService.receiveUserInfo(username);
        } catch (RuntimeException runtimeException) {
            return ResponseEntity.badRequest()
                    .body(new ExceptionResponseModel(runtimeException.getMessage(), Instant.now()));
        }
        return ResponseEntity.ok(returnDto);
    }
}
