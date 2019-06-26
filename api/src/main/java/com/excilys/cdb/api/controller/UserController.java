package com.excilys.cdb.api.controller;

import com.excilys.cdb.model.Role;
import com.excilys.cdb.model.User;
import com.excilys.cdb.service.service.UserService;
import com.excilys.cdb.shared.dto.CreateUserDTO;
import com.excilys.cdb.shared.dto.UserDTO;
import com.excilys.cdb.shared.mapper.Mapper;
import com.excilys.cdb.shared.pagination.Page;
import com.excilys.cdb.shared.pagination.PagingParameters;
import com.excilys.cdb.shared.utils.Utils;
import com.excilys.cdb.shared.validator.Validator;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final Validator<CreateUserDTO> createUserDTOValidator;
    private final Mapper<CreateUserDTO, User> createUserDTOUserMapper;
    private final PasswordEncoder passwordEncoder;
    private final Mapper<User, UserDTO> userToDto;
    private final PagingParameters pagingParameters;

    public UserController(UserService userService, Validator<CreateUserDTO> createUserDTOValidator, Mapper<CreateUserDTO, User> createUserDTOUserMapper, PasswordEncoder passwordEncoder, Mapper<User, UserDTO> userToDto, PagingParameters pagingParameters) {
        this.userService = userService;
        this.createUserDTOValidator = createUserDTOValidator;
        this.createUserDTOUserMapper = createUserDTOUserMapper;
        this.passwordEncoder = passwordEncoder;
        this.userToDto = userToDto;
        this.pagingParameters = pagingParameters;
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

    @GetMapping(params = {"!page", "!size"})
    public List<UserDTO> findAll() {
        return userService.findAll().stream().map(userToDto::map).collect(Collectors.toList());
    }

    @GetMapping
    public ResponseEntity<List<UserDTO>> findAll(@Validated @ModelAttribute Page page) throws UnsupportedEncodingException {
        final List<UserDTO> body = userService.findAll(page).stream().map(userToDto::map).collect(Collectors.toList());
        final long count = userService.count();
        long currrentPage = page.getPage();
        final long lastPage = Utils.indexLastPage(count, page.getSize());
        final String baseUrl = ServletUriComponentsBuilder.fromCurrentRequest().build().toString();
        final ResponseEntity.BodyBuilder response = ResponseEntity.ok();

        page.setPage(1);
        final String firstUrl = Utils.createPagingUrl(baseUrl, page, pagingParameters);
        final String first = com.excilys.cdb.api.utils.Utils.createLink(firstUrl, "first");
        response.header(HttpHeaders.LINK, first);

        if (currrentPage > 1) {
            page.setPage(currrentPage - 1);
            final String previousUrl = Utils.createPagingUrl(baseUrl, page, pagingParameters);
            final String previous = com.excilys.cdb.api.utils.Utils.createLink(previousUrl, "previous");
            response.header(HttpHeaders.LINK, previous);
        }
        if (currrentPage < lastPage) {
            page.setPage(currrentPage + 1);
            final String nextUrl = Utils.createPagingUrl(baseUrl, page, pagingParameters);
            final String next = com.excilys.cdb.api.utils.Utils.createLink(nextUrl, "next");
            response.header(HttpHeaders.LINK, next);
        }
        page.setPage(lastPage);
        final String lastUrl = Utils.createPagingUrl(baseUrl, page, pagingParameters);
        final String last = com.excilys.cdb.api.utils.Utils.createLink(lastUrl, "last");
        response.header(HttpHeaders.LINK, last);

        return response.body(body);
    }
}
