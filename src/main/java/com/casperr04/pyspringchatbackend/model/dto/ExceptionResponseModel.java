package com.casperr04.pyspringchatbackend.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExceptionResponseModel {

    @Schema(name = "message", type = "message", format = "string", description = "Error message", example = "Username does not fit constraints")
    private String message;

    @Schema(name = "timestamp", type = "date", format = "date", description = "Error timestamp", example = "2023-06-11 16:14:47.518 +0200")
    private Instant timestamp;
}
