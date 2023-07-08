package com.casperr04.pyspringchatbackend.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Data
@Builder
public class UserLoginDto {

    @Schema(name = "username", type = "username", format = "string", description = "Username of the user", example = "johnsmith2003")
    private String username;

    @Schema(name = "password", type = "password", format = "string", description = "Password of the user", example = "gAWwek3452Q32!l")
    private String password;
}
