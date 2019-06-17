package com.excilys.cdb.shared.mapper;

import com.excilys.cdb.model.Computer;
import com.excilys.cdb.model.Computer.ComputerBuilder;
import com.excilys.cdb.shared.dto.CreateComputerDTO;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

@Component
public class CreateComputerDTOToComputerMapper implements Mapper<CreateComputerDTO, Computer> {

    private final FindCompanyById findCompanyById;

    public CreateComputerDTOToComputerMapper(FindCompanyById findCompanyById) {
        this.findCompanyById = findCompanyById;
    }

    @Override
    public Computer map(CreateComputerDTO dto) {
        Objects.requireNonNull(dto);
        ComputerBuilder builder = Computer.builder().name(dto.getName().trim()).introduced(dto.getIntroduced())
                .discontinued(dto.getDiscontinued());

        Optional.ofNullable(dto.getManufacturerId()).flatMap(findCompanyById::findById)
                .ifPresent(builder::manufacturer);

        return builder.build();
    }
}
