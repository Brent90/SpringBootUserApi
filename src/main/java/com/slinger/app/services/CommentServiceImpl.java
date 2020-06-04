package com.slinger.app.services;

import com.slinger.app.api.v1.mapper.CommentMapper;
import com.slinger.app.api.v1.model.CommentDTO;
import com.slinger.app.domian.Comment;
import com.slinger.app.repositories.CommentRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.slinger.app.controllers.CommentController.*;
import static com.slinger.app.controllers.PostController.*;
import static com.slinger.app.controllers.UserController.*;

@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;

    public CommentServiceImpl(CommentRepository commentRepository, CommentMapper commentMapper) {
        this.commentRepository = commentRepository;
        this.commentMapper = commentMapper;
    }

    @Override
    public List<CommentDTO> listAllComments() {
        return commentRepository
                .findAll()
                .stream()
                .map(comment -> {
                    CommentDTO commentDTO = commentMapper.commentToCommentDTO(comment);
                    commentDTO.setCommentUrl(COMMENT_URL + "/" + comment.getId());
                    commentDTO.setPostUrl(POST_URL + "/" + comment.getPost().getId());
                    commentDTO.setUserUrl(USER_URL + "/" + comment.getUser().getId());
                    return commentDTO;
                }).collect(Collectors.toList());
    }

    @Override
    public CommentDTO findCommentById(Long id) {
        return commentRepository
                .findById(id)
                .map(comment -> {
                  CommentDTO commentDTO = commentMapper.commentToCommentDTO(comment);
                  commentDTO.setCommentUrl(COMMENT_URL + "/" + id);
                  commentDTO.setPostUrl(POST_URL + "/" + comment.getPost().getId());
                  commentDTO.setUserUrl(USER_URL + "/" + comment.getUser().getId());
                  return commentDTO;
                }).orElseThrow(() -> new RuntimeException("No comment found with id " + id)); //todo add better exception handling
    }

    @Override
    public void deleteCommentById(Long id) {
        Optional<Comment> commentOptional = commentRepository.findById(id);

        if(!commentOptional.isPresent()) {
            throw new RuntimeException("No comment found with id " + id); // todo add better exception handling
        }

        Comment deleteComment = commentOptional.get();
        commentRepository.deleteById(deleteComment.getId());
    }
}

























