package com.slinger.app.services;

import com.slinger.app.api.v1.mapper.UserMapper;
import com.slinger.app.api.v1.model.UserDTO;
import com.slinger.app.domian.User;
import com.slinger.app.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceImplTest {

    @Mock
    UserRepository userRepository;

    UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
       userService = new UserServiceImpl(userRepository, UserMapper.USER_MAPPER);
    }

    @Test
    void listAllUsers() {
        //given
        List<User> users = Arrays.asList(new User(), new User(), new User());

        when(userRepository.findAll()).thenReturn(users);

        //when
        List<UserDTO> userDTOS = userService.listAllUsers();

        //then
        assertNotNull(userDTOS);
        assertEquals(3, userDTOS.size());
        verify(userRepository, times(1)).findAll();

    }
}
