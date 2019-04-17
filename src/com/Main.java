package com;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.function.Function;
import java.util.function.LongFunction;

import com.controller.Controller;
import com.infra.dao.ConnectionFactory;
import com.infra.dao.jdbc.CompanyDaoJDBC;
import com.infra.dao.jdbc.ComputerDaoJDBC;
import com.infra.dao.mapper.ResultSetToCompanyMapper;
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
import com.metier.mapper.CreateComputerDTOToComputerMapper;
import com.metier.mapper.UpdateComputerDTOToComputerMapper;
import com.metier.service.CompanyService;
import com.metier.service.CompanyServiceImpl;
import com.metier.service.ComputerService;
import com.metier.service.ComputerServiceImpl;
import com.metier.validator.CreateComputerValidator;
import com.metier.validator.UpdateComputerValidator;
import com.ui.Ui;
import com.ui.cli.CliUi;

public class Main {

    public static void main(String[] args) {
	Ui ui = ui();
	ConnectionFactory connectionFactory = connectionFactory();
	CompanyDAO companyDAO = companyDAO(connectionFactory);
	ComputerService computerService = computerService(connectionFactory, companyDAO);
	CompanyService companyService = companyService(connectionFactory, companyDAO);

	Controller controller = controller(ui, computerService, companyService);
	controller.start();
    }

    private static Controller controller(Ui ui, ComputerService computerService, CompanyService companyService) {
	return new Controller(ui, computerService, companyService);
    }

    private static ConnectionFactory connectionFactory() {
	return () -> {
	    try {
		return DriverManager.getConnection(
			"jdbc:mysql://localhost:3306/computer-database-db?zeroDateTimeBehavior=CONVERT_TO_NULL&serverTimezone=UTC",
			"admincdb", "qwerty1234");
	    } catch (SQLException e) {
		e.printStackTrace();
	    }
	    return null;
	};
    }

    private static Ui ui() {
	return new CliUi();
    }

    private static CompanyDAO companyDAO(ConnectionFactory connectionFactory) {
	ResultSetToCompanyMapper resultSetToCompany = new ResultSetToCompanyMapper();
	return new CompanyDaoJDBC(connectionFactory, resultSetToCompany);
    }

    private static CompanyService companyService(ConnectionFactory connectionFactory, CompanyDAO companyDAO) {
	Function<Company, CompanyDTO> companyToCompanyDTO = new CompanyToCompanyDTOMapper();
	return new CompanyServiceImpl(companyDAO, companyToCompanyDTO);
    }

    private static ComputerService computerService(ConnectionFactory connectionFactory, CompanyDAO companyDAO) {
	ComputerDAO computerDAO = new ComputerDaoJDBC(connectionFactory);

	ValidatorFactory<CreateComputerDTO> createValidatorFactory = CreateComputerValidator::new;
	ValidatorFactory<UpdateComputerDTO> updateValidatorFactory = UpdateComputerValidator::new;

	LongFunction<Company> findCompanyById = id -> companyDAO.findById(id)
		.orElseThrow(() -> new CompanyNotFoundException(id));

	Function<Computer, ComputerDTO> computerToComputerDTO = new ComputerToComputerDTOMapper();

	Function<UpdateComputerDTO, Computer> updateComputerDTOToComputer = new UpdateComputerDTOToComputerMapper(
		findCompanyById);

	Function<CreateComputerDTO, Computer> createComputerDTOToComputer = new CreateComputerDTOToComputerMapper(
		findCompanyById);

	return new ComputerServiceImpl(computerDAO, createValidatorFactory, updateValidatorFactory,
		computerToComputerDTO, updateComputerDTOToComputer, createComputerDTOToComputer);
    }

}
