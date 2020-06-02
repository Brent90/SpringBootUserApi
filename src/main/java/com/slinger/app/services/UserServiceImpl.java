package com.slinger.app.services;

import com.slinger.app.api.v1.mapper.PostMapper;
import com.slinger.app.api.v1.mapper.UserMapper;
import com.slinger.app.api.v1.model.PostDTO;
import com.slinger.app.api.v1.model.UserDTO;
import com.slinger.app.domian.User;
import com.slinger.app.repositories.PostRepository;
import com.slinger.app.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.slinger.app.controllers.UserController.*;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final UserMapper userMapper;
    private final PostMapper postMapper;

    public UserServiceImpl(UserRepository userRepository, PostRepository postRepository, UserMapper userMapper, PostMapper postMapper) {
        this.userRepository = userRepository;
        this.postRepository = postRepository;
        this.userMapper = userMapper;
        this.postMapper = postMapper;
    }

    @Override
    public List<UserDTO> listAllUsers() {
        return userRepository
                .findAll()
                .stream()
                .map(user -> {
                    UserDTO userDTO = userMapper.userToUserDTO(user);
                    userDTO.setUserUrl(USER_URL + "/" + user.getId());
                    return userDTO;
                }).collect(Collectors.toList());
    }

    @Override
    public UserDTO findUserById(Long id) {
        return userRepository
                .findById(id)
                .map(user -> {
                    UserDTO userDTO = userMapper.userToUserDTO(user);
                    userDTO.setUserUrl(USER_URL + "/" + user.getId());
                    return userDTO;
                })
                .orElseThrow(() ->new RuntimeException("User not found with id " + id)); //todo add better exception handling
    }

    @Override
    public UserDTO createNewUser(UserDTO userDTO) {
        User user = UserMapper.USER_MAPPER.userDTOToUser(userDTO);
        return saveAndReturn(user);
    }

    @Override
    public UserDTO updateUser(Long id, UserDTO userDTO) {
        User user = UserMapper.USER_MAPPER.userDTOToUser(userDTO);
        user.setId(id);
        return saveAndReturn(user);
    }

    @Override
    public void deleteUserById(Long id) {
        Optional<User> optionalUser = userRepository.findById(id);

        if(!optionalUser.isPresent()) {
            throw new RuntimeException("No user found with id " + id + " cannot delete"); //todo add better exception handling
        }else {
            userRepository.deleteById(id);
        }

    }

    @Override
    public List<PostDTO> listAllUserPosts(Long userId) {
       return postRepository
               .findAll()
               .stream()
               .filter(post -> post.getUser().getId().equals(userId))
               .map(post -> {
                   PostDTO postDTO = postMapper.postToPostDTO(post);
                   postDTO.setPostUrl("/api/v1/posts/" + post.getId()); //todo make this a constant
                   postDTO.setUserUrl(USER_URL + "/" + post.getUser().getId());
                   return postDTO;
               }).collect(Collectors.toList());

    }

    //helper method for saving and updating user
    private UserDTO saveAndReturn(User user) {
        User savedUser = userRepository.save(user);
        UserDTO returnDTO = userMapper.userToUserDTO(savedUser);
        returnDTO.setUserUrl(USER_URL + "/" + savedUser.getId());
        return returnDTO;
    }
}
