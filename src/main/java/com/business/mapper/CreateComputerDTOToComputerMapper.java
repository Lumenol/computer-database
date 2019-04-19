package com.business.mapper;

import java.util.Objects;
import java.util.function.Function;
import java.util.function.LongFunction;

import com.business.dto.CreateComputerDTO;
import com.business.entite.Company;
import com.business.entite.Computer;
import com.business.entite.Computer.ComputerBuilder;
import com.business.exception.CompanyNotFoundException;

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
