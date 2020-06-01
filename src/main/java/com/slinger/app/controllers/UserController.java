package com.slinger.app.controllers;

import com.slinger.app.api.v1.model.UserDTOList;
import com.slinger.app.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

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
}
