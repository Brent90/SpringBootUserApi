package com.slinger.app.controllers;

import com.slinger.app.api.v1.model.UserDTO;
import com.slinger.app.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


import static org.junit.jupiter.api.Assertions.*;

class UserControllerTest {

    @Mock
    UserService userService;

    @InjectMocks
    UserController userController;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Test
    void listAllUsers() throws Exception {
        List<UserDTO> users = Arrays.asList(new UserDTO(), new UserDTO(), new UserDTO());
        when(userService.listAllUsers()).thenReturn(users);

        mockMvc.perform(get("/api/v1/users") //todo make this constant
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.users", hasSize(3)));

        verify(userService, times(1)).listAllUsers();

    }
}
















