package com.excilys.cdb.controller.web;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.excilys.cdb.controller.web.pagination.Pagination;
import com.excilys.cdb.controller.web.pagination.PaginationParameters;
import com.excilys.cdb.controller.web.sorting.Sorting;
import com.excilys.cdb.controller.web.sorting.SortingParameters;
import com.excilys.cdb.dto.ComputerDTO;
import com.excilys.cdb.mapper.dto.ComputerToComputerDTOMapper;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.persistence.page.OrderBy;
import com.excilys.cdb.persistence.page.Page;
import com.excilys.cdb.persistence.page.Pageable;
import com.excilys.cdb.service.ComputerService;

@Controller
@RequestMapping({ "/dashboard", "/" })
public class DashboardController {

    private static final String PARAMETER_SEARCH = "search";
    private static final String PARAMETER_COMPUTERS = "computers";
    private static final String PARAMETER_NUMBER_OF_COMPUTERS = "numberOfComputers";
    private static final String DASHBOARD = "dashboard";
    private final ComputerService computerService;
    private final ComputerToComputerDTOMapper computerToComputerDTOMapper;
    private final Pagination pagination;
    private final Sorting sorting;

    public DashboardController(Pagination pagination, Sorting sorting, ComputerService computerService,
	    ComputerToComputerDTOMapper computerToComputerDTOMapper) {
	this.pagination = pagination;
	this.sorting = sorting;
	this.computerService = computerService;
	this.computerToComputerDTOMapper = computerToComputerDTOMapper;
    }

    @PostMapping
    public String deleteComputer(@RequestParam("selection") List<Long> removeComputersId,
	    @RequestParam(required = false) String search, @ModelAttribute("page") Page page,
	    @ModelAttribute("orderBy") OrderBy orderBy) throws UnsupportedEncodingException {
	removeComputersId.forEach(computerService::delete);
	final Pageable pageable = Pageable.builder().orderBy(orderBy).page(page).build();
	return redirectToPageNumber(pageable, search);
    }

    @GetMapping
    public ModelAndView computers(@RequestParam(required = false) String search, @ModelAttribute("page") Page page,
	    @ModelAttribute("orderBy") OrderBy orderBy) throws UnsupportedEncodingException {
	final long numberOfComputers = getComputerCount(search);
	final Pageable pageable = Pageable.builder().page(page).orderBy(orderBy).build();
	final Optional<Page> newPage = pagination.redirectIfPageOutOfRange(pageable.getPage(), numberOfComputers);
	if (newPage.isPresent()) {
	    pageable.setPage(newPage.get());
	    return new ModelAndView(redirectToPageNumber(pageable, search));
	}

	final List<ComputerDTO> computers = getComputers(pageable, search);

	final ModelAndView modelAndView = new ModelAndView(DASHBOARD);
	modelAndView.addObject(PARAMETER_NUMBER_OF_COMPUTERS, numberOfComputers);
	modelAndView.addObject(PARAMETER_COMPUTERS, computers);
	modelAndView.addObject(PARAMETER_SEARCH, search);
	pagination.setPageParameters(modelAndView, pageable.getPage(), numberOfComputers);
	sorting.setOrderBy(modelAndView, pageable.getOrderBy());

	return modelAndView;
    }

    private List<ComputerDTO> getComputers(Pageable pageable, String search) {
	final List<Computer> computers;
	if (Objects.isNull(search)) {
	    computers = computerService.findAll(pageable);
	} else {
	    computers = computerService.search(pageable, search);
	}
	return computers.stream().map(computerToComputerDTOMapper::map).collect(Collectors.toList());
    }

    private long getComputerCount(String search) {
	if (Objects.isNull(search)) {
	    return computerService.count();
	} else {
	    return computerService.countSearch(search);
	}

    }

    private String redirectToPageNumber(Pageable pageable, String search) throws UnsupportedEncodingException {
	final PaginationParameters paggingParameters = pagination.getParameters();
	final SortingParameters sortingParameters = sorting.getParameters();
	final StringBuilder stringBuilder = new StringBuilder(DASHBOARD).append("?");

	final Page page = pageable.getPage();
	stringBuilder.append(paggingParameters.getPage()).append("=").append(page.getPage());
	stringBuilder.append("&");
	stringBuilder.append(paggingParameters.getSize()).append("=").append(page.getSize());

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

	if (Objects.nonNull(orderBy.getMeaning())) {
	    stringBuilder.append("&");
	    stringBuilder.append(encode(sortingParameters.getMeaning())).append("=")
		    .append(encode(orderBy.getMeaning().getIdentifier()));
	}

	return "redirect:" + stringBuilder.toString();
    }

    private String encode(String s) throws UnsupportedEncodingException {
	return URLEncoder.encode(s, "UTF-8");
    }

}
