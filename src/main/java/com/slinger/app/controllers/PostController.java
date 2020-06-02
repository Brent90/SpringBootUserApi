package com.slinger.app.controllers;

import com.slinger.app.api.v1.model.PostDTO;
import com.slinger.app.api.v1.model.PostDTOList;
import com.slinger.app.services.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(PostController.POST_URL)
public class PostController {

    public static final String POST_URL = "/api/v1/posts";
    private final PostService postService;


    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public PostDTOList listPosts() {
        return new PostDTOList(postService.listPosts());
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public PostDTO findPostById(@PathVariable String id) {
        return postService.findPostById(Long.valueOf(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PostDTO createPost(@RequestBody PostDTO postDTO) {
        return postService.createPost(postDTO);
    }

}
