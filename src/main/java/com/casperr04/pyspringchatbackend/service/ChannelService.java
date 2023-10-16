package com.casperr04.pyspringchatbackend.service;

import com.casperr04.pyspringchatbackend.model.dto.ChannelCreationResponse;
import com.casperr04.pyspringchatbackend.model.dto.MessageInfoDto;

import java.security.Principal;

public interface ChannelService {
    ChannelCreationResponse createPrivateChannel(String username);
    MessageInfoDto retrieveMessageInfo(String channelid, String messageid);
    boolean checkIfUserInChannel(Principal principal, Long id);
}