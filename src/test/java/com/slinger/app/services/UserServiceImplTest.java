package com.slinger.app.services;

import com.slinger.app.api.v1.mapper.PostMapper;
import com.slinger.app.api.v1.mapper.UserMapper;
import com.slinger.app.api.v1.model.UserDTO;
import com.slinger.app.domian.User;
import com.slinger.app.repositories.PostRepository;
import com.slinger.app.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static com.slinger.app.controllers.UserController.*;

class UserServiceImplTest {

    public static final long ID = 1L;
    public static final String NAME = "User Name";
    public static final String USERNAME = "username343";
    public static final String EMAIL = "email@gmail.com";
    @Mock
    UserRepository userRepository;

    @Mock
    PostRepository postRepository;

    UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
       userService = new UserServiceImpl(userRepository, postRepository, UserMapper.USER_MAPPER, PostMapper.POST_MAPPER);
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
        assertEquals(USER_URL + "/" + ID, userDTO.getUserUrl());
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

    @Test
    void deleteUserById() {
        User user = new User();
        user.setId(ID);

        userRepository.deleteById(ID);
        Optional<User> optionalUser = userRepository.findById(ID);

        assertThrows(NoSuchElementException.class, () -> optionalUser.get());
        verify(userRepository, times(1)).deleteById(anyLong());
    }
}
