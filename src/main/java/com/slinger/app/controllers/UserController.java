package com.slinger.app.controllers;

import com.slinger.app.api.v1.model.PostDTO;
import com.slinger.app.api.v1.model.UserDTO;
import com.slinger.app.api.v1.model.UserDTOList;
import com.slinger.app.domian.Post;
import com.slinger.app.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.persistence.Lob;
import java.util.List;

@RestController
@RequestMapping(UserController.USER_URL)
public class UserController {

    private final UserService userService;
    public final static String USER_URL = "/api/v1/users";

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public UserDTOList listAllUsers() {
        return new UserDTOList(userService.listAllUsers());
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UserDTO getUserById(@PathVariable String id) {
        return userService.findUserById(Long.valueOf(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserDTO createUser(@RequestBody UserDTO userDTO) {
        return userService.createNewUser(userDTO);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UserDTO updateUser(@PathVariable String id, @RequestBody UserDTO userDTO) {
        return userService.updateUser(Long.valueOf(id), userDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteUserById(@PathVariable String id) {
        userService.deleteUserById(Long.valueOf(id));
    }

    @GetMapping("/{id}/posts")
    @ResponseStatus(HttpStatus.OK)
    public List<PostDTO> listUserPosts(@PathVariable String id) {
        return userService.listAllUserPosts(Long.valueOf(id));
    }

    @PostMapping("/{id}/posts")
    @ResponseStatus(HttpStatus.CREATED)
    public PostDTO createPostWithUserId(@PathVariable String id, @RequestBody PostDTO postDTO) {
        return userService.createPostWithUserId(Long.valueOf(id), postDTO);
    }


}












































