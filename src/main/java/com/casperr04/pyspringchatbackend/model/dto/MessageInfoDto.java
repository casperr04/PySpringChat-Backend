package com.casperr04.pyspringchatbackend.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.time.Instant;

@Data
@Builder
public class MessageInfoDto {

    @Schema(name = "username", type = "username", format = "string", description = "Username of the user", example = "johndoe2003")
    private String username;

    @Schema(name = "message-id", type = "id", format = "long", description = "ID of the message", example = "4214")
    private String id;

    @Schema(name = "creation-date", type = "date", format = "date", description = "Message creation date", example = "2023-06-11 16:14:47.518 +0200")
    private Instant dateOfCreation;

    @Schema(name = "message", type = "message", format = "string", description = "Message string", example = "Hello world!")
    private String message;
}
