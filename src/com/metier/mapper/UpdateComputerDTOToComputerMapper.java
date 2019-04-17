package com.metier.mapper;

import java.util.function.Function;
import java.util.function.LongFunction;

import com.metier.dto.UpdateComputerDTO;
import com.metier.entite.Company;
import com.metier.entite.Computer;
import com.metier.exception.CompanyNotFoundException;
import com.metier.exception.MappingException;

public class UpdateComputerDTOToComputerMapper implements Function<UpdateComputerDTO, Computer> {

    private final LongFunction<Company> findCompanyById;

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

    public UpdateComputerDTOToComputerMapper(LongFunction<Company> findCompanyById) {
	super();
	this.findCompanyById = findCompanyById;
    }

}
