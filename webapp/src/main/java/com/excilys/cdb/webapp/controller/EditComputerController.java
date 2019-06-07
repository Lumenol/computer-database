package com.excilys.cdb.webapp.controller;

import com.excilys.cdb.model.Computer;
import com.excilys.cdb.service.service.CompanyService;
import com.excilys.cdb.service.service.ComputerService;
import com.excilys.cdb.shared.dto.CompanyDTO;
import com.excilys.cdb.shared.dto.UpdateComputerDTO;
import com.excilys.cdb.shared.mapper.CompanyToCompanyDTOMapper;
import com.excilys.cdb.shared.mapper.ComputerToUpdateComputerDTOMapper;
import com.excilys.cdb.shared.mapper.UpdateComputerDTOToComputerMapper;
import com.excilys.cdb.shared.validator.UpdateComputerValidator;
import com.excilys.cdb.shared.validator.Validator;
import com.excilys.cdb.webapp.exception.BadArgumentRequestException;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/computers/edit/{id}")
public class EditComputerController {
    private static final String PARAMETER_COMPUTER = "computer";
    private static final String PARAMETER_SUCCESS = "success";
    private static final String PARAMETER_COMPANIES = "companies";
    private static final String EDIT_COMPUTER_JSP = "editComputer";
    private final CompanyService companyService;
    private final UpdateComputerDTOToComputerMapper updateComputerDTOToComputerMapper;
    private final Validator<UpdateComputerDTO> updateComputerValidator;
    private final CompanyToCompanyDTOMapper companyToCompanyDTOMapper;
    private final ComputerService computerService;
    private final ComputerToUpdateComputerDTOMapper computerToUpdateComputerDTOMapper;

    public EditComputerController(CompanyService companyService,
    		UpdateComputerDTOToComputerMapper updateComputerDTOToComputerMapper,
    		Validator<UpdateComputerDTO> updateComputerValidator, CompanyToCompanyDTOMapper companyToCompanyDTOMapper,
                                  ComputerService computerService, ComputerToUpdateComputerDTOMapper computerToUpdateComputerDTOMapper) {
        super();
        this.companyService = companyService;
        this.updateComputerDTOToComputerMapper = updateComputerDTOToComputerMapper;
        this.updateComputerValidator = updateComputerValidator;
        this.companyToCompanyDTOMapper = companyToCompanyDTOMapper;
        this.computerService = computerService;
        this.computerToUpdateComputerDTOMapper = computerToUpdateComputerDTOMapper;
    }

    @InitBinder
    protected void initBinding(WebDataBinder dataBinder) {
        dataBinder.addValidators(updateComputerValidator);
    }

    @ModelAttribute(PARAMETER_COMPANIES)
    public List<CompanyDTO> companies() {
        return companyService.findAll().stream().map(companyToCompanyDTOMapper::map).collect(Collectors.toList());
    }

    @GetMapping
    public ModelAndView form(@PathVariable long id) {
        final UpdateComputerDTO computer = computerService.findById(id).map(computerToUpdateComputerDTOMapper::map)
                .orElseThrow(BadArgumentRequestException::new);

        final ModelAndView modelAndView = new ModelAndView(EDIT_COMPUTER_JSP);
        modelAndView.addObject(PARAMETER_COMPUTER, computer);
        return modelAndView;
    }

    @PostMapping
    public ModelAndView edit(@Validated @ModelAttribute(PARAMETER_COMPUTER) UpdateComputerDTO computerDTO,
                             BindingResult result) {

        final ModelAndView modelAndView = new ModelAndView(EDIT_COMPUTER_JSP);
        if (!result.hasErrors()) {
            final Computer computer = updateComputerDTOToComputerMapper.map(computerDTO);
            computerService.update(computer);
        }
        modelAndView.addObject(PARAMETER_SUCCESS, !result.hasErrors());
        return modelAndView;
    }
}
