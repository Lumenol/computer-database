package com.excilys.cdb.shared.mapper;

import org.springframework.stereotype.Component;

import com.excilys.cdb.model.User;
import com.excilys.cdb.shared.dto.CreateUserDTO;

@Component
public class CreateUserDTOToUserMapper implements Mapper<CreateUserDTO, User> {
	@Override
	public User map(CreateUserDTO from) {
		return User.builder().login(from.getLogin()).password(from.getPassword()).build();
	}
}
