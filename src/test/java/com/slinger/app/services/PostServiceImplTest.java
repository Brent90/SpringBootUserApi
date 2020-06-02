package com.slinger.app.services;

import com.slinger.app.api.v1.mapper.PostMapper;
import com.slinger.app.api.v1.model.PostDTO;
import com.slinger.app.domian.Post;
import com.slinger.app.repositories.PostRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PostServiceImplTest {

    @Mock
    PostRepository postRepository;

    PostService postService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        postService = new PostServiceImpl(postRepository, PostMapper.POST_MAPPER);
    }

    @Test
    void listPosts() {
        //given
        List<Post> posts = Arrays.asList(new Post(), new Post(), new Post());
        int size = posts.size();

        when(postRepository.findAll()).thenReturn(posts);

        //when
        List<PostDTO> postDTOS = postService.listPosts();

        //then
        assertNotNull(postDTOS);
        assertEquals(size, postDTOS.size());
        verify(postRepository, times(1)).findAll();

    }
}
