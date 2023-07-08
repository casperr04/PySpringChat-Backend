package com.casperr04.pyspringchatbackend.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.Builder;
import lombok.Data;

import java.time.Instant;

@Data
@Builder
public class AuthResponse {

    @NotNull
    @Schema(name = "user-id", type = "id", format = "long", description = "ID of the user", example = "1337")
    private Long id;

    @NotBlank
    @Schema(name = "username", type = "username", format = "string", description = "Username of the user", example = "johnsmith2003")
    private String username;

    @PastOrPresent
    @Schema(name = "creation-date", type = "date", format = "date", description = "Account creation date", example = "2023-06-11 16:14:47.518 +0200")
    private Instant accountDateOfCreation;

    @NotBlank
    @Schema(name = "bearer-token", type = "token", format = "string", description = "Bearer token for the account", example = "4c2d64ba-70d5-4ff9-900f-b5853529e97f")
    private String token;

    @FutureOrPresent
    @Schema(name = "token-expiration-date", type = "date", format = "date", description = "Bearer token expration date", example = "2023-06-11 16:14:47.518 +0200")
    private Instant tokenExpirationDate;
}

