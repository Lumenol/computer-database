package com.excilys.cdb.api.controller;

import com.excilys.cdb.api.exception.ResourceNotFound;
import com.excilys.cdb.model.Company;
import com.excilys.cdb.service.service.CompanyService;
import com.excilys.cdb.shared.dto.CompanyDTO;
import com.excilys.cdb.shared.mapper.Mapper;
import com.excilys.cdb.shared.pagination.Page;
import com.excilys.cdb.shared.pagination.PagingParameters;
import com.excilys.cdb.shared.utils.Utils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/companies")
public class CompanyController {

    private final CompanyService companyService;
    private final Mapper<Company, CompanyDTO> companyToDto;
    private final PagingParameters pagingParameters;

    public CompanyController(CompanyService companyService, Mapper<Company, CompanyDTO> companyToDto, PagingParameters pagingParameters) {
        this.companyService = companyService;
        this.companyToDto = companyToDto;
        this.pagingParameters = pagingParameters;
    }

    @GetMapping(params = {"!page", "!size"})
    public List<CompanyDTO> findAll() {
        return companyService.findAll().stream().map(companyToDto::map).collect(Collectors.toList());
    }

    @GetMapping
    public ResponseEntity<List<CompanyDTO>> findAll(@Validated @ModelAttribute Page page) throws UnsupportedEncodingException {
        final List<CompanyDTO> body = companyService.findAll(page).stream().map(companyToDto::map).collect(Collectors.toList());
        final long count = companyService.count();
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
