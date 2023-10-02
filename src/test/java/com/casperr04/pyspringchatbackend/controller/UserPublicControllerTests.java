package com.casperr04.pyspringchatbackend.controller;

import com.casperr04.pyspringchatbackend.exception.ExceptionControllerAdvice;
import com.casperr04.pyspringchatbackend.exception.MissingEntityException;
import com.casperr04.pyspringchatbackend.model.dto.UserPublicDto;
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

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
@WebMvcTest(controllers = UserPublicController.class)
@AutoConfigureMockMvc(addFilters = false)
@ContextConfiguration(classes = UserPublicController.class)
@ImportAutoConfiguration(ExceptionControllerAdvice.class)
@ExtendWith(MockitoExtension.class)
class UserPublicControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    ObjectMapper mapper = new ObjectMapper();

    UserPublicDto mockDto = UserPublicDto.builder()
            .name("ABC123")
            .Id(123)
            .dateOfCreation(Instant.now())
            .build();

    @BeforeEach
    public void init() {
        mapper.registerModule(new JavaTimeModule());
    }
    @Test
    void GetUserInfoByID_UserFound_Success() throws Exception {
        when(userService.receiveUserInfo(anyLong())).thenReturn(mockDto);

        ResultActions response = mockMvc.perform(get("/v1/user/info/id/123")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("utf-8"));
        response.andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", CoreMatchers.is(mockDto.getName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", CoreMatchers.is(((int) mockDto.getId()))));
    }

    @Test
    void GetUserInfoByUsername_UserFound_Success() throws Exception {
        when(userService.receiveUserInfo(anyString())).thenReturn(mockDto);

        ResultActions response = mockMvc.perform(get("/v1/user/info/name/ABC123")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("utf-8"));
        response.andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", CoreMatchers.is(mockDto.getName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", CoreMatchers.is(((int) mockDto.getId()))));
    }

    @Test
    void GetUserInfoByID_UserNotFound_Failure() throws Exception {
        when(userService.receiveUserInfo(anyLong())).thenThrow(MissingEntityException.class);

        ResultActions response = mockMvc.perform(get("/v1/user/info/id/123")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("utf-8"));
        response.andExpect(MockMvcResultMatchers.status().is4xxClientError());
    }

    @Test
    void GetUserInfoByUsername_UserNotFound_Failure() throws Exception {
        when(userService.receiveUserInfo(anyString())).thenThrow(MissingEntityException.class);

        ResultActions response = mockMvc.perform(get("/v1/user/info/name/ABC123")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("utf-8"));
        response.andExpect(MockMvcResultMatchers.status().is4xxClientError());
    }
}
