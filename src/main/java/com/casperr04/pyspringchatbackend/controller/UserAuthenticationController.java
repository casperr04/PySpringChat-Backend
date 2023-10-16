package com.casperr04.pyspringchatbackend.controller;

import com.casperr04.pyspringchatbackend.model.dto.*;
import com.casperr04.pyspringchatbackend.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "user-authentication-controller", description = "These endpoints are public, you do not need to be authenticated to use them.")
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/auth/")
public class UserAuthenticationController {

    private final UserService userService;

    @Operation(summary = "Register user", description = "Registers an user.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Successfully created, returns AuthResponse DTO.",
                    content = @Content(schema = @Schema(implementation = AuthResponse.class))),
            @ApiResponse(responseCode = "400",
                    description = "User already exists",
                    content = @Content(schema = @Schema(implementation = ExceptionResponseModel.class))),
            @ApiResponse(responseCode = "400",
                    description = "Username or password don't fit constraints.",
                    content = @Content(schema = @Schema(implementation = ExceptionResponseModel.class)))})
    @PostMapping("register")
    public ResponseEntity<AuthResponse> register(@RequestBody UserRegisterDto registerDto) {
        var returnDto = userService.registerUser(registerDto);
        return ResponseEntity.ok(returnDto);
    }

    @Operation(summary = "Login", description = "Logs in an user, returning a bearer token.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "User successfully authenticated, returns AuthResponse DTO.",
                    content = @Content(schema = @Schema(implementation = AuthResponse.class))),
            @ApiResponse(responseCode = "400",
                    description = "Invalid credentials.",
                    content = @Content(schema = @Schema(implementation = ExceptionResponseModel.class)))})
    @PostMapping("login")
    public ResponseEntity<AuthResponse> login(@RequestBody UserLoginDto userLoginDto) {
        var returnDto = userService.authenticate(userLoginDto);
        return ResponseEntity.ok(returnDto);
    }


    @PostMapping("refresh")
    public ResponseEntity<AuthResponse> refresh(@RequestBody TokenDto tokenDto) {
        var returnDto = userService.authenticate(tokenDto);
        return ResponseEntity.ok(returnDto);
    }
}
