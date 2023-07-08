package com.casperr04.pyspringchatbackend.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.time.Instant;

@Data
@Builder
public class ChannelCreationResponse {

    @Schema(name = "channel-id", type = "id", format = "string", description = "ID of the channel", example = "2137")
    private String channelId;

    @Schema(name = "creation-date", type = "date", format = "date", description = "Channel creation date", example = "2023-06-11 16:14:47.518 +0200")
    private Instant dateOfCreation;
}
