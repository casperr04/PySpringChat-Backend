package com.casperr04.pyspringchatbackend.service;

import com.casperr04.pyspringchatbackend.model.dto.ChannelCreationResponse;
import com.casperr04.pyspringchatbackend.model.dto.MessageInfoDto;

public interface ChannelService {
    ChannelCreationResponse createPrivateChannel(String username);
    MessageInfoDto retrieveMessageInfo(String channelid, String messageid);
}
