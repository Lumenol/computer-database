package com.excilys.cdb.persistence.mapper;

import com.excilys.cdb.model.User;
import com.excilys.cdb.persistence.entity.UserEntity;
import com.excilys.cdb.shared.mapper.Mapper;
import org.springframework.stereotype.Component;

@Component
public class UserToUserEntityMapper implements Mapper<User, UserEntity> {
    @Override
    public UserEntity map(User user) {
        return UserEntity.builder().id(user.getId()).login(user.getLogin()).password(user.getPassword()).build();
    }
}
