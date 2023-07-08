package com.casperr04.pyspringchatbackend.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.time.Instant;

@Data
@Builder
public class UserPublicDto {

    @Schema(name = "user-id", type = "id", format = "long", description = "ID of the user", example = "1337")
    private long Id;

    @Schema(name = "username", type = "username", format = "string", description = "Username of the user", example = "johnsmith2003")
    private String name;

    @Schema(name = "creation-date", type = "date", format = "date", description = "Account creation date", example = "2023-06-11 16:14:47.518 +0200")
    private Instant dateOfCreation;
}
