package com.slinger.app.services;

import com.slinger.app.api.v1.model.CommentDTO;
import com.slinger.app.api.v1.model.PostDTO;

import java.util.List;

public interface PostService {

    List<PostDTO> listPosts();

    PostDTO findPostById(Long id);

    PostDTO createPost(PostDTO postDTO);

    PostDTO updatePost(Long id, PostDTO postDTO);

    void deletePostById(Long id);

    List<CommentDTO> listPostComments(Long postId);

}
