package com.controller;

import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.business.dto.CompanyDTO;
import com.business.dto.ComputerDTO;
import com.business.dto.UpdateComputerDTO;
import com.business.exception.ComputerNotFoundException;
import com.business.exception.ValidatorException;
import com.business.service.CompanyService;
import com.business.service.ComputerService;
import com.ui.Action;
import com.ui.Ui;
import com.ui.dto.CompanyListDTO;
import com.ui.dto.ComputerDetailDTO;
import com.ui.dto.ComputerListDTO;
import com.ui.dto.CreateComputerDTO;
import com.ui.dto.PageDTO;

public class Controller {

    private static final int PAGE_SIZE = 10;

    private static enum State {
	CREATE_COMPUTER, DELETE_COMPUTER, QUIT, SHOW_DETAIL_COMPUTER, SHOW_LIST_COMPANY, SHOW_LIST_COMPUTER, SHOW_MENU,
	UPDATE_COMPUTER
    }

    private final Function<CompanyDTO, CompanyListDTO> companyDTOToCompanyListDTO;
    private final CompanyService companyService;
    private final Function<ComputerDTO, ComputerDetailDTO> computerDTOToComputerDetailDTO;

    private final Function<ComputerDTO, ComputerListDTO> computerDTOToComputerListDTO;
    private final ComputerService computerService;
    private final Function<CreateComputerDTO, com.business.dto.CreateComputerDTO> createComputerDTOUiToBusiness;
    private State state = State.SHOW_MENU;
    private final Ui ui;

    private final Function<com.ui.dto.UpdateComputerDTO, com.business.dto.UpdateComputerDTO> updateComputerDTOUiToBusiness;
    private final BiFunction<Long, Long, List<CompanyListDTO>> getPageCompanyDTO;
    private final BiFunction<Long, Long, List<ComputerListDTO>> getPageComputerDTO;

    public Controller(Ui ui, ComputerService computerService, CompanyService companyService,
	    Function<CompanyDTO, CompanyListDTO> companyDTOToCompanyListDTO,
	    Function<ComputerDTO, ComputerDetailDTO> computerDTOToComputerDetailDTO,
	    Function<ComputerDTO, ComputerListDTO> computerDTOToComputerListDTO,
	    Function<CreateComputerDTO, com.business.dto.CreateComputerDTO> createComputerDTOUiToBusiness,
	    Function<com.ui.dto.UpdateComputerDTO, UpdateComputerDTO> updateComputerDTOUiToBusiness) {
	super();
	this.ui = ui;
	this.computerService = computerService;
	this.companyService = companyService;
	this.companyDTOToCompanyListDTO = companyDTOToCompanyListDTO;
	this.computerDTOToComputerDetailDTO = computerDTOToComputerDetailDTO;
	this.computerDTOToComputerListDTO = computerDTOToComputerListDTO;
	this.createComputerDTOUiToBusiness = createComputerDTOUiToBusiness;
	this.updateComputerDTOUiToBusiness = updateComputerDTOUiToBusiness;

	getPageCompanyDTO = (Long from, Long to) -> companyService.findAll(from, to).stream()
		.map(companyDTOToCompanyListDTO::apply).collect(Collectors.toList());

	getPageComputerDTO = (Long from, Long to) -> computerService.findAll(from, to).stream()
		.map(computerDTOToComputerListDTO::apply).collect(Collectors.toList());
    }

    private void createComputer() {
	CreateComputerDTO dtoUi = ui.getCreateComputerDTO();
	try {
	    computerService.create(createComputerDTOUiToBusiness.apply(dtoUi));
	} catch (ValidatorException e) {
	    ui.showMessage(e.getMessage());
	}
	state = State.SHOW_MENU;
    }

    private void deleteComputer() {
	long id = ui.getComputerId();
	computerService.delete(id);
	state = State.SHOW_MENU;
    }

    private void showDetailComputer() {
	long id = ui.getComputerId();
	if (computerService.exist(id)) {
	    ComputerDTO computer = computerService.findById(id);
	    ui.showDetail(computerDTOToComputerDetailDTO.apply(computer));
	} else {
	    ui.showComputerNotFound();
	}
	state = State.SHOW_MENU;
    }

    private <T> void showPagginer(BiFunction<Long, Long, List<T>> getPage, Function<PageDTO<T>,Action> showPage) {
	long from = 0;
	while (true) {
	    List<T> listDTO = getPage.apply(from, from + PAGE_SIZE);
	    PageDTO<T> page = new PageDTO<T>(listDTO, from > 0, listDTO.size() == PAGE_SIZE);
	    Action input = showPage.apply(page);
	    switch (input) {
	    case PREVIOUS:
		from = Math.max(0, from - PAGE_SIZE);
		break;
	    case NEXT:
		from += PAGE_SIZE;
		break;
	    case RETURN:
		state = State.SHOW_MENU;
		return;
	    default:
		break;
	    }
	}
    }

    private void showListCampany() {
	showPagginer(getPageCompanyDTO, ui::showListCompany);
    }

    private void showListComputer() {
	showPagginer(getPageComputerDTO, ui::showListComputer);
    }

    private void showMenu() {
	switch (ui.getInputMenu()) {
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
	case UPDATE_COMPUTER:
	    state = State.UPDATE_COMPUTER;
	    break;
	case QUIT:
	    state = State.QUIT;
	    break;
	default:
	    break;
	}
    }

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
	    case UPDATE_COMPUTER:
		updateComputer();
		break;
	    case QUIT:
		ui.showGoodBye();
		return;
	    default:
		break;
	    }
	}
    }

    private void updateComputer() {
	com.ui.dto.UpdateComputerDTO dtoUi = ui.getUpdateComputerDTO();
	try {
	    computerService.update(updateComputerDTOUiToBusiness.apply(dtoUi));
	} catch (ComputerNotFoundException e) {
	    ui.showComputerNotFound();
	} catch (ValidatorException e) {
	    ui.showMessage(e.getMessage());
	}
	state = State.SHOW_MENU;
    }

}
