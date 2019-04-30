package com.excilys.cdb.mapper.dto;

import java.util.Objects;

import com.excilys.cdb.dto.CompanyDTO;
import com.excilys.cdb.dto.ComputerDTO;
import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;

public class ComputerToComputerDTOMapper implements Mapper<Computer, ComputerDTO> {

    private static ComputerToComputerDTOMapper instance;
    private final Mapper<Company, CompanyDTO> companyToCompanyDTO = CompanyToCompanyDTOMapper.getInstance();

    private ComputerToComputerDTOMapper() {
    }

    public static ComputerToComputerDTOMapper getInstance() {
	if (Objects.isNull(instance)) {
	    instance = new ComputerToComputerDTOMapper();
	}
	return instance;
    }

    @Override
    public ComputerDTO map(Computer computer) {
	ComputerDTO computerDTO = new ComputerDTO();
	computerDTO.setId(computer.getId());
	computerDTO.setName(computer.getName());
	computerDTO.setIntroduced(computer.getIntroduced());
	computerDTO.setDiscontinued(computer.getDiscontinued());
	if (Objects.nonNull(computer.getManufacturer())) {
	    computerDTO.setMannufacturer(companyToCompanyDTO.map(computer.getManufacturer()));
	}
	return computerDTO;
    }

}
