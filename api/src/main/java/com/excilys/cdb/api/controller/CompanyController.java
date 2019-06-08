package com.excilys.cdb.api.controller;

import com.excilys.cdb.api.exception.ResourceNotFound;
import com.excilys.cdb.model.Company;
import com.excilys.cdb.service.service.CompanyService;
import com.excilys.cdb.shared.dto.CompanyDTO;
import com.excilys.cdb.shared.mapper.Mapper;
import com.excilys.cdb.shared.pagination.Page;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/companies")
public class CompanyController {

    private final CompanyService companyService;
    private final Mapper<Company, CompanyDTO> companyToDto;

    public CompanyController(CompanyService companyService, Mapper<Company, CompanyDTO> companyToDto) {
        this.companyService = companyService;
        this.companyToDto = companyToDto;
    }

    @GetMapping
    public List<CompanyDTO> findAll(@Validated @ModelAttribute Page page) {
        return companyService.findAll(page).stream().map(companyToDto::map).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public CompanyDTO findById(@PathVariable long id) {
        return companyService.findById(id).map(companyToDto::map).orElseThrow(ResourceNotFound::new);
    }

    @GetMapping("/count")
    public long count() {
        return companyService.count();
    }

    @GetMapping("/exist/{id}")
    public boolean existById(@PathVariable long id) {
        return companyService.exist(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable long id) {
        companyService.delete(id);
    }

}
