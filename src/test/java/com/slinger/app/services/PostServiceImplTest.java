package com.slinger.app.services;

import com.slinger.app.api.v1.mapper.PostMapper;
import com.slinger.app.api.v1.model.PostDTO;
import com.slinger.app.domian.Post;
import com.slinger.app.domian.User;
import com.slinger.app.repositories.PostRepository;
import com.slinger.app.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PostServiceImplTest {

    public static final String POST_TITLE = "Post Title";
    public static final String POST_BODY = "Post Body";
    public static final long ID = 1L;
    User user = User.builder().id(10L).name("name").build();

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

    @Test
    void findPostById() {
        //given
        Post post = new Post();
        post.setId(ID);
        post.setTitle(POST_TITLE);
        post.setBody(POST_BODY);
        post.setUser(user);

        when(postRepository.findById(anyLong())).thenReturn(Optional.of(post));

        //when
        PostDTO postDTO = postService.findPostById(anyLong());

        assertNotNull(postDTO);
        assertEquals(POST_TITLE, postDTO.getTitle());
        assertEquals(POST_BODY, postDTO.getBody());
    }

    @Test
    void createPost() {
        //given
        PostDTO postDTO = new PostDTO();
        postDTO.setBody(POST_BODY);
        postDTO.setTitle(POST_TITLE);

        Post savedPost = PostMapper.POST_MAPPER.postDTOToPost(postDTO);
        savedPost.setId(ID);

        when(postRepository.save(any(Post.class))).thenReturn(savedPost);

        //when
        PostDTO savedDTO = postService.createPost(postDTO);

        //then
        assertNotNull(savedDTO);
        assertEquals(POST_BODY, savedDTO.getBody());
        assertEquals(POST_TITLE, savedDTO.getTitle());
        verify(postRepository, times(1)).save(any(Post.class));
    }

    @Test
    void deletePostById() {
        Post post = new Post();
        post.setId(ID);
        post.setTitle(POST_TITLE);
        post.setBody(POST_BODY);

        postRepository.deleteById(ID);

        Optional<Post> deletedPost = postRepository.findById(ID);

        assertThrows(NoSuchElementException.class, () -> postRepository.findById(ID).get());
        verify(postRepository, times(1)).deleteById(anyLong());



    }
}



















