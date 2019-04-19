package com;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;
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
import com.controller.mapper.CompanyDTOToCompanyListDTO;
import com.controller.mapper.ComputerDTOToComputerDetailDTO;
import com.controller.mapper.ComputerDTOToComputerListDTO;
import com.controller.mapper.CreateComputerDTOUiToBusiness;
import com.controller.mapper.UpdateComputerDTOUiToBusiness;
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
import com.ui.dto.CompanyListDTO;
import com.ui.dto.ComputerDetailDTO;
import com.ui.dto.ComputerListDTO;

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
	Function<com.ui.dto.CreateComputerDTO, CreateComputerDTO> createComputerDTOUiToBusiness = new CreateComputerDTOUiToBusiness();
	Function<com.ui.dto.UpdateComputerDTO, UpdateComputerDTO> updateComputerDTOUiToBusiness = new UpdateComputerDTOUiToBusiness();

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
