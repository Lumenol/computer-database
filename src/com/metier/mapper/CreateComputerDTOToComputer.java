package com.metier.mapper;

import java.util.function.Function;
import java.util.function.LongFunction;

import com.metier.dto.CreateComputerDTO;
import com.metier.entite.Company;
import com.metier.entite.Computer;
import com.metier.exception.CompanyNotFoundException;
import com.metier.exception.MappingException;

public class CreateComputerDTOToComputer implements Function<CreateComputerDTO, Computer> {

    private final LongFunction<Company> findCompanyById;

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

    public CreateComputerDTOToComputer(LongFunction<Company> findCompanyById) {
	super();
	this.findCompanyById = findCompanyById;
    }

}
