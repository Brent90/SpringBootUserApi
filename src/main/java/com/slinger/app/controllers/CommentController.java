package com.slinger.app.controllers;

import com.slinger.app.api.v1.model.CommentDTO;
import com.slinger.app.api.v1.model.CommentDTOList;
import com.slinger.app.services.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(CommentController.COMMENT_URL)
public class CommentController {

    public static final String COMMENT_URL = "/api/v1/comments";
    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public CommentDTOList getComments() {
         return new CommentDTOList(commentService.listAllComments());
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CommentDTO getCommentById(@PathVariable String id) {
        return commentService.findCommentById(Long.valueOf(id));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteCommentById(@PathVariable String id) {
        commentService.deleteCommentById(Long.valueOf(id));
    }

}
