package com.slinger.app.services;

import com.slinger.app.api.v1.mapper.PostMapper;
import com.slinger.app.api.v1.model.PostDTO;
import com.slinger.app.domian.Post;
import com.slinger.app.repositories.PostRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

import static com.slinger.app.controllers.UserController.*;

@Service
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final PostMapper postMapper;

    public PostServiceImpl(PostRepository postRepository, PostMapper postMapper) {
        this.postRepository = postRepository;
        this.postMapper = postMapper;
    }

    @Override
    public List<PostDTO> listPosts() {
        return postRepository
                .findAll()
                .stream()
                .map(post -> setPostDTO(post))
                .collect(Collectors.toList());
    }

    @Override
    public PostDTO findPostById(Long id) {
        return postRepository
                .findById(id)
                .map(post -> setPostDTO(post))
                .orElseThrow(() -> new RuntimeException("Post not found with id " + id)); //todo add better exception handling
    }


    private PostDTO setPostDTO(Post post) {
        PostDTO postDTO = postMapper.postToPostDTO(post);
        postDTO.setPostUrl("/api/v1/posts/" + post.getId()); //todo make this a constant

        if(post.getUser() == null) {
            postDTO.setUserUrl(null);
        } else {
            postDTO.setUserUrl(USER_URL + "/" + post.getUser().getId());
        }
        return postDTO;
    }

}























