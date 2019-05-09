package com.excilys.cdb.config;

import com.excilys.cdb.controller.Controller;
import com.excilys.cdb.mapper.dto.CompanyToCompanyDTOMapper;
import com.excilys.cdb.mapper.dto.ComputerToComputerDTOMapper;
import com.excilys.cdb.mapper.resultset.ResultSetMapper;
import com.excilys.cdb.mapper.resultset.ResultSetToCompanyMapper;
import com.excilys.cdb.mapper.resultset.ResultSetToCountMapper;
import com.excilys.cdb.mapper.resultset.ResultSetToListMapper;
import com.excilys.cdb.model.Company;
import com.excilys.cdb.persistence.ConnectionManager;
import com.excilys.cdb.persistence.dao.ComputerDAO;
import com.excilys.cdb.service.ComputerService;
import com.excilys.cdb.ui.Cli;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "com.excilys.cdb")
public class AppConfig {
    @Bean
    public ConnectionManager connectionManager() {
        return ConnectionManager.getInstance();
    }

    @Bean
    public ResultSetMapper<Long> longResultSetMapper() {
        return ResultSetToCountMapper.getInstance();
    }

    @Bean
    public ResultSetToListMapper<Company> companyResultSetToListMapper(ResultSetToCompanyMapper resultSetToCompanyMapper) {
        return new ResultSetToListMapper<>(resultSetToCompanyMapper);
    }

    @Bean
    public ResultSetToCompanyMapper resultSetToCompanyMapper() {
        return ResultSetToCompanyMapper.getInstance();
    }

    @Bean
    public ComputerService computerService() {
        return ComputerService.getInstance();
    }

    @Bean
    public ComputerDAO computerDAO() {
        return ComputerDAO.getInstance();
    }

    @Bean
    public CompanyToCompanyDTOMapper companyToCompanyDTOMapper() {
        return CompanyToCompanyDTOMapper.getInstance();
    }

    @Bean
    public ComputerToComputerDTOMapper computerToComputerDTOMapper() {
        return ComputerToComputerDTOMapper.getInstance();
    }

    @Bean
    public Cli cli(Controller controller) {
        return new Cli(controller, System.in, System.out);
    }

}
