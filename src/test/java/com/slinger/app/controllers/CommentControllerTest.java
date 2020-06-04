package com.slinger.app.controllers;

import com.slinger.app.api.v1.model.CommentDTO;
import com.slinger.app.services.CommentService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static com.slinger.app.controllers.CommentController.*;

import static org.junit.jupiter.api.Assertions.*;

class CommentControllerTest {

    @Mock
    CommentService commentService;

    @InjectMocks
    CommentController commentController;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(commentController).build();
    }

    @Test
    void getComments() throws Exception {
        List<CommentDTO> commentDTOList = Arrays.asList(new CommentDTO(), new CommentDTO());

        when(commentService.listAllComments()).thenReturn(commentDTOList);

        mockMvc.perform(get(COMMENT_URL)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.comments", Matchers.hasSize(2)));

        verify(commentService, times(1)).listAllComments();


    }
}
