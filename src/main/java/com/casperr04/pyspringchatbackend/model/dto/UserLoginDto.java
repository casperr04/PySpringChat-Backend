package com.casperr04.pyspringchatbackend.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Data
@Builder
public class UserLoginDto {
    private String username;
    private String password;
}
