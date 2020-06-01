package com.slinger.app.controllers;

import com.slinger.app.api.v1.model.UserDTO;
import com.slinger.app.api.v1.model.UserDTOList;
import com.slinger.app.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
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
}























