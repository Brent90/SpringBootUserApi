package com.slinger.app.services;

import com.slinger.app.api.v1.mapper.UserMapper;
import com.slinger.app.api.v1.model.UserDTO;
import com.slinger.app.repositories.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public List<UserDTO> listAllUsers() {
        return userRepository
                .findAll()
                .stream()
                .map(user -> {
                    UserDTO userDTO = userMapper.userToUserDTO(user);
                    userDTO.setUserUrl("/api/v1/users"); //todo make this a constant
                    return userDTO;
                }).collect(Collectors.toList());
    }
}
