package com.slinger.app.api.v1.model;

import lombok.Data;

import java.util.List;

@Data
public class PostDTOList {
    private List<PostDTO> posts;
}
