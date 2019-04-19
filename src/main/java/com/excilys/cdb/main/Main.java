package com.excilys.cdb.main;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;
import java.util.function.Function;
import java.util.function.LongFunction;

import com.excilys.cdb.controller.Controller;
import com.excilys.cdb.dao.CompanyDAO;
import com.excilys.cdb.dao.ComputerDAO;
import com.excilys.cdb.dao.jdbc.CompanyDaoJDBC;
import com.excilys.cdb.dao.jdbc.ComputerDaoJDBC;
import com.excilys.cdb.dto.CompanyDTO;
import com.excilys.cdb.dto.ComputerDTO;
import com.excilys.cdb.dto.CreateComputerDTO;
import com.excilys.cdb.dto.UpdateComputerDTO;
import com.excilys.cdb.exception.CompanyNotFoundException;
import com.excilys.cdb.mapper.CompanyDTOToCompanyListDTO;
import com.excilys.cdb.mapper.CompanyToCompanyDTOMapper;
import com.excilys.cdb.mapper.ComputerDTOToComputerDetailDTO;
import com.excilys.cdb.mapper.ComputerDTOToComputerListDTO;
import com.excilys.cdb.mapper.ComputerToComputerDTOMapper;
import com.excilys.cdb.mapper.CreateComputerDTOToComputerMapper;
import com.excilys.cdb.mapper.CreateComputerDTOUiToBusiness;
import com.excilys.cdb.mapper.ResultSetMapper;
import com.excilys.cdb.mapper.ResultSetToCompanyMapper;
import com.excilys.cdb.mapper.ResultSetToComputerMapper;
import com.excilys.cdb.mapper.ResultSetToListCompanyMapper;
import com.excilys.cdb.mapper.ResultSetToListComputerMapper;
import com.excilys.cdb.mapper.UpdateComputerDTOToComputerMapper;
import com.excilys.cdb.mapper.UpdateComputerDTOUiToBusiness;
import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.service.CompanyService;
import com.excilys.cdb.service.CompanyServiceImpl;
import com.excilys.cdb.service.ComputerService;
import com.excilys.cdb.service.ComputerServiceImpl;
import com.excilys.cdb.ui.Ui;
import com.excilys.cdb.ui.cli.CliUi;
import com.excilys.cdb.ui.dto.CompanyListDTO;
import com.excilys.cdb.ui.dto.ComputerDetailDTO;
import com.excilys.cdb.ui.dto.ComputerListDTO;
import com.excilys.cdb.validator.CreateComputerValidator;
import com.excilys.cdb.validator.UpdateComputerValidator;

import all.ConnectionFactory;
import all.ValidatorFactory;

public class Main {

    private static CompanyDAO companyDAO(ConnectionFactory connectionFactory) {
	ResultSetMapper<Company> resultSetToCompany = new ResultSetToCompanyMapper();
	ResultSetMapper<List<Company>> resultSetToListCompany = new ResultSetToListCompanyMapper(resultSetToCompany);
	return new CompanyDaoJDBC(connectionFactory, resultSetToListCompany);
    }

    private static Function<Company, CompanyDTO> companyToCompanyDTO() {
	return new CompanyToCompanyDTOMapper();
    }

    private static CompanyService companyService(ConnectionFactory connectionFactory, CompanyDAO companyDAO,
	    Function<Company, CompanyDTO> companyToCompanyDTO) {
	return new CompanyServiceImpl(companyDAO, companyToCompanyDTO);
    }

    private static ComputerService computerService(ConnectionFactory connectionFactory, CompanyDAO companyDAO,
	    Function<Company, CompanyDTO> companyToCompanyDTO) {
	ResultSetMapper<Computer> resultSetToComputerMapper = new ResultSetToComputerMapper();
	ResultSetMapper<List<Computer>> resultSetToListComputerMapper = new ResultSetToListComputerMapper(
		resultSetToComputerMapper);
	ComputerDAO computerDAO = new ComputerDaoJDBC(connectionFactory, resultSetToListComputerMapper);

	ValidatorFactory<CreateComputerDTO> createValidatorFactory = CreateComputerValidator::new;
	ValidatorFactory<UpdateComputerDTO> updateValidatorFactory = UpdateComputerValidator::new;

	LongFunction<Company> findCompanyById = id -> companyDAO.findById(id)
		.orElseThrow(() -> new CompanyNotFoundException(id));

	Function<Computer, ComputerDTO> computerToComputerDTO = new ComputerToComputerDTOMapper(companyToCompanyDTO);

	Function<UpdateComputerDTO, Computer> updateComputerDTOToComputer = new UpdateComputerDTOToComputerMapper(
		findCompanyById);

	Function<CreateComputerDTO, Computer> createComputerDTOToComputer = new CreateComputerDTOToComputerMapper(
		findCompanyById);

	return new ComputerServiceImpl(computerDAO, createValidatorFactory, updateValidatorFactory,
		computerToComputerDTO, updateComputerDTOToComputer, createComputerDTOToComputer);
    }

    private static Properties properties(String propertiesFileName) throws FileNotFoundException, IOException {
	Properties properties = new Properties();
	properties.load(new FileReader(propertiesFileName));
	return properties;

    }

    private static ConnectionFactory connectionFactory(String url, String user, String password) {
	return () -> {
	    try {
		return DriverManager.getConnection(url, user, password);
	    } catch (SQLException e) {
		e.printStackTrace();
	    }
	    return null;
	};
    }

    private static Controller controller(Ui ui, ComputerService computerService, CompanyService companyService) {
	Function<CompanyDTO, CompanyListDTO> companyDTOToCompanyListDTO = new CompanyDTOToCompanyListDTO();
	Function<ComputerDTO, ComputerDetailDTO> computerDTOToComputerDetailDTO = new ComputerDTOToComputerDetailDTO();
	Function<ComputerDTO, ComputerListDTO> computerDTOToComputerListDTO = new ComputerDTOToComputerListDTO();
	Function<com.excilys.cdb.ui.dto.CreateComputerDTO, CreateComputerDTO> createComputerDTOUiToBusiness = new CreateComputerDTOUiToBusiness();
	Function<com.excilys.cdb.ui.dto.UpdateComputerDTO, UpdateComputerDTO> updateComputerDTOUiToBusiness = new UpdateComputerDTOUiToBusiness();

	return new Controller(ui, computerService, companyService, companyDTOToCompanyListDTO,
		computerDTOToComputerDetailDTO, computerDTOToComputerListDTO, createComputerDTOUiToBusiness,
		updateComputerDTOUiToBusiness);
    }

    public static void main(String[] args) throws FileNotFoundException, IOException {
	Ui ui = ui();

	Properties database = properties("database.properties");
	String url = database.getProperty("url");
	String username = database.getProperty("username");
	String password = database.getProperty("password");

	ConnectionFactory connectionFactory = connectionFactory(url, username, password);
	CompanyDAO companyDAO = companyDAO(connectionFactory);
	Function<Company, CompanyDTO> companyToCompanyDTO = companyToCompanyDTO();
	ComputerService computerService = computerService(connectionFactory, companyDAO, companyToCompanyDTO);
	CompanyService companyService = companyService(connectionFactory, companyDAO, companyToCompanyDTO);

	Controller controller = controller(ui, computerService, companyService);
	controller.start();
    }

    private static Ui ui() {
	return new CliUi();
    }

}
