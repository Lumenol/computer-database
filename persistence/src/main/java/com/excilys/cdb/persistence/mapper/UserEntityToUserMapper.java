package com.excilys.cdb.persistence.mapper;

import com.excilys.cdb.model.User;
import com.excilys.cdb.persistence.entity.UserEntity;
import com.excilys.cdb.shared.mapper.Mapper;
import org.springframework.stereotype.Component;

@Component
public class UserEntityToUserMapper implements Mapper<UserEntity, User> {
    @Override
    public User map(UserEntity userEntity) {
        return User.builder().id(userEntity.getId()).login(userEntity.getLogin()).password(userEntity.getPassword()).build();
    }
}
