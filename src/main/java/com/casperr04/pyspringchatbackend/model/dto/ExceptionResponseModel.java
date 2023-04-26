package com.casperr04.pyspringchatbackend.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExceptionResponseModel {
    private String message;
    private Instant timestamp;
}
