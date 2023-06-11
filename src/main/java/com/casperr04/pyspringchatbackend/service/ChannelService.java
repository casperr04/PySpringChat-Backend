package com.casperr04.pyspringchatbackend.service;

import com.casperr04.pyspringchatbackend.model.dto.ChannelCreationResponse;

public interface ChannelService {
    ChannelCreationResponse createPrivateChannel(String username);
}
