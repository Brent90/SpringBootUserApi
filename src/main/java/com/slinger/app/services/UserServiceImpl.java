package com.slinger.app.services;

import com.slinger.app.api.v1.mapper.CommentMapper;
import com.slinger.app.api.v1.mapper.PostMapper;
import com.slinger.app.api.v1.mapper.UserMapper;
import com.slinger.app.api.v1.model.CommentDTO;
import com.slinger.app.api.v1.model.PostDTO;
import com.slinger.app.api.v1.model.UserDTO;
import com.slinger.app.domian.Post;
import com.slinger.app.domian.User;
import com.slinger.app.repositories.CommentRepository;
import com.slinger.app.repositories.PostRepository;
import com.slinger.app.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.slinger.app.controllers.UserController.*;
import static com.slinger.app.controllers.PostController.*;
import static com.slinger.app.controllers.CommentController.*;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final UserMapper userMapper;
    private final PostMapper postMapper;
    private final CommentMapper commentMapper;

    public UserServiceImpl(UserRepository userRepository, PostRepository postRepository, CommentRepository commentRepository, UserMapper userMapper, PostMapper postMapper, CommentMapper commentMapper) {
        this.userRepository = userRepository;
        this.postRepository = postRepository;
        this.commentRepository = commentRepository;
        this.userMapper = userMapper;
        this.postMapper = postMapper;
        this.commentMapper = commentMapper;
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
               .filter(post -> {
                   if(post.getUser() != null) {
                      return post.getUser().getId().equals(userId);
                   }
                   return false;
               })
               .map(post -> {
                   PostDTO postDTO = postMapper.postToPostDTO(post);
                   postDTO.setPostUrl(POST_URL + "/" + post.getId());
                   postDTO.setUserUrl(USER_URL + "/" + post.getUser().getId());
                   return postDTO;
               }).collect(Collectors.toList());

    }

    @Override
    public PostDTO createPostWithUserId(Long userId, PostDTO postDTO) {
        User user = userRepository.findById(userId).get(); //todo add error handling
        Post post = postMapper.postDTOToPost(postDTO);
        user.addPost(post);
        userRepository.save(user);

        return postMapper.postToPostDTO(post);

    }

    @Override
    public List<CommentDTO> listAllUserComments(Long userId) {
        return commentRepository
                .findAll()
                .stream()
                .filter(comment -> comment.getUser().getId().equals(userId)
                ).map(comment -> {
                    CommentDTO commentDTO = commentMapper.commentToCommentDTO(comment);
                    commentDTO.setCommentUrl(COMMENT_URL + "/" + comment.getId());
                    commentDTO.setPostUrl(POST_URL + "/" + comment.getPost().getId());
                    commentDTO.setUserUrl(USER_URL + "/" + comment.getUser().getId());
                    return commentDTO;
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
