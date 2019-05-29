package com.excilys.cdb.shared.mapper;

import com.excilys.cdb.model.Computer;
import com.excilys.cdb.shared.dto.ComputerDTO;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class ComputerToComputerDTOMapper implements Mapper<Computer, ComputerDTO> {

    @Override
    public ComputerDTO map(Computer computer) {
        Objects.requireNonNull(computer);
        final ComputerDTO.ComputerDTOBuilder builder = ComputerDTO.builder().id(computer.getId())
                .name(computer.getName()).introduced(computer.getIntroduced()).discontinued(computer.getDiscontinued());

        if (Objects.nonNull(computer.getManufacturer())) {
            builder.manufacturer(computer.getManufacturer().getName());
        }
        return builder.build();
    }

}
