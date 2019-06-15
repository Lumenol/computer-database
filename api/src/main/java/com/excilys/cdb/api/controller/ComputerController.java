package com.excilys.cdb.api.controller;

import com.excilys.cdb.api.exception.ResourceNotFound;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.service.service.ComputerService;
import com.excilys.cdb.shared.dto.ComputerDTO;
import com.excilys.cdb.shared.dto.CreateComputerDTO;
import com.excilys.cdb.shared.dto.UpdateComputerDTO;
import com.excilys.cdb.shared.mapper.Mapper;
import com.excilys.cdb.shared.pagination.OrderBy;
import com.excilys.cdb.shared.pagination.Page;
import com.excilys.cdb.shared.pagination.Pageable;
import com.excilys.cdb.shared.validator.Validator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/computers")
public class ComputerController {

    private final ComputerService computerService;
    private final Mapper<Computer, ComputerDTO> computerToDto;
    private final Mapper<CreateComputerDTO, Computer> createComputerDTOComputerMapper;
    private final Mapper<UpdateComputerDTO, Computer> updateComputerDTOComputerMapper;
    private final Validator<UpdateComputerDTO> updateComputerValidator;
    private final Validator<CreateComputerDTO> createComputerValidator;

    public ComputerController(ComputerService computerService, Mapper<Computer, ComputerDTO> computerToDto,
                              Mapper<CreateComputerDTO, Computer> createComputerDTOComputerMapper, Mapper<UpdateComputerDTO, Computer> updateComputerDTOComputerMapper, Validator<UpdateComputerDTO> updateComputerValidator,
                              Validator<CreateComputerDTO> createComputerValidator) {
        this.computerService = computerService;
        this.computerToDto = computerToDto;
        this.createComputerDTOComputerMapper = createComputerDTOComputerMapper;
        this.updateComputerDTOComputerMapper = updateComputerDTOComputerMapper;
        this.updateComputerValidator = updateComputerValidator;
        this.createComputerValidator = createComputerValidator;
    }

    @GetMapping
    public ResponseEntity<List<ComputerDTO>> findAll(@ModelAttribute Page page, @ModelAttribute OrderBy orderBy,
                                                     @RequestParam Optional<String> search) {
        final Pageable pageable = Pageable.builder().page(page).orderBy(orderBy).build();

        final List<ComputerDTO> body = search.map(s -> computerService.searchByNameOrCompanyName(pageable, s))
                .orElseGet(() -> computerService.findAll(pageable)).stream().map(computerToDto::map)
                .collect(Collectors.toList());

        return ResponseEntity.ok().body(body);
    }

    @GetMapping("/{id}")
    public ComputerDTO findById(@PathVariable long id) {
        return computerService.findById(id).map(computerToDto::map).orElseThrow(ResourceNotFound::new);
    }

    @GetMapping("/count")
    public long count() {
        return computerService.count();
    }

    @GetMapping("/exist/{id}")
    public boolean existById(@PathVariable long id) {
        return computerService.exist(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable long id) {
        computerService.delete(id);
    }


    @PostMapping
    public ResponseEntity create(@RequestBody CreateComputerDTO createComputerDTO) throws BindException {
        final BindException errors = new BindException(createComputerDTO, "computer");
        createComputerValidator.validate(createComputerDTO, errors);
        if (errors.hasErrors()) {
            throw errors;
        }
        final Computer computer = createComputerDTOComputerMapper.map(createComputerDTO);
        computerService.create(computer);

        final URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(computer.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @PutMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@RequestBody UpdateComputerDTO updateComputerDTO) throws BindException {
        final BindException errors = new BindException(updateComputerDTO, "computer");
        updateComputerValidator.validate(updateComputerDTO, errors);
        if (errors.hasErrors()) {
            throw errors;
        }
        computerService.update(updateComputerDTOComputerMapper.map(updateComputerDTO));
    }
}
