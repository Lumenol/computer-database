package com.excilys.cdb.api.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.excilys.cdb.api.exception.ResourceNotFound;
import com.excilys.cdb.model.Company;
import com.excilys.cdb.service.service.CompanyService;
import com.excilys.cdb.shared.dto.CompanyDTO;
import com.excilys.cdb.shared.mapper.Mapper;
import com.excilys.cdb.shared.pagination.Page;

@RestController
@RequestMapping("/api/companies")
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
	
	@DeleteMapping("/{id}")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public void deleteById(@PathVariable long id) {
		companyService.delete(id);
	}
	
}
