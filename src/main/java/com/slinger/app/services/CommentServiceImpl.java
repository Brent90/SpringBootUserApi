package com.slinger.app.services;

import com.slinger.app.api.v1.mapper.CommentMapper;
import com.slinger.app.api.v1.model.CommentDTO;
import com.slinger.app.domian.Comment;
import com.slinger.app.repositories.CommentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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
                    commentDTO.setCommentUrl("/api/v1/comments/" + comment.getId());
                    commentDTO.setPostUrl("/api/v1/posts/" + comment.getPost().getId());
                    commentDTO.setUserUrl("/api/v1/users/" + comment.getUser().getId());
                    return commentDTO;
                }).collect(Collectors.toList());
    }
}
