package com.slinger.app.controllers;

import com.slinger.app.api.v1.model.PostDTO;
import com.slinger.app.services.PostService;
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

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static com.slinger.app.controllers.PostController.*;

class PostControllerTest {

    public static final String POST_TITLE = "Post Title";
    public static final String POST_BODY = "Post Body";
    @Mock
    PostService postService;

    @InjectMocks
    PostController postController;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(postController).build();
    }

    @Test
    void listPosts() throws Exception {
        List<PostDTO> posts = Arrays.asList(new PostDTO(), new PostDTO(), new PostDTO());

        when(postService.listPosts()).thenReturn(posts);

        mockMvc.perform(get(POST_URL)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.posts", hasSize(3)));

        verify(postService, times(1)).listPosts();

    }

    @Test
    void findPostById() throws Exception{
        PostDTO postDTO = new PostDTO();
        postDTO.setTitle(POST_TITLE);
        postDTO.setBody(POST_BODY);

        when(postService.findPostById(anyLong())).thenReturn(postDTO);

        mockMvc.perform(get(POST_URL + "/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title", equalTo(POST_TITLE)))
                .andExpect(jsonPath("$.body", equalTo(POST_BODY)));

        verify(postService, times(1)).findPostById(anyLong());

    }
}













