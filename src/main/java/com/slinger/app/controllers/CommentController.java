package com.slinger.app.controllers;

import com.slinger.app.api.v1.model.CommentDTOList;
import com.slinger.app.services.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

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

}
