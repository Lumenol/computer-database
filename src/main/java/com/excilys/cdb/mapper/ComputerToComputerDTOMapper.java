package com.excilys.cdb.mapper;

import java.util.function.Function;

import com.excilys.cdb.dto.CompanyDTO;
import com.excilys.cdb.dto.ComputerDTO;
import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;

public class ComputerToComputerDTOMapper implements Function<Computer, ComputerDTO> {

    private final Function<Company, CompanyDTO> companyToCompanyDTO;

    public ComputerToComputerDTOMapper(Function<Company, CompanyDTO> companyToCompanyDTO) {
	super();
	this.companyToCompanyDTO = companyToCompanyDTO;
    }

    @Override
    public ComputerDTO apply(Computer computer) {
	ComputerDTO computerDTO = new ComputerDTO();
	computerDTO.setId(computer.getId());
	computerDTO.setName(computer.getName());
	computerDTO.setIntroduced(computer.getIntroduced());
	computerDTO.setDiscontinued(computer.getDiscontinued());

	computerDTO.setMannufacturer(computer.getManufacturer().map(companyToCompanyDTO::apply));

	return computerDTO;
    }

}
