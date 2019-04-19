package com.business.mapper;

import java.util.function.Function;
import java.util.function.LongFunction;

import com.business.dto.UpdateComputerDTO;
import com.business.entite.Company;
import com.business.entite.Computer;
import com.business.exception.CompanyNotFoundException;
import com.business.exception.MappingException;

public class UpdateComputerDTOToComputerMapper implements Function<UpdateComputerDTO, Computer> {

    private final LongFunction<Company> findCompanyById;

    public UpdateComputerDTOToComputerMapper(LongFunction<Company> findCompanyById) {
	super();
	this.findCompanyById = findCompanyById;
    }

    @Override
    public Computer apply(UpdateComputerDTO dto) {
	try {
	    Company mannufacturer = findCompanyById.apply(dto.getMannufacturerId());
	    return Computer.builder().id(dto.getId()).name(dto.getName()).introduced(dto.getIntroduced())
		    .discontinued(dto.getDiscontinued()).manufacturer(mannufacturer).build();
	} catch (CompanyNotFoundException e) {
	    throw new MappingException(e);
	}
    }

}
