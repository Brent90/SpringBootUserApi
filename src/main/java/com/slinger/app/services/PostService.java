package com.slinger.app.services;

import com.slinger.app.api.v1.model.PostDTO;

import java.util.List;

public interface PostService {

    List<PostDTO> listPosts();

    PostDTO findPostById(Long id);

}
