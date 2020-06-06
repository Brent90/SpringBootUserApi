package com.slinger.app.services;

import com.slinger.app.api.v1.mapper.CommentMapper;
import com.slinger.app.api.v1.mapper.PostMapper;
import com.slinger.app.api.v1.model.CommentDTO;
import com.slinger.app.api.v1.model.PostDTO;
import com.slinger.app.domian.Post;
import com.slinger.app.domian.User;
import com.slinger.app.exceptions.NotFoundException;
import com.slinger.app.repositories.CommentRepository;
import com.slinger.app.repositories.PostRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.slinger.app.controllers.UserController.*;
import static com.slinger.app.controllers.PostController.*;
import static com.slinger.app.controllers.CommentController.*;

@Slf4j
@Service
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final PostMapper postMapper;
    private final CommentMapper commentMapper;

    public PostServiceImpl(PostRepository postRepository, CommentRepository commentRepository, PostMapper postMapper, CommentMapper commentMapper) {
        this.postRepository = postRepository;
        this.commentRepository = commentRepository;
        this.postMapper = postMapper;
        this.commentMapper = commentMapper;
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
                .orElseThrow(() -> new NotFoundException("Post not found with id " + id));
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
            throw new NotFoundException("No post found with id " + id);
        }else {
            postRepository.deleteById(id);
        }

    }

    @Override
    public List<CommentDTO> listPostComments(Long postId) {
        Optional<Post> optionalPost = postRepository.findById(postId);

        if(!optionalPost.isPresent()) {
            throw new NotFoundException("No post found with id " + postId);
        }

        Post post = optionalPost.get();

       return post.getComments().stream()
                .map(comment -> {
                    CommentDTO commentDTO = commentMapper.commentToCommentDTO(comment);
                    commentDTO.setCommentUrl(COMMENT_URL + "/" + comment.getId());
                    commentDTO.setPostUrl(POST_URL + "/" + postId);
                    commentDTO.setUserUrl(USER_URL + "/" + post.getUser().getId());
                    return commentDTO;
                }).collect(Collectors.toList());
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























