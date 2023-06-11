package com.casperr04.pyspringchatbackend.model.dto;

import lombok.Builder;
import lombok.Data;

import java.time.Instant;

@Data
@Builder
public class ChannelCreationResponse {
    private String channelId;
    private Instant dateOfCreation;
}
