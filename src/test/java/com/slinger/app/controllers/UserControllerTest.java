package com.slinger.app.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
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
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static com.slinger.app.controllers.UserController.*;


class UserControllerTest {

    public static final String NAME = "Name";
    public static final String USERNAME = "Username";
    public static final String EMAIL = "email@gmail.com";
    public static final Long ID = 1L;

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

        mockMvc.perform(get(USER_URL)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.users", hasSize(3)));

        verify(userService, times(1)).listAllUsers();

    }

    @Test
    void getUserById() throws Exception {
        UserDTO userDTO = new UserDTO();
        userDTO.setName(NAME);
        userDTO.setUsername(USERNAME);
        userDTO.setEmail(EMAIL);
        userDTO.setUserUrl(USER_URL + "/" + ID);

        when(userService.findUserById(anyLong())).thenReturn(userDTO);

        mockMvc.perform(get(USER_URL + "/" + ID)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", equalTo(userDTO.getName())))
                .andExpect(jsonPath("$.username", equalTo(userDTO.getUsername())))
                .andExpect(jsonPath("$.email", equalTo(userDTO.getEmail())));

        verify(userService, times(1)).findUserById(anyLong());
    }

    @Test
    void createUser() throws Exception {
        UserDTO userDTO = new UserDTO();
        userDTO.setName(NAME);
        userDTO.setUsername(USERNAME);
        userDTO.setEmail(EMAIL);

        UserDTO returnDTO = new UserDTO();
        returnDTO.setName(userDTO.getName());
        returnDTO.setUsername(userDTO.getUsername());
        returnDTO.setEmail(userDTO.getEmail());
        returnDTO.setUserUrl(USER_URL + "/1");

        when(userService.createNewUser(userDTO)).thenReturn(returnDTO);

        mockMvc.perform(post(USER_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(userDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", equalTo(NAME)))
                .andExpect(jsonPath("$.username", equalTo(USERNAME)))
                .andExpect(jsonPath("$.user_url", equalTo(USER_URL +"/1")));

        verify(userService, times(1)).createNewUser(any(UserDTO.class));
    }


    @Test
    void updateUser() throws Exception{
        UserDTO user = new UserDTO();
        user.setName(NAME);
        user.setEmail(EMAIL);
        user.setUsername(USERNAME);

        UserDTO returnDTO = new UserDTO();
        returnDTO.setName(user.getName());
        returnDTO.setEmail(user.getEmail());
        returnDTO.setUsername(USERNAME);
        returnDTO.setUserUrl(USER_URL + "/1");

        when(userService.updateUser(anyLong(), any(UserDTO.class))).thenReturn(returnDTO);

        mockMvc.perform(put(USER_URL + "/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(user)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.user_url", equalTo(USER_URL + "/1")));

        verify(userService, times(1)).updateUser(anyLong(), any(UserDTO.class));

    }
}
















