package com.excilys.cdb.mapper;

import java.util.function.Function;
import java.util.function.LongFunction;

import com.excilys.cdb.dto.UpdateComputerDTO;
import com.excilys.cdb.exception.CompanyNotFoundException;
import com.excilys.cdb.exception.MappingException;
import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;

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
