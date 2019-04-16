package com.controller;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import com.metier.dto.CompanyDTO;
import com.metier.dto.ComputerDTO;
import com.metier.exception.ComputerNotFoundException;
import com.metier.service.CompanyService;
import com.metier.service.ComputerService;
import com.ui.Action;
import com.ui.Ui;
import com.ui.dto.CompanyListDTO;
import com.ui.dto.ComputerDetailDTO;
import com.ui.dto.ComputerListDTO;
import com.ui.dto.CreateComputerDTO;

public class Controller {

    private static enum State {
	SHOW_MENU, SHOW_LIST_COMPUTER, SHOW_DETAIL_COMPUTER, DELETE_COMPUTER, CREATE_COMPUTER, SHOW_LIST_COMPANY
    }

    private final Ui ui;
    private final ComputerService computerService;
    private final CompanyService companyService;

    private State state;

    public void start() {
	while (true) {
	    switch (state) {
	    case SHOW_MENU:
		showMenu();
		break;
	    case SHOW_LIST_COMPUTER:
		showListComputer();
		break;
	    case SHOW_DETAIL_COMPUTER:
		showDetailComputer();
		break;
	    case DELETE_COMPUTER:
		deleteComputer();
		break;
	    case CREATE_COMPUTER:
		createComputer();
		break;
	    case SHOW_LIST_COMPANY:
		showListCampany();
		break;
	    default:
		break;
	    }
	}
    }

    private void showListCampany() {
	List<CompanyDTO> findAll = companyService.findAll();
	List<CompanyListDTO> listDTO = findAll.stream().map(Controller::toCompanyListDTO).collect(Collectors.toList());
	ui.showListCompany(listDTO);
	state = State.SHOW_MENU;
    }

    private void createComputer() {
	CreateComputerDTO dtoUi = ui.getCreateComputerDTO();
	com.metier.dto.CreateComputerDTO dtoMetier = new com.metier.dto.CreateComputerDTO();

	dtoMetier.setName(dtoUi.getName());
	try {
	    dtoMetier.setMannufacturerId(Long.valueOf(dtoUi.getMannufacturer()));
	} catch (NumberFormatException e) {
	    e.printStackTrace();
	}

	try {
	    dtoMetier.setIntroduced(LocalDate.parse(dtoUi.getIntroduced()));
	} catch (DateTimeException e) {
	    e.printStackTrace();
	}

	try {
	    dtoMetier.setIntroduced(LocalDate.parse(dtoUi.getDiscontinued()));
	} catch (DateTimeException e) {
	    e.printStackTrace();
	}

	computerService.create(dtoMetier);

	state = State.SHOW_MENU;
    }

    private void deleteComputer() {
	long id = ui.getComputerId();
	computerService.delete(id);
	state = State.SHOW_MENU;
    }

    private void showDetailComputer() {
	long id = ui.getComputerId();
	try {
	    ComputerDTO computer = computerService.findById(id);
	    ui.showDetail(toComputerDetailDTO(computer));
	} catch (ComputerNotFoundException e) {
	    ui.showComputerNotFound();
	}
	state = State.SHOW_MENU;

    }

    private static ComputerListDTO toComputerListDTO(ComputerDTO computer) {
	ComputerListDTO computerListDTO = new ComputerListDTO();
	computerListDTO.setId(Long.toString(computer.getId()));
	computerListDTO.setName(computer.getName());
	return computerListDTO;
    }

    private static CompanyListDTO toCompanyListDTO(CompanyDTO company) {
	CompanyListDTO companyListDTO = new CompanyListDTO();
	companyListDTO.setId(Long.toString(company.getId()));
	companyListDTO.setName(company.getName());
	return companyListDTO;
    }

    private static ComputerDetailDTO toComputerDetailDTO(ComputerDTO computer) {
	ComputerDetailDTO computerDetailDTO = new ComputerDetailDTO();
	computerDetailDTO.setId(Long.toString(computer.getId()));
	computerDetailDTO.setName(computer.getName());

	if (Objects.nonNull(computer.getIntroduced())) {
	    computerDetailDTO.setIntroduced(computer.getIntroduced().toString());
	}

	if (Objects.nonNull(computer.getDiscontinued())) {
	    computerDetailDTO.setIntroduced(computer.getDiscontinued().toString());
	}

	if (Objects.nonNull(computer.getMannufacturer())) {
	    computerDetailDTO.setMannufacturer(computer.getMannufacturer().getName());
	}

	return computerDetailDTO;
    }

    private void showListComputer() {
	List<ComputerDTO> computers = computerService.findAll();
	List<ComputerListDTO> convertedComputers = computers.stream().map(Controller::toComputerListDTO)
		.collect(Collectors.toList());
	ui.showListComputer(convertedComputers);
	state = State.SHOW_MENU;
    }

    private void showMenu() {
	ui.showMenu();
	Action action = ui.getInput();
	switch (action) {
	case LIST_COMPUTER:
	    state = State.SHOW_LIST_COMPUTER;
	    break;
	case DETAIL_COMPUTER:
	    state = State.SHOW_DETAIL_COMPUTER;
	    break;
	case DELETE_COMPUTER:
	    state = State.DELETE_COMPUTER;
	    break;
	case CREATE_COMPUTER:
	    state = State.CREATE_COMPUTER;
	    break;
	case LIST_COMPANY:
	    state = State.SHOW_LIST_COMPANY;
	    break;
	default:
	    break;
	}
    }

    public Controller(Ui ui, ComputerService computerService, CompanyService companyService) {
	super();
	this.ui = ui;
	this.computerService = computerService;
	this.companyService = companyService;
	state = State.SHOW_MENU;
    }

}
