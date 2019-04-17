package com.business.mapper;

import java.util.function.Function;
import java.util.function.LongFunction;

import com.business.dto.CreateComputerDTO;
import com.business.entite.Company;
import com.business.entite.Computer;
import com.business.exception.CompanyNotFoundException;
import com.business.exception.MappingException;

public class CreateComputerDTOToComputerMapper implements Function<CreateComputerDTO, Computer> {

    private final LongFunction<Company> findCompanyById;

    public CreateComputerDTOToComputerMapper(LongFunction<Company> findCompanyById) {
	super();
	this.findCompanyById = findCompanyById;
    }

    @Override
    public Computer apply(CreateComputerDTO dto) {
	try {
	    Company mannufacturer = findCompanyById.apply(dto.getMannufacturerId());
	    return Computer.builder().name(dto.getName()).introduced(dto.getIntroduced())
		    .discontinued(dto.getDiscontinued()).manufacturer(mannufacturer).build();
	} catch (CompanyNotFoundException e) {
	    throw new MappingException(e);
	}
    }

}
