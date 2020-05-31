package com.slinger.app.api.v1.mapper;

import com.slinger.app.api.v1.model.UserDTO;
import com.slinger.app.domian.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {

    UserMapper USER_MAPPER = Mappers.getMapper(UserMapper.class);

    UserDTO userToUserDTO(User user);

    User userDTOToUser(UserDTO userDTO);

}
