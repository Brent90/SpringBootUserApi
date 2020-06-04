package com.slinger.app.services;

import com.slinger.app.api.v1.mapper.CommentMapper;
import com.slinger.app.api.v1.model.CommentDTO;
import com.slinger.app.domian.Comment;
import com.slinger.app.domian.Post;
import com.slinger.app.domian.User;
import com.slinger.app.repositories.CommentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CommentServiceImplTest {



    @Mock
    CommentRepository commentRepository;

    CommentService commentService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        commentService = new CommentServiceImpl(commentRepository, CommentMapper.COMMENT_MAPPER);
    }

    @Test
    void listAllComments() {
        //given
        User user1 = new User();
        user1.setId(1L);

        User user2 = new User();
        user2.setId(3L);

        Post post1 = new Post();
        post1.setId(1L);

        Post post2 = new Post();
        post2.setId(3L);

        List<Comment> comments = new ArrayList<>();
        Comment comment1 = new Comment();
        comment1.setPost(post1);
        comment1.setUser(user1);

        Comment comment2 = new Comment();
        comment2.setPost(post2);
        comment2.setUser(user2);

        comments.add(comment1);
        comments.add(comment2);

        when(commentRepository.findAll()).thenReturn(comments);

        //when
        List<CommentDTO> commentDTOS = commentService.listAllComments();

        //then
        assertNotNull(commentDTOS);
        assertEquals(2, commentDTOS.size());
        verify(commentRepository, times(1)).findAll();

    }

    @Test
    void findCommentById() {
        //given
        Comment comment = new Comment();
        comment.setBody("Comment Body");

        User user1 = new User();
        user1.setId(3L);

        Post post1 = new Post();
        post1.setId(1L);

        comment.setUser(user1);
        comment.setPost(post1);


        when(commentRepository.findById(anyLong())).thenReturn(Optional.of(comment));

        //when
        CommentDTO commentDTO = commentService.findCommentById(anyLong());

        //then
        assertNotNull(commentDTO);
        assertEquals("Comment Body", commentDTO.getBody());
    }

    @Test
    void deleteCommentById() {
        Comment comment = new Comment();
        comment.setId(1L);

        commentRepository.deleteById(1L);

        Optional<Comment> commentOptional = commentRepository.findById(1L);

        assertThrows(NoSuchElementException.class, () -> commentRepository.findById(1L).get());
        verify(commentRepository, times(1)).deleteById(anyLong());
    }
}

















