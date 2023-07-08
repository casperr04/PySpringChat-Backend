package com.casperr04.pyspringchatbackend.controller;


import com.casperr04.pyspringchatbackend.model.dto.ExceptionResponseModel;
import com.casperr04.pyspringchatbackend.model.dto.UserPublicDto;
import com.casperr04.pyspringchatbackend.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * This controller is for unauthenticated endpoints that serve public user information.
 */

@Tag(name = "user-public-controller", description = "These endpoints are public, you do not need to be authenticated to use them.")
@RestController
@AllArgsConstructor
@RequestMapping("/v1/user/info")
public class UserPublicController {

    private final UserService userService;

    @Operation(summary = "Get user information by ID", description = "Gets public information about an user. This endpoint is public.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "User found, returns UserPublicDto.",
                    content = @Content(schema = @Schema(implementation = UserPublicDto.class))),
            @ApiResponse(responseCode = "400",
                    description = "User not found.",
                    content = @Content(schema = @Schema(implementation = ExceptionResponseModel.class)))})
    @GetMapping("/id/{id}")
    public ResponseEntity<UserPublicDto> getPublicUserInformationByUsername(@Parameter(description = "ID of the user", required = true) @PathVariable("id") Long id) {
        var returnDto = userService.receiveUserInfo(id);
        return ResponseEntity.ok(returnDto);
    }
    @Operation(summary = "Get user information by username.", description = "Gets public information about an user. This endpoint is public.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "User found, returns UserPublicDto.",
                    content = @Content(schema = @Schema(implementation = UserPublicDto.class))),
            @ApiResponse(responseCode = "400",
                    description = "User not found.",
                    content = @Content(schema = @Schema(implementation = ExceptionResponseModel.class)))})
    @GetMapping("/name/{username}")
    public ResponseEntity<UserPublicDto> getPublicUserInformationByUsername(@Parameter(description = "Username of the user", required = true) @PathVariable("username") String username) {
        var returnDto = userService.receiveUserInfo(username);
        return ResponseEntity.ok(returnDto);
    }
}
