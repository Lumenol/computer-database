package com;

import java.sql.DriverManager;
import java.sql.SQLException;

import com.controller.Controller;
import com.infra.dao.ConnectionFactory;
import com.infra.dao.jdbc.CompanyDaoJDBC;
import com.infra.dao.jdbc.ComputerDaoJDBC;
import com.metier.ValidatorFactory;
import com.metier.dao.CompanyDAO;
import com.metier.dao.ComputerDAO;
import com.metier.dto.CreateComputerDTO;
import com.metier.dto.UpdateComputerDTO;
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
		// TODO Auto-generated catch block
		e.printStackTrace();
	    }
	    return null;
	};

	ComputerDAO computerDAO = new ComputerDaoJDBC(connectionFactory);
	CompanyDAO companyDAO = new CompanyDaoJDBC(connectionFactory);

	ValidatorFactory<CreateComputerDTO> createValidatorFactory = dto -> new CreateComputerValidator(dto);
	ValidatorFactory<UpdateComputerDTO> updateValidatorFactory = dto -> new UpdateComputerValidator(dto);

	ComputerServiceImpl computerService = new ComputerServiceImpl(computerDAO, companyDAO, createValidatorFactory,
		updateValidatorFactory);
	CompanyService companyService = new CompanyServiceImpl(companyDAO);

	Controller controller = new Controller(ui, computerService, companyService);
	controller.start();
    }

}
