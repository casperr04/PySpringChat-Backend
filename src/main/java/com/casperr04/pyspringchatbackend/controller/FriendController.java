package com.casperr04.pyspringchatbackend.controller;

import com.casperr04.pyspringchatbackend.model.dto.ExceptionResponseModel;
import com.casperr04.pyspringchatbackend.service.FriendService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/v1/friend/")
public class FriendController {
    private FriendService friendService;

    @Operation(summary = "Send a friend request", description = "Sends a friend request to a given user.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Sent a friend request to given user"),
            @ApiResponse(responseCode = "400",
                    description = "User hasn't been found",
                    content = @Content(schema = @Schema(implementation = ExceptionResponseModel.class)))})
    @PostMapping("/request/{username}")
    public ResponseEntity<String> sendFriendRequest(@Parameter(description = "Username of user", required = true) @PathVariable String username) {
        friendService.sendFriendRequest(username);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Accept friend request", description = "Accepts a friend request.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Friend request accepted."),
            @ApiResponse(responseCode = "400",
                    description = "User or friend request not found",
                    content = @Content(schema = @Schema(implementation = ExceptionResponseModel.class)))})
    @PostMapping("/accept/{username}")
    public ResponseEntity<String> acceptFriendRequest(@Parameter(description = "Username of user", required = true) @PathVariable String username) {
        friendService.acceptFriendRequest(username);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Remove friend", description = "Removes a friended user.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "User removed from friends."),
            @ApiResponse(responseCode = "400",
                    description = "User is not friended or found",
                    content = @Content(schema = @Schema(implementation = ExceptionResponseModel.class)))})
    @PostMapping("/remove/{username}")
    public ResponseEntity<String> removeFriend(@Parameter(description = "Username of friended user", required = true) @PathVariable String username) {
        friendService.removeFriend(username);
        return ResponseEntity.ok().build();
    }
}
