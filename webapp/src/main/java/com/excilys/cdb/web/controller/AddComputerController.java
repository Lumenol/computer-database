package com.excilys.cdb.web.controller;

import com.excilys.cdb.model.Computer;
import com.excilys.cdb.service.service.CompanyService;
import com.excilys.cdb.service.service.ComputerService;
import com.excilys.cdb.shared.dto.CompanyDTO;
import com.excilys.cdb.shared.dto.CreateComputerDTO;
import com.excilys.cdb.shared.mapper.CompanyToCompanyDTOMapper;
import com.excilys.cdb.shared.mapper.CreateComputerDTOToComputerMapper;
import com.excilys.cdb.shared.validator.CreateComputerValidator;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/computers/add")
public class AddComputerController {
    private static final String PARAMETER_COMPANIES = "companies";
    private static final String PARAMETER_SUCCESS = "success";
    private static final String ADD_COMPUTER_JSP = "addComputer";
    private static final String PARAMETER_COMPUTER = "computer";
    private final CompanyService companyService;
    private final CreateComputerDTOToComputerMapper createComputerDTOToComputerMapper;
    private final CompanyToCompanyDTOMapper companyToCompanyDTOMapper;
    private final ComputerService computerService;
    private final CreateComputerValidator createComputerValidator;

    public AddComputerController(CompanyService companyService,
                                 CreateComputerDTOToComputerMapper createComputerDTOToComputerMapper,
                                 CreateComputerValidator createComputerValidator, CompanyToCompanyDTOMapper companyToCompanyDTOMapper,
                                 ComputerService computerService) {
        super();
        this.companyService = companyService;
        this.createComputerDTOToComputerMapper = createComputerDTOToComputerMapper;
        this.createComputerValidator = createComputerValidator;
        this.companyToCompanyDTOMapper = companyToCompanyDTOMapper;
        this.computerService = computerService;
    }

    @InitBinder
    protected void initBinding(WebDataBinder dataBinder) {
        dataBinder.addValidators(createComputerValidator);
    }

    @GetMapping
    public String form() {
        return ADD_COMPUTER_JSP;
    }

    @ModelAttribute(PARAMETER_COMPUTER)
    public CreateComputerDTO computer() {
        return new CreateComputerDTO();
    }

    @ModelAttribute(PARAMETER_COMPANIES)
    public List<CompanyDTO> companies() {
        return companyService.findAll().stream().map(companyToCompanyDTOMapper::map).collect(Collectors.toList());
    }

    @PostMapping
    public ModelAndView add(@Validated @ModelAttribute(PARAMETER_COMPUTER) CreateComputerDTO computerDTO,
                            BindingResult result) {
        final ModelAndView modelAndView = new ModelAndView(ADD_COMPUTER_JSP);
        if (!result.hasErrors()) {
            final Computer computer = createComputerDTOToComputerMapper.map(computerDTO);
            computerService.create(computer);
            modelAndView.addObject(PARAMETER_COMPUTER, new CreateComputerDTO());
        }
        modelAndView.addObject(PARAMETER_SUCCESS, !result.hasErrors());
        return modelAndView;
    }
}
