package com.excilys.cdb.shared.mapper;

import com.excilys.cdb.model.User;
import com.excilys.cdb.shared.dto.CreateUserDTO;
import org.springframework.stereotype.Component;

@Component
public class CreateUserDTOToUserMapper implements Mapper<CreateUserDTO, User> {
    @Override
    public User map(CreateUserDTO from) {
        return User.builder().login(from.getLogin()).password(from.getPassword()).build();
    }
}
