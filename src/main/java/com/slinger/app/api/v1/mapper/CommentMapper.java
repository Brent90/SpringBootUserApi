package com.slinger.app.api.v1.mapper;

import com.slinger.app.api.v1.model.CommentDTO;
import com.slinger.app.domian.Comment;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CommentMapper {

    CommentMapper COMMENT_MAPPER = Mappers.getMapper(CommentMapper.class);

    CommentDTO commentToCommentDTO(Comment comment);

    Comment commentDTOToComment(CommentDTO commentDTO);
}
