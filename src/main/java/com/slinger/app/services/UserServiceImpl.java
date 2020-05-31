package com.slinger.app.services;

import com.slinger.app.api.v1.mapper.UserMapper;
import com.slinger.app.api.v1.model.UserDTO;
import com.slinger.app.domian.User;
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

    @Override
    public UserDTO findUserById(Long id) {
        return userRepository
                .findById(id)
                .map(user -> {
                    UserDTO userDTO = userMapper.userToUserDTO(user);
                    userDTO.setUserUrl("/api/v1/users/" + user.getId()); //todo make this a constant
                    return userDTO;
                })
                .orElseThrow(() ->new RuntimeException("User not found with id " + id)); //todo add better exception handling
    }

    @Override
    public UserDTO createNewUser(UserDTO userDTO) {
        User user = UserMapper.USER_MAPPER.userDTOToUser(userDTO);
        User savedUser = userRepository.save(user);

        UserDTO returnDTO = userMapper.userToUserDTO(savedUser);
        returnDTO.setUserUrl("/api/v1/users/" + savedUser.getId());  //todo make this a constant

        return returnDTO;
    }
}
