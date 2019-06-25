package com.excilys.cdb.api.controller;

import com.excilys.cdb.model.Role;
import com.excilys.cdb.model.User;
import com.excilys.cdb.service.service.UserService;
import com.excilys.cdb.shared.dto.CreateUserDTO;
import com.excilys.cdb.shared.mapper.Mapper;
import com.excilys.cdb.shared.validator.Validator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Objects;
import java.util.Set;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final Validator<CreateUserDTO> createUserDTOValidator;
    private final Mapper<CreateUserDTO, User> createUserDTOUserMapper;
    private final PasswordEncoder passwordEncoder;

    public UserController(UserService userService, Validator<CreateUserDTO> createUserDTOValidator, Mapper<CreateUserDTO, User> createUserDTOUserMapper, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.createUserDTOValidator = createUserDTOValidator;
        this.createUserDTOUserMapper = createUserDTOUserMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity create(@RequestBody CreateUserDTO createUserDTO) throws BindException {
        if (Objects.isNull(createUserDTO.getPasswordCheck())) {
            createUserDTO.setPasswordCheck(createUserDTO.getPassword());
        }

        final BindException errors = new BindException(createUserDTO, "user");
        createUserDTOValidator.validate(createUserDTO, errors);
        if (errors.hasErrors()) {
            throw errors;
        }
        final User user = createUserDTOUserMapper.map(createUserDTO);
        user.addRole(Role.USER);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userService.create(user);
        final URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(user.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable long id) {
        userService.deleteById(id);
    }

    @PutMapping("/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void updateRoles(@PathVariable Long id, @RequestBody Set<Role> roles) {
        userService.updateRoles(id, roles);
    }


}
