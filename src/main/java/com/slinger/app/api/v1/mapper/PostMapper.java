package com.slinger.app.api.v1.mapper;

import com.slinger.app.api.v1.model.PostDTO;
import com.slinger.app.domian.Post;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.web.bind.annotation.PostMapping;

@Mapper
public interface PostMapper {

    PostMapper POST_MAPPER = Mappers.getMapper(PostMapper.class);

    PostDTO postToPostDTO(Post post);

    Post postDTOToPost(PostDTO postDTO);

}
