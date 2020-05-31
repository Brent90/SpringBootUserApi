package com.slinger.app.api.v1.model;

import lombok.Data;

import java.util.List;

@Data
public class UserDTOList {
    private List<UserDTO> users;
}
