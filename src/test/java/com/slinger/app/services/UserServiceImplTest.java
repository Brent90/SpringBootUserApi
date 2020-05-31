package com.slinger.app.services;

import com.slinger.app.api.v1.mapper.UserMapper;
import com.slinger.app.api.v1.model.UserDTO;
import com.slinger.app.domian.Post;
import com.slinger.app.domian.User;
import com.slinger.app.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.persistence.Id;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceImplTest {

    public static final long ID = 1L;
    public static final String NAME = "User Name";
    public static final String USERNAME = "username343";
    public static final String EMAIL = "email@gmail.com";
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

    @Test
    void findUserById() {
        //given
        User user = new User();
        user.setId(ID);
        user.setName(NAME);
        user.setUsername(USERNAME);
        user.setEmail(EMAIL);

        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));

        //when
        UserDTO userDTO = userService.findUserById(anyLong());

        //then
        assertNotNull(userDTO);
        assertEquals(NAME, userDTO.getName());
        assertEquals(USERNAME, userDTO.getUsername());
        assertEquals(EMAIL, userDTO.getEmail());
        assertEquals("/api/v1/users/" + ID, userDTO.getUserUrl()); //todo make this a constant
        verify(userRepository, times(1)).findById(anyLong());

    }

    @Test
    void createNewUser() {
        //given
        UserDTO userDTO = new UserDTO();
        userDTO.setName(NAME);
        userDTO.setUsername(USERNAME);
        userDTO.setEmail(EMAIL);

        User savedUser = UserMapper.USER_MAPPER.userDTOToUser(userDTO);
        savedUser.setId(ID);

        when(userRepository.save(any(User.class))).thenReturn(savedUser);

        //when
        UserDTO savedDTO = userService.createNewUser(userDTO);

        //then
        assertNotNull(savedDTO);
        assertEquals(NAME, savedDTO.getName());
        assertEquals(USERNAME, savedDTO.getUsername());
        assertEquals(EMAIL, savedDTO.getEmail());
        verify(userRepository, times(1)).save(any(User.class));

    }
}
