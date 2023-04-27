package com.casperr04.pyspringchatbackend.model.dto;

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
    private Long id;

    @NotBlank
    private String username;

    @PastOrPresent
    private Instant accountDateOfCreation;

    @NotBlank
    private String token;

    @FutureOrPresent
    private Instant tokenExpirationDate;
}

