package com.casperr04.pyspringchatbackend.controller;

import com.casperr04.pyspringchatbackend.model.dto.AuthResponse;
import com.casperr04.pyspringchatbackend.model.dto.ExceptionResponseModel;
import com.casperr04.pyspringchatbackend.model.dto.UserLoginDto;
import com.casperr04.pyspringchatbackend.model.dto.UserRegisterDto;
import com.casperr04.pyspringchatbackend.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "user-authentication-controller", description = "These endpoints are public, you do not need to be authenticated to use them.")
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/auth/")
public class UserAuthenticationController {

    private final UserService userService;

    @Operation(summary = "Register user", description = "Registers an user.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Successfully created, returns AuthResponse DTO.",
                    content = @Content(schema = @Schema(implementation = AuthResponse.class))),
            @ApiResponse(responseCode = "400",
                    description = "User already exists {\"openapi\":\"3.0.1\",\"info\":{\"title\":\"OpenAPI definition\",\"version\":\"v0\"},\"servers\":[{\"url\":\"http://localhost:8080\",\"description\":\"Generated server url\"}],\"paths\":{\"/v1/friend/request/send/{username}\":{\"post\":{\"tags\":[\"friend-controller\"],\"summary\":\"Send a friend request\",\"description\":\"Sends a friend request to a given user.\",\"operationId\":\"sendFriendRequest\",\"parameters\":[{\"name\":\"username\",\"in\":\"path\",\"required\":true,\"schema\":{\"type\":\"string\"}}],\"responses\":{\"200\":{\"description\":\"Sent a friend request to given user\",\"content\":{\"*/*\":{\"schema\":{\"type\":\"string\"}}}},\"400\":{\"description\":\"User hasn't been found\",\"content\":{\"*/*\":{\"schema\":{\"$ref\":\"#/components/schemas/ExceptionResponseModel\"}}}}}}},\"/v1/friend/request/accept/{username}\":{\"post\":{\"tags\":[\"friend-controller\"],\"summary\":\"Accept friend request\",\"description\":\"Accepts a friend request.\",\"operationId\":\"acceptFriendRequest\",\"parameters\":[{\"name\":\"username\",\"in\":\"path\",\"required\":true,\"schema\":{\"type\":\"string\"}}],\"responses\":{\"200\":{\"description\":\"Friend request accepted.\",\"content\":{\"*/*\":{\"schema\":{\"type\":\"string\"}}}},\"400\":{\"description\":\"User or friend request not found\",\"content\":{\"*/*\":{\"schema\":{\"$ref\":\"#/components/schemas/ExceptionResponseModel\"}}}}}}},\"/v1/friend/remove/{username}\":{\"post\":{\"tags\":[\"friend-controller\"],\"summary\":\"Remove friend\",\"description\":\"Removes a friended user.\",\"operationId\":\"removeFriend\",\"parameters\":[{\"name\":\"username\",\"in\":\"path\",\"required\":true,\"schema\":{\"type\":\"string\"}}],\"responses\":{\"400\":{\"description\":\"User is not friended or found\",\"content\":{\"*/*\":{\"schema\":{\"$ref\":\"#/components/schemas/ExceptionResponseModel\"}}}},\"200\":{\"description\":\"User removed from friends.\",\"content\":{\"*/*\":{\"schema\":{\"type\":\"string\"}}}}}}},\"/v1/channels/create/private-channel/{username}\":{\"post\":{\"tags\":[\"channel-controller\"],\"summary\":\"Create private channel\",\"description\":\"Creates a private channel with a friended user.\",\"operationId\":\"createPrivateChannel\",\"parameters\":[{\"name\":\"username\",\"in\":\"path\",\"required\":true,\"schema\":{\"type\":\"string\"}}],\"responses\":{\"200\":{\"description\":\"Successfully created, returns ChannelCreationResponse DTO.\",\"content\":{\"*/*\":{\"schema\":{\"$ref\":\"#/components/schemas/ChannelCreationResponse\"}}}},\"400\":{\"description\":\"User is not friended or found\",\"content\":{\"*/*\":{\"schema\":{\"$ref\":\"#/components/schemas/ExceptionResponseModel\"}}}}}}},\"/v1/auth/register\":{\"post\":{\"tags\":[\"user-authentication-controller\"],\"summary\":\"Register user\",\"description\":\"Registers an user.\",\"operationId\":\"register\",\"requestBody\":{\"content\":{\"application/json\":{\"schema\":{\"$ref\":\"#/components/schemas/UserRegisterDto\"}}},\"required\":true},\"responses\":{\"200\":{\"description\":\"Successfully created, returns AuthResponse DTO.\",\"content\":{\"*/*\":{\"schema\":{\"$ref\":\"#/components/schemas/AuthResponse\"}}}},\"400\":{\"description\":\"User already exists\",\"content\":{\"*/*\":{\"schema\":{\"$ref\":\"#/components/schemas/ExceptionResponseModel\"}}}}}}},\"/v1/auth/login\":{\"post\":{\"tags\":[\"user-authentication-controller\"],\"summary\":\"Login\",\"description\":\"Logs in an user, returning a bearer token.\",\"operationId\":\"login\",\"requestBody\":{\"content\":{\"application/json\":{\"schema\":{\"$ref\":\"#/components/schemas/UserLoginDto\"}}},\"required\":true},\"responses\":{\"200\":{\"description\":\"User successfully authenticated, returns AuthResponse DTO.\",\"content\":{\"*/*\":{\"schema\":{\"$ref\":\"#/components/schemas/AuthResponse\"}}}},\"400\":{\"description\":\"Invalid credentials.\",\"content\":{\"*/*\":{\"schema\":{\"$ref\":\"#/components/schemas/ExceptionResponseModel\"}}}}}}},\"/v1/user/info/name/{username}\":{\"get\":{\"tags\":[\"user-public-controller\"],\"summary\":\"Get user information by username.\",\"description\":\"Gets public information about an user. This endpoint is public.\",\"operationId\":\"getPublicUserInformationById\",\"parameters\":[{\"name\":\"username\",\"in\":\"path\",\"required\":true,\"schema\":{\"type\":\"string\"}}],\"responses\":{\"200\":{\"description\":\"User found, returns UserPublicDto.\",\"content\":{\"*/*\":{\"schema\":{\"$ref\":\"#/components/schemas/UserPublicDto\"}}}},\"400\":{\"description\":\"User not found.\",\"content\":{\"*/*\":{\"schema\":{\"$ref\":\"#/components/schemas/ExceptionResponseModel\"}}}}}}},\"/v1/user/info/id/{id}\":{\"get\":{\"tags\":[\"user-public-controller\"],\"summary\":\"Get user information by ID\",\"description\":\"Gets public information about an user. This endpoint is public.\",\"operationId\":\"getPublicUserInformationById_1\",\"parameters\":[{\"name\":\"id\",\"in\":\"path\",\"required\":true,\"schema\":{\"type\":\"integer\",\"format\":\"int64\"}}],\"responses\":{\"200\":{\"description\":\"User found, returns UserPublicDto.\",\"content\":{\"*/*\":{\"schema\":{\"$ref\":\"#/components/schemas/UserPublicDto\"}}}},\"400\":{\"description\":\"User not found.\",\"content\":{\"*/*\":{\"schema\":{\"$ref\":\"#/components/schemas/ExceptionResponseModel\"}}}}}}},\"/v1/channels/message/private-channel/{channelid}/{messageid}\":{\"get\":{\"tags\":[\"channel-controller\"],\"summary\":\"Get message from private channel\",\"description\":\"Returns information about a message given a channel and message id.\",\"operationId\":\"getMessageInfo\",\"parameters\":[{\"name\":\"channelid\",\"in\":\"path\",\"required\":true,\"schema\":{\"type\":\"string\"}},{\"name\":\"messageid\",\"in\":\"path\",\"required\":true,\"schema\":{\"type\":\"string\"}}],\"responses\":{\"400\":{\"description\":\"Request not found or user is not in channel.\",\"content\":{\"*/*\":{\"schema\":{\"$ref\":\"#/components/schemas/ExceptionResponseModel\"}}}},\"200\":{\"description\":\"Found, returns MessageInfoDto.\",\"content\":{\"*/*\":{\"schema\":{\"$ref\":\"#/components/schemas/MessageInfoDto\"}}}}}}}},\"components\":{\"schemas\":{\"ExceptionResponseModel\":{\"type\":\"object\",\"properties\":{\"message\":{\"type\":\"string\"},\"timestamp\":{\"type\":\"string\",\"format\":\"date-time\"}}},\"ChannelCreationResponse\":{\"type\":\"object\",\"properties\":{\"channelId\":{\"type\":\"string\"},\"dateOfCreation\":{\"type\":\"string\",\"format\":\"date-time\"}}},\"UserRegisterDto\":{\"type\":\"object\",\"properties\":{\"username\":{\"type\":\"string\"},\"password\":{\"type\":\"string\"}}},\"AuthResponse\":{\"required\":[\"id\",\"token\",\"username\"],\"type\":\"object\",\"properties\":{\"id\":{\"type\":\"integer\",\"format\":\"int64\"},\"username\":{\"type\":\"string\"},\"accountDateOfCreation\":{\"type\":\"string\",\"format\":\"date-time\"},\"token\":{\"type\":\"string\"},\"tokenExpirationDate\":{\"type\":\"string\",\"format\":\"date-time\"}}},\"UserLoginDto\":{\"type\":\"object\",\"properties\":{\"username\":{\"type\":\"string\"},\"password\":{\"type\":\"string\"}}},\"UserPublicDto\":{\"type\":\"object\",\"properties\":{\"name\":{\"type\":\"string\"},\"dateOfCreation\":{\"type\":\"string\",\"format\":\"date-time\"},\"id\":{\"type\":\"integer\",\"format\":\"int64\"}}},\"MessageInfoDto\":{\"type\":\"object\",\"properties\":{\"username\":{\"type\":\"string\"},\"id\":{\"type\":\"string\"},\"dateOfCreation\":{\"type\":\"string\",\"format\":\"date-time\"},\"message\":{\"type\":\"string\"}}}}}}",
                    content = @Content(schema = @Schema(implementation = ExceptionResponseModel.class))),
            @ApiResponse(responseCode = "400",
                    description = "Username or password don't fit constraints.",
                    content = @Content(schema = @Schema(implementation = ExceptionResponseModel.class)))})
    @PostMapping("register")
    public ResponseEntity<AuthResponse> register(@RequestBody UserRegisterDto registerDto) {
        var returnDto = userService.registerUser(registerDto);
        return ResponseEntity.ok(returnDto);
    }

    @Operation(summary = "Login", description = "Logs in an user, returning a bearer token.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "User successfully authenticated, returns AuthResponse DTO.",
                    content = @Content(schema = @Schema(implementation = AuthResponse.class))),
            @ApiResponse(responseCode = "400",
                    description = "Invalid credentials.",
                    content = @Content(schema = @Schema(implementation = ExceptionResponseModel.class)))})
    @PostMapping("login")
    public ResponseEntity<AuthResponse> login(@RequestBody UserLoginDto userLoginDto) {
        var returnDto = userService.authenticate(userLoginDto);
        return ResponseEntity.ok(returnDto);
    }
}
