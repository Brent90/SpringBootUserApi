package com.slinger.app.api.v1.mapper;

import com.slinger.app.api.v1.model.CommentDTO;
import com.slinger.app.domian.Comment;
import com.slinger.app.domian.Post;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.*;

class CommentMapperTest {

    CommentMapper COMMENT_MAPPER = Mappers.getMapper(CommentMapper.class);
    final String COMMENT_BODY = "COMMENT BODY";

    @BeforeEach
    void setUp() {
    }

    @Test
    void commentToCommentDTO() {
        //given
        Comment comment = new Comment();
        comment.setPost(new Post());
        comment.setBody(COMMENT_BODY);

        //when
        CommentDTO commentDTO = COMMENT_MAPPER.commentToCommentDTO(comment);
        commentDTO.setCommentUrl("/api/comments/3");

        //then
        assertNotNull(commentDTO);
        assertEquals(COMMENT_BODY, commentDTO.getBody());
        assertEquals("/api/comments/3", commentDTO.getCommentUrl());

    }

    @Test
    void commentDTOToComment() {
        //given
        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setCommentUrl("comment_url");
        commentDTO.setPostUrl("post_url");
        commentDTO.setBody(COMMENT_BODY);

        //when
        Comment comment = COMMENT_MAPPER.commentDTOToComment(commentDTO);

        //then
        assertNotNull(commentDTO);
        assertEquals(COMMENT_BODY, comment.getBody());

    }
}
