package com.slinger.app.services;

import com.slinger.app.api.v1.mapper.PostMapper;
import com.slinger.app.api.v1.model.PostDTO;
import com.slinger.app.domian.Post;
import com.slinger.app.domian.User;
import com.slinger.app.repositories.PostRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.slinger.app.controllers.UserController.*;
import static com.slinger.app.controllers.PostController.*;

@Slf4j
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

    @Override
    public PostDTO createPost(PostDTO postDTO) {
        Post post = postMapper.postDTOToPost(postDTO);
        Post savedPost = postRepository.save(post);

        PostDTO returnDTO = setPostDTO(savedPost);

        return returnDTO;
    }

    @Override
    public PostDTO updatePost(Long id, PostDTO postDTO) {
        Post post = postMapper.postDTOToPost(postDTO);
        post.setId(id);

        Optional<Post> optionalPost = postRepository.findById(id);

        if(optionalPost.isPresent()) {
            Post foundPost = optionalPost.get();
            User user = foundPost.getUser();
            post.setUser(user);
        }

        Post savedPost = postRepository.save(post);
        return setPostDTO(savedPost);
    }

    @Override
    public void deletePostById(Long id) {
        Optional<Post> optionalPost = postRepository.findById(id);

        if(!optionalPost.isPresent()) {
            throw new RuntimeException("No post found with id " + id); //todo add better exception handling
        }else {
            postRepository.deleteById(id);
        }

    }

    private PostDTO setPostDTO(Post post) {
        PostDTO postDTO = postMapper.postToPostDTO(post);
        postDTO.setPostUrl(POST_URL + "/" + post.getId());

        if(post.getUser() == null) {
            postDTO.setUserUrl(null);
        } else {
            postDTO.setUserUrl(USER_URL + "/" + post.getUser().getId());
        }
        return postDTO;
    }

}























