package com.casperr04.pyspringchatbackend.model.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Data
public class UserRegisterDto {
    private String username;
    private String password;
}
