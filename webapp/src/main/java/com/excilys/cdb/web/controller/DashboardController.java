package com.excilys.cdb.web.controller;

import com.excilys.cdb.model.Computer;
import com.excilys.cdb.service.ComputerService;
import com.excilys.cdb.shared.dto.ComputerDTO;
import com.excilys.cdb.shared.mapper.ComputerToComputerDTOMapper;
import com.excilys.cdb.shared.pagination.OrderBy;
import com.excilys.cdb.shared.pagination.Page;
import com.excilys.cdb.shared.pagination.Pageable;
import com.excilys.cdb.web.pagination.Paging;
import com.excilys.cdb.web.pagination.PagingParameters;
import com.excilys.cdb.web.sorting.Sorting;
import com.excilys.cdb.web.sorting.SortingParameters;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequestMapping({"/dashboard"})
public class DashboardController {

    private static final String PARAMETER_SEARCH = "search";
    private static final String PARAMETER_COMPUTERS = "computers";
    private static final String PARAMETER_NUMBER_OF_COMPUTERS = "numberOfComputers";
    private static final String DASHBOARD = "dashboard";
    private final ComputerService computerService;
    private final ComputerToComputerDTOMapper computerToComputerDTOMapper;
    private final Paging paging;
    private final Sorting sorting;

    public DashboardController(Paging paging, Sorting sorting, ComputerService computerService,
                               ComputerToComputerDTOMapper computerToComputerDTOMapper) {
        this.paging = paging;
        this.sorting = sorting;
        this.computerService = computerService;
        this.computerToComputerDTOMapper = computerToComputerDTOMapper;
    }

    @PostMapping
    public String deleteComputer(@RequestParam("selection") List<Long> removeComputersId,
                                 @RequestParam(required = false) String search, @ModelAttribute Page page, @ModelAttribute OrderBy orderBy)
            throws UnsupportedEncodingException {
        removeComputersId.forEach(computerService::delete);
        final Pageable pageable = Pageable.builder().orderBy(orderBy).page(page).build();
        return redirectToPageNumber(pageable, search);
    }

    @GetMapping
    public ModelAndView computers(@RequestParam(required = false) String search, @ModelAttribute Page page,
                                  @ModelAttribute OrderBy orderBy) throws UnsupportedEncodingException {
        final long numberOfComputers = getComputerCount(search);
        final Pageable pageable = Pageable.builder().page(page).orderBy(orderBy).build();
        final Optional<Page> newPage = paging.redirectIfPageOutOfRange(pageable.getPage(), numberOfComputers);
        if (newPage.isPresent()) {
            pageable.setPage(newPage.get());
            return new ModelAndView(redirectToPageNumber(pageable, search));
        }

        final List<ComputerDTO> computers = getComputers(pageable, search);

        final ModelAndView modelAndView = new ModelAndView(DASHBOARD);
        modelAndView.addObject(PARAMETER_NUMBER_OF_COMPUTERS, numberOfComputers);
        modelAndView.addObject(PARAMETER_COMPUTERS, computers);
        modelAndView.addObject(PARAMETER_SEARCH, search);
        paging.setPageParameters(modelAndView, pageable.getPage(), numberOfComputers);
        sorting.setOrderBy(modelAndView, pageable.getOrderBy());

        return modelAndView;
    }

    private List<ComputerDTO> getComputers(Pageable pageable, String search) {
        final List<Computer> computers;
        if (Objects.isNull(search)) {
            computers = computerService.findAll(pageable);
        } else {
            computers = computerService.searchByNameOrCompanyName(pageable, search);
        }
        return computers.stream().map(computerToComputerDTOMapper::map).collect(Collectors.toList());
    }

    private long getComputerCount(String search) {
        if (Objects.isNull(search)) {
            return computerService.count();
        } else {
            return computerService.countByNameOrCompanyName(search);
        }

    }

    private String redirectToPageNumber(Pageable pageable, String search) throws UnsupportedEncodingException {
        final PagingParameters pagingParameters = paging.getParameters();
        final SortingParameters sortingParameters = sorting.getParameters();
        final StringBuilder stringBuilder = new StringBuilder(DASHBOARD).append("?");

        final Page page = pageable.getPage();
        stringBuilder.append(pagingParameters.getPage()).append("=").append(page.getPage());
        stringBuilder.append("&");
        stringBuilder.append(pagingParameters.getSize()).append("=").append(page.getSize());

        if (Objects.nonNull(search)) {
            stringBuilder.append("&");
            stringBuilder.append(encode(PARAMETER_SEARCH)).append("=").append(encode(search));
        }

        final OrderBy orderBy = pageable.getOrderBy();
        if (Objects.nonNull(orderBy.getField())) {
            stringBuilder.append("&");
            stringBuilder.append(encode(sortingParameters.getOrderBy())).append("=")
                    .append(encode(orderBy.getField().getIdentifier()));
        }

        if (Objects.nonNull(orderBy.getDirection())) {
            stringBuilder.append("&");
            stringBuilder.append(encode(sortingParameters.getDirection())).append("=")
                    .append(encode(orderBy.getDirection().getIdentifier()));
        }

        return "redirect:" + stringBuilder.toString();
    }

    private String encode(String s) throws UnsupportedEncodingException {
        return URLEncoder.encode(s, "UTF-8");
    }

}
