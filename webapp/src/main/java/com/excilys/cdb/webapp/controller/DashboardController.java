package com.excilys.cdb.webapp.controller;

import com.excilys.cdb.model.Computer;
import com.excilys.cdb.service.service.ComputerService;
import com.excilys.cdb.shared.dto.ComputerDTO;
import com.excilys.cdb.shared.mapper.ComputerToComputerDTOMapper;
import com.excilys.cdb.shared.pagination.OrderBy;
import com.excilys.cdb.shared.pagination.Page;
import com.excilys.cdb.shared.pagination.Pageable;
import com.excilys.cdb.shared.paging.PagingParameters;
import com.excilys.cdb.shared.paging.SortingParameters;
import com.excilys.cdb.shared.utils.Utils;
import com.excilys.cdb.webapp.paging.Paging;
import com.excilys.cdb.webapp.paging.Sorting;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.UnsupportedEncodingException;
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
        return "redirect:" + Utils.createPagingUrl(DASHBOARD, pageable, search, PARAMETER_SEARCH, pagingParameters, sortingParameters);
    }

}
