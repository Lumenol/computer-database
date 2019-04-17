package com;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.function.Function;
import java.util.function.LongFunction;

import com.business.ValidatorFactory;
import com.business.dao.CompanyDAO;
import com.business.dao.ComputerDAO;
import com.business.dto.CompanyDTO;
import com.business.dto.ComputerDTO;
import com.business.dto.CreateComputerDTO;
import com.business.dto.UpdateComputerDTO;
import com.business.entite.Company;
import com.business.entite.Computer;
import com.business.exception.CompanyNotFoundException;
import com.business.mapper.CompanyToCompanyDTOMapper;
import com.business.mapper.ComputerToComputerDTOMapper;
import com.business.mapper.CreateComputerDTOToComputerMapper;
import com.business.mapper.UpdateComputerDTOToComputerMapper;
import com.business.service.CompanyService;
import com.business.service.CompanyServiceImpl;
import com.business.service.ComputerService;
import com.business.service.ComputerServiceImpl;
import com.business.validator.CreateComputerValidator;
import com.business.validator.UpdateComputerValidator;
import com.controller.Controller;
import com.infra.dao.ConnectionFactory;
import com.infra.dao.jdbc.CompanyDaoJDBC;
import com.infra.dao.jdbc.ComputerDaoJDBC;
import com.infra.dao.mapper.ResultSetMapper;
import com.infra.dao.mapper.ResultSetToCompanyMapper;
import com.infra.dao.mapper.ResultSetToComputerMapper;
import com.infra.dao.mapper.ResultSetToListCompanyMapper;
import com.infra.dao.mapper.ResultSetToListComputerMapper;
import com.ui.Ui;
import com.ui.cli.CliUi;

public class Main {

    private static CompanyDAO companyDAO(ConnectionFactory connectionFactory) {
	ResultSetMapper<Company> resultSetToCompany = new ResultSetToCompanyMapper();
	ResultSetMapper<List<Company>> resultSetToListCompany = new ResultSetToListCompanyMapper(resultSetToCompany);
	return new CompanyDaoJDBC(connectionFactory, resultSetToListCompany);
    }

    private static CompanyService companyService(ConnectionFactory connectionFactory, CompanyDAO companyDAO) {
	Function<Company, CompanyDTO> companyToCompanyDTO = new CompanyToCompanyDTOMapper();
	return new CompanyServiceImpl(companyDAO, companyToCompanyDTO);
    }

    private static ComputerService computerService(ConnectionFactory connectionFactory, CompanyDAO companyDAO) {
	ResultSetMapper<Computer> resultSetToComputerMapper = new ResultSetToComputerMapper();
	ResultSetMapper<List<Computer>> resultSetToListComputerMapper = new ResultSetToListComputerMapper(
		resultSetToComputerMapper);
	ComputerDAO computerDAO = new ComputerDaoJDBC(connectionFactory, resultSetToListComputerMapper);

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

    private static Controller controller(Ui ui, ComputerService computerService, CompanyService companyService) {
	return new Controller(ui, computerService, companyService);
    }

    public static void main(String[] args) {
	Ui ui = ui();
	ConnectionFactory connectionFactory = connectionFactory();
	CompanyDAO companyDAO = companyDAO(connectionFactory);
	ComputerService computerService = computerService(connectionFactory, companyDAO);
	CompanyService companyService = companyService(connectionFactory, companyDAO);

	Controller controller = controller(ui, computerService, companyService);
	controller.start();
    }

    private static Ui ui() {
	return new CliUi();
    }

}
