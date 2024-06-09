package com.casperr04.pyspringchatbackend.controller;

import com.casperr04.pyspringchatbackend.model.dto.ChannelCreationResponse;
import com.casperr04.pyspringchatbackend.model.dto.ExceptionResponseModel;
import com.casperr04.pyspringchatbackend.model.dto.MessageInfoDto;
import com.casperr04.pyspringchatbackend.service.ChannelService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;

@RestController
@AllArgsConstructor
@RequestMapping("/v1/channels/")
public class ChannelController {

    private ChannelService channelService;

    @Operation(summary = "Create private channel", description = "Creates a private channel with a friended user.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Successfully created, returns ChannelCreationResponse DTO.",
                    content = @Content(schema = @Schema(implementation = ChannelCreationResponse.class))),
            @ApiResponse(responseCode = "400",
                    description = "User is not friended or found",
                    content = @Content(schema = @Schema(implementation = ExceptionResponseModel.class)))})
    @PostMapping("/private-channel/create/{username}")
    public ResponseEntity<ChannelCreationResponse> createPrivateChannel(@Parameter(description = "Username of friended user", required = true) @PathVariable String username) {
        var returnDto = channelService.createPrivateChannel(username);
        return ResponseEntity.ok(returnDto);
    }

    @Operation(summary = "Get message from private channel", description = "Returns information about a message given a channel and message id.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Found, returns MessageInfoDto.",
                    content = @Content(schema = @Schema(implementation = MessageInfoDto.class))),
            @ApiResponse(responseCode = "400",
                    description = "Request not found or user is not in channel.",
                    content = @Content(schema = @Schema(implementation = ExceptionResponseModel.class)))})
    @GetMapping("/private-channel/message/{channelid}/{messageid}")
    public ResponseEntity<MessageInfoDto> getMessageInfo(@Parameter(description = "Channel ID", required = true) @PathVariable String channelid, @Parameter(description = "Message ID", required = true) @PathVariable String messageid) {
        var returnDto = channelService.retrieveMessageInfo(channelid, messageid);
        return ResponseEntity.ok(returnDto);
    }

    @GetMapping("/private-channel/check/{channelid}")
    public ResponseEntity<Void> checkIfInChannel(Principal principal, @PathVariable Long channelid){
        if(channelService.checkIfUserInChannel(principal, channelid)){
            return ResponseEntity.ok().build();
        } else return ResponseEntity.notFound().build();
    }

    @GetMapping("/private-channel")
    public ResponseEntity<ArrayList<Object>> getPrivateChannels(Principal principal){
        var channelDtos = channelService.getPrivateChannels(principal);
        if(!channelDtos.isEmpty()){
            return ResponseEntity.ok().body(channelDtos);
        } else return ResponseEntity.notFound().build();
    }
}
