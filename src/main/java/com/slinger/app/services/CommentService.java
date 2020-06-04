package com.slinger.app.services;

import com.slinger.app.api.v1.model.CommentDTO;
import com.slinger.app.domian.Comment;

import java.util.List;

public interface CommentService {

    List<CommentDTO> listAllComments();

    CommentDTO findCommentById(Long id);
}
