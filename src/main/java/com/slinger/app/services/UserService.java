package com.slinger.app.services;

import com.slinger.app.api.v1.model.PostDTO;
import com.slinger.app.api.v1.model.UserDTO;

import java.util.List;

public interface UserService {

    List<UserDTO> listAllUsers();

    UserDTO findUserById(Long id);

    UserDTO createNewUser(UserDTO userDTO);

    UserDTO updateUser(Long id, UserDTO userDTO);

    void deleteUserById(Long id);

    List<PostDTO> listAllUserPosts(Long userId);

}
