package com.excilys.cdb.shared.mapper;

import com.excilys.cdb.model.Role;
import com.excilys.cdb.model.User;
import com.excilys.cdb.shared.dto.UserDTO;
import org.springframework.stereotype.Component;

@Component
public class UserToUserDTOMapper implements Mapper<User, UserDTO> {
    @Override
    public UserDTO map(User from) {
        final UserDTO userDTO = new UserDTO();
        userDTO.setId(from.getId());
        userDTO.setLogin(from.getLogin());
        userDTO.setAdmin(from.getRoles().contains(Role.ADMIN));
        return userDTO;
    }
}
