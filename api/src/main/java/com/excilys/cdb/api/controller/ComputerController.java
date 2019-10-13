package com.excilys.cdb.api.controller;

import com.excilys.cdb.api.exception.ResourceNotFound;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.service.service.ComputerService;
import com.excilys.cdb.shared.dto.ComputerDTO;
import com.excilys.cdb.shared.dto.CreateComputerDTO;
import com.excilys.cdb.shared.dto.UpdateComputerDTO;
import com.excilys.cdb.shared.mapper.Mapper;
import com.excilys.cdb.shared.pagination.*;
import com.excilys.cdb.shared.utils.Utils;
import com.excilys.cdb.shared.validator.CreateComputerValidator;
import com.excilys.cdb.shared.validator.UpdateComputerValidator;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/computers")
public class ComputerController {

    private static final String SEARCH_PARAMETER = "search";
    private final ComputerService computerService;
    private final Mapper<Computer, ComputerDTO> computerToDto;
    private final Mapper<CreateComputerDTO, Computer> createComputerDTOComputerMapper;
    private final Mapper<UpdateComputerDTO, Computer> updateComputerDTOComputerMapper;
    private final UpdateComputerValidator updateComputerValidator;
    private final CreateComputerValidator createComputerValidator;
    private final PagingParameters pagingParameters;
    private final SortingParameters sortingParameters;

    public ComputerController(ComputerService computerService, Mapper<Computer, ComputerDTO> computerToDto,
                              Mapper<CreateComputerDTO, Computer> createComputerDTOComputerMapper, Mapper<UpdateComputerDTO, Computer> updateComputerDTOComputerMapper, UpdateComputerValidator updateComputerValidator,
                              CreateComputerValidator createComputerValidator, PagingParameters pagingParameters, SortingParameters sortingParameters) {
        this.computerService = computerService;
        this.computerToDto = computerToDto;
        this.createComputerDTOComputerMapper = createComputerDTOComputerMapper;
        this.updateComputerDTOComputerMapper = updateComputerDTOComputerMapper;
        this.updateComputerValidator = updateComputerValidator;
        this.createComputerValidator = createComputerValidator;
        this.pagingParameters = pagingParameters;
        this.sortingParameters = sortingParameters;
    }

    @GetMapping
    public ResponseEntity<List<ComputerDTO>> findAll(@ModelAttribute Page page, @ModelAttribute OrderBy orderBy,
                                                     @RequestParam(required = false) String search) throws UnsupportedEncodingException {
        final Pageable pageable = Pageable.builder().page(page).orderBy(orderBy).build();

        final Optional<String> searchOptional = Optional.ofNullable(search);
        final long count = searchOptional.map(computerService::countByNameOrCompanyName).orElseGet(computerService::count);

        final List<ComputerDTO> body = searchOptional.map(s -> computerService.searchByNameOrCompanyName(pageable, s))
                .orElseGet(() -> computerService.findAll(pageable)).stream().map(computerToDto::map)
                .collect(Collectors.toList());

        long currrentPage = pageable.getPage().getPage();
        final long lastPage = Utils.indexLastPage(count, page.getSize());
        final String baseUrl = ServletUriComponentsBuilder.fromCurrentRequest().build().toString();
        final ResponseEntity.BodyBuilder response = ResponseEntity.ok();


        pageable.getPage().setPage(1);
        final String firstUrl = Utils.createPagingUrl(baseUrl, pageable, search, SEARCH_PARAMETER, pagingParameters, sortingParameters);
        final String first = com.excilys.cdb.api.utils.Utils.createLink(firstUrl, "first");
        response.header(HttpHeaders.LINK, first);

        if (currrentPage > 1) {
            pageable.getPage().setPage(currrentPage - 1);
            final String previousUrl = Utils.createPagingUrl(baseUrl, pageable, search, SEARCH_PARAMETER, pagingParameters, sortingParameters);
            final String previous = com.excilys.cdb.api.utils.Utils.createLink(previousUrl, "previous");
            response.header(HttpHeaders.LINK, previous);
        }
        if (currrentPage < lastPage) {
            pageable.getPage().setPage(currrentPage + 1);
            final String nextUrl = Utils.createPagingUrl(baseUrl, pageable, search, SEARCH_PARAMETER, pagingParameters, sortingParameters);
            final String next = com.excilys.cdb.api.utils.Utils.createLink(nextUrl, "next");
            response.header(HttpHeaders.LINK, next);
        }
        pageable.getPage().setPage(lastPage);
        final String lastUrl = Utils.createPagingUrl(baseUrl, pageable, search, SEARCH_PARAMETER, pagingParameters, sortingParameters);
        final String last = com.excilys.cdb.api.utils.Utils.createLink(lastUrl, "last");
        response.header(HttpHeaders.LINK, last);

        return response.body(body);
    }

    @GetMapping("/{id}")
    public ComputerDTO findById(@PathVariable long id) {
        return computerService.findById(id).map(computerToDto::map).orElseThrow(ResourceNotFound::new);
    }

    @GetMapping("/count")
    public long count(@RequestParam(required = false) String search) {
        return Optional.ofNullable(search).map(computerService::countByNameOrCompanyName).orElseGet(computerService::count);
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
