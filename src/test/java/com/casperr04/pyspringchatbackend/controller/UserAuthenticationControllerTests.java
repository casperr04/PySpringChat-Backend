package com.casperr04.pyspringchatbackend.controller;

import com.casperr04.pyspringchatbackend.exception.ExceptionControllerAdvice;
import com.casperr04.pyspringchatbackend.model.dto.AuthResponse;
import com.casperr04.pyspringchatbackend.model.dto.UserLoginDto;
import com.casperr04.pyspringchatbackend.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.Instant;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@WebMvcTest(controllers = UserAuthenticationController.class)
@AutoConfigureMockMvc(addFilters = false)
@ContextConfiguration(classes = UserAuthenticationController.class)
@ImportAutoConfiguration(ExceptionControllerAdvice.class)
@ExtendWith(MockitoExtension.class)
public class UserAuthenticationControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    ObjectMapper mapper = new ObjectMapper();

    AuthResponse mockResponse = AuthResponse.builder()
            .username("ABC123")
            .id(123L)
            .token("DEF456")
            .accountDateOfCreation(Instant.now())
            .tokenExpirationDate(Instant.now().plusSeconds(600))
            .build();

    @BeforeEach
    public void init() {
        mapper.registerModule(new JavaTimeModule());
    }
    @Test
    void RegisterUser_ValidUser_Success() throws Exception {
        when(userService.registerUser(any())).thenReturn(mockResponse);
        String jsonString = mapper.writeValueAsString(mockResponse);

        ResultActions response = mockMvc.perform(post("/v1/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("utf-8")
                .content(jsonString));
        response.andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andExpect(MockMvcResultMatchers.jsonPath("$.username", CoreMatchers.is(mockResponse.getUsername())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.token", CoreMatchers.is(mockResponse.getToken())));
    }

    @Test
    void AuthenticateUser_ValidUser_Success() throws Exception {
        when(userService.authenticate((UserLoginDto) any())).thenReturn(mockResponse);
        String jsonString = mapper.writeValueAsString(mockResponse);

        ResultActions response = mockMvc.perform(post("/v1/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("utf-8")
                .content(jsonString));
        response.andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andExpect(MockMvcResultMatchers.jsonPath("$.username", CoreMatchers.is(mockResponse.getUsername())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.token", CoreMatchers.is(mockResponse.getToken())));
    }

    @Test
    void RegisterUser_InvalidUser_Failure() throws Exception {
        when(userService.registerUser(any())).thenThrow(new IllegalArgumentException("Error Message"));
        String jsonString = mapper.writeValueAsString(mockResponse);

        ResultActions response = mockMvc.perform(post("/v1/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("utf-8")
                .content(jsonString));
        response.andExpect(MockMvcResultMatchers.status().is4xxClientError());
    }

    @Test
    void AuthenticateUser_UserNotFound_Failure() throws Exception {
        when(userService.registerUser(any())).thenThrow(new IllegalArgumentException("Error Message"));
        String jsonString = mapper.writeValueAsString(mockResponse);

        ResultActions response = mockMvc.perform(post("/v1/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("utf-8")
                .content(jsonString));
        response.andExpect(MockMvcResultMatchers.status().is4xxClientError());
    }
}
