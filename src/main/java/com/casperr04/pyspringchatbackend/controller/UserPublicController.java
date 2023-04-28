package com.casperr04.pyspringchatbackend.controller;


import com.casperr04.pyspringchatbackend.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * This controller is for unauthenticated endpoints that server public user information.
 */
@RestController
@AllArgsConstructor
@RequestMapping("/v1/user/info")
public class UserPublicController {

    private final UserService userService;

    @GetMapping("/id/{id}")
    public ResponseEntity<?> getPublicUserInformationById(@PathVariable("id") Long id) {
        var returnDto = userService.receiveUserInfo(id);
        return ResponseEntity.ok(returnDto);
    }

    @GetMapping("/name/{username}")
    public ResponseEntity<?> getPublicUserInformationById(@PathVariable("username") String username) {
        var returnDto = userService.receiveUserInfo(username);
        return ResponseEntity.ok(returnDto);
    }
}
