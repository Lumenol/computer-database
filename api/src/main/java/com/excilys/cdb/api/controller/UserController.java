package com.excilys.cdb.api.controller;

import com.excilys.cdb.model.User;
import com.excilys.cdb.service.service.UserService;
import com.excilys.cdb.shared.dto.CreateUserDTO;
import com.excilys.cdb.shared.mapper.Mapper;
import com.excilys.cdb.shared.validator.Validator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final Validator<CreateUserDTO> createUserDTOValidator;
    private final Mapper<CreateUserDTO, User> createUserDTOUserMapper;

    public UserController(UserService userService, Validator<CreateUserDTO> createUserDTOValidator, Mapper<CreateUserDTO, User> createUserDTOUserMapper) {
        this.userService = userService;
        this.createUserDTOValidator = createUserDTOValidator;
        this.createUserDTOUserMapper = createUserDTOUserMapper;
    }

    @PostMapping
    public ResponseEntity create(@RequestBody CreateUserDTO createUserDTO) throws BindException {
        final BindException errors = new BindException(createUserDTO, "user");
        createUserDTOValidator.validate(createUserDTO, errors);
        if (errors.hasErrors()) {
            throw errors;
        }
        final User user = createUserDTOUserMapper.map(createUserDTO);
        userService.create(user);
        final URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(user.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable long id) {
        userService.deleteById(id);
    }

}
