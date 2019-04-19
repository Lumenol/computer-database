package com.excilys.cdb.mapper;

import java.util.Objects;
import java.util.function.Function;
import java.util.function.LongFunction;

import com.excilys.cdb.dto.CreateComputerDTO;
import com.excilys.cdb.exception.CompanyNotFoundException;
import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.model.Computer.ComputerBuilder;

public class CreateComputerDTOToComputerMapper implements Function<CreateComputerDTO, Computer> {

    private final LongFunction<Company> findCompanyById;

    public CreateComputerDTOToComputerMapper(LongFunction<Company> findCompanyById) {
	super();
	this.findCompanyById = findCompanyById;
    }

    @Override
    public Computer apply(CreateComputerDTO dto) {
	ComputerBuilder builder = Computer.builder().name(dto.getName()).introduced(dto.getIntroduced())
		.discontinued(dto.getDiscontinued());
	if (Objects.nonNull(dto.getMannufacturerId())) {
	    try {
		builder.manufacturer(findCompanyById.apply(dto.getMannufacturerId()));
	    } catch (CompanyNotFoundException e) {
	    }
	}
	return builder.build();
    }

}
