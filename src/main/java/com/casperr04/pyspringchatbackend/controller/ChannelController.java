package com.casperr04.pyspringchatbackend.controller;

import com.casperr04.pyspringchatbackend.model.dto.ChannelCreationResponse;
import com.casperr04.pyspringchatbackend.service.ChannelService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/v1/channels/")
public class ChannelController {

    private ChannelService channelService;

    @PostMapping("create/private-channel/{username}")
    public ResponseEntity<ChannelCreationResponse> createPrivateChannel(@PathVariable String username) {
        var returnDto = channelService.createPrivateChannel(username);
        return ResponseEntity.ok(returnDto);
    }
}
