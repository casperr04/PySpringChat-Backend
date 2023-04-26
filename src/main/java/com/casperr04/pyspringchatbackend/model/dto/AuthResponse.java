package com.casperr04.pyspringchatbackend.model.dto;

import lombok.Builder;
import lombok.Data;

import java.time.Instant;

@Data
@Builder
public class AuthResponse {
    private Long id;
    private String username;
    private Instant dateOfCreation;
    private String token;
}

