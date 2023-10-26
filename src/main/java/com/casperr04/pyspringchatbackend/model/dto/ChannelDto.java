package com.casperr04.pyspringchatbackend.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChannelDto {
	private Long channelId;
	private ArrayList<UserPublicDto> usersInChannel;
}
