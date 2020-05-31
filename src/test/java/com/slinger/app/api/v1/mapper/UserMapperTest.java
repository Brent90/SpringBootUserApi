package com.slinger.app.api.v1.mapper;

import com.slinger.app.api.v1.model.UserDTO;
import com.slinger.app.domian.User;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.*;

class UserMapperTest {

    public static final String PERSON_NAME = "Person Name";
    public static final String EMAIL = "someemail@email.com";
    public static final String USERNAME = "username99";

    UserMapper USER_MAPPER = Mappers.getMapper(UserMapper.class);

    @Test
    void userToUserDTO() {
        //given
        User user = new User();
        user.setName(PERSON_NAME);
        user.setEmail(EMAIL);
        user.setUsername(USERNAME);

        //when
        UserDTO userDTO = USER_MAPPER.userToUserDTO(user);

        //then
        assertNotNull(userDTO);
        assertEquals(PERSON_NAME, userDTO.getName());
        assertEquals(EMAIL, userDTO.getEmail());
        assertEquals(USERNAME, userDTO.getUsername());
    }
}
