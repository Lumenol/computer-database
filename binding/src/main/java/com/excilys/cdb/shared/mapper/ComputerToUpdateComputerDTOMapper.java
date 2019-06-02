package com.excilys.cdb.shared.mapper;

import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.shared.dto.UpdateComputerDTO;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

@Component
public class ComputerToUpdateComputerDTOMapper implements Mapper<Computer, UpdateComputerDTO> {

    @Override
    public UpdateComputerDTO map(Computer computer) {
        Objects.requireNonNull(computer);
        final UpdateComputerDTO.UpdateComputerDTOBuilder builder = UpdateComputerDTO.builder().id(computer.getId())
                .name(computer.getName()).introduced(computer.getIntroduced()).discontinued(computer.getDiscontinued());
        Optional.ofNullable(computer.getManufacturer()).map(Company::getId).ifPresent(builder::manufacturerId);
        return builder.build();
    }
}
