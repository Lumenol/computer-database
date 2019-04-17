package com;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.function.Function;
import java.util.function.LongFunction;

import com.controller.Controller;
import com.infra.dao.ConnectionFactory;
import com.infra.dao.jdbc.CompanyDaoJDBC;
import com.infra.dao.jdbc.ComputerDaoJDBC;
import com.metier.ValidatorFactory;
import com.metier.dao.CompanyDAO;
import com.metier.dao.ComputerDAO;
import com.metier.dto.CompanyDTO;
import com.metier.dto.ComputerDTO;
import com.metier.dto.CreateComputerDTO;
import com.metier.dto.UpdateComputerDTO;
import com.metier.entite.Company;
import com.metier.entite.Computer;
import com.metier.exception.CompanyNotFoundException;
import com.metier.mapper.CompanyToCompanyDTOMapper;
import com.metier.mapper.ComputerToComputerDTOMapper;
import com.metier.mapper.CreateComputerDTOToComputer;
import com.metier.mapper.UpdateComputerDTOToComputer;
import com.metier.service.CompanyService;
import com.metier.service.CompanyServiceImpl;
import com.metier.service.ComputerServiceImpl;
import com.metier.validator.CreateComputerValidator;
import com.metier.validator.UpdateComputerValidator;
import com.ui.cli.CliUi;

public class Main {

    public static void main(String[] args) {
	CliUi ui = new CliUi();
	ConnectionFactory connectionFactory = () -> {
	    try {
		return DriverManager.getConnection(
			"jdbc:mysql://localhost:3306/computer-database-db?zeroDateTimeBehavior=CONVERT_TO_NULL&serverTimezone=UTC",
			"admincdb", "qwerty1234");
	    } catch (SQLException e) {
		e.printStackTrace();
	    }
	    return null;
	};

	ComputerDAO computerDAO = new ComputerDaoJDBC(connectionFactory);
	CompanyDAO companyDAO = new CompanyDaoJDBC(connectionFactory);

	ValidatorFactory<CreateComputerDTO> createValidatorFactory = CreateComputerValidator::new;
	ValidatorFactory<UpdateComputerDTO> updateValidatorFactory = UpdateComputerValidator::new;

	LongFunction<Company> findCompanyById = id -> companyDAO.findById(id)
		.orElseThrow(() -> new CompanyNotFoundException(id));

	Function<Computer, ComputerDTO> computerToComputerDTO = new ComputerToComputerDTOMapper();
	Function<UpdateComputerDTO, Computer> updateComputerDTOToComputer = new UpdateComputerDTOToComputer(
		findCompanyById);
	Function<CreateComputerDTO, Computer> createComputerDTOToComputer = new CreateComputerDTOToComputer(
		findCompanyById);

	ComputerServiceImpl computerService = new ComputerServiceImpl(computerDAO, createValidatorFactory,
		updateValidatorFactory, computerToComputerDTO, updateComputerDTOToComputer,
		createComputerDTOToComputer);

	Function<Company, CompanyDTO> companyToCompanyDTO = new CompanyToCompanyDTOMapper();
	CompanyService companyService = new CompanyServiceImpl(companyDAO, companyToCompanyDTO);

	Controller controller = new Controller(ui, computerService, companyService);
	controller.start();
    }

}
