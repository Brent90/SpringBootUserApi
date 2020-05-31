package com.slinger.app.api.v1.mapper;

import com.slinger.app.api.v1.model.PostDTO;
import com.slinger.app.domian.Post;
import com.slinger.app.domian.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.*;

class PostMapperTest {

    public static final String POST_BODY = "Post Body";
    public static final String POST_TITLE = "Post Title";
    PostMapper POST_MAPPER = Mappers.getMapper(PostMapper.class);

    @BeforeEach
    void setUp() {
    }

    @Test
    void postToPostDTO() {
        //given
        Post post = new Post();
        post.setUser(new User());
        post.setBody(POST_BODY);
        post.setTitle(POST_TITLE);

        //when
        PostDTO postDTO = POST_MAPPER.postToPostDTO(post);

        //then
        assertNotNull(postDTO);
        assertEquals(POST_BODY, postDTO.getBody());
        assertEquals(POST_TITLE, postDTO.getTitle());
    }

    @Test
    void postDTOToPost() {
        //given
        PostDTO postDTO = new PostDTO();
        postDTO.setBody(POST_BODY);
        postDTO.setTitle(POST_TITLE);

        //when
        Post post = POST_MAPPER.postDTOToPost(postDTO);

        //then
        assertNotNull(post);
        assertEquals(POST_BODY, post.getBody());
        assertEquals(POST_TITLE, post.getTitle());
    }
}
