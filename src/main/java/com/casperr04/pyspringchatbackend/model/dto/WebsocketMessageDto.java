package com.casperr04.pyspringchatbackend.model.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class WebsocketMessageDto {

	private String author;

	private String eventType;

	private LocalDateTime date;

	private String message;
}

