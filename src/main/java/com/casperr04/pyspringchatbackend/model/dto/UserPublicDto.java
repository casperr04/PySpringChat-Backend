package com.casperr04.pyspringchatbackend.model.dto;

import lombok.Builder;
import lombok.Data;

import java.time.Instant;

@Data
@Builder
public class UserPublicDto {
    private long Id;
    private String name;
    private Instant dateOfCreation;
}
