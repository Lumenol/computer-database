package com.excilys.cdb.shared.mapper;

import com.excilys.cdb.model.Computer;
import com.excilys.cdb.shared.dto.UpdateComputerDTO;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

@Component
public class UpdateComputerDTOToComputerMapper implements Mapper<UpdateComputerDTO, Computer> {

    private final FindCompanyById findCompanyById;

    public UpdateComputerDTOToComputerMapper(FindCompanyById findCompanyById) {
        this.findCompanyById = findCompanyById;
    }

    @Override
    public Computer map(UpdateComputerDTO dto) {
        Objects.requireNonNull(dto);
        final Computer.ComputerBuilder builder = Computer.builder().id(dto.getId()).name(dto.getName().trim())
                .introduced(dto.getIntroduced()).discontinued(dto.getDiscontinued());
        Optional.ofNullable(dto.getMannufacturerId()).flatMap(findCompanyById::findById)
                .ifPresent(builder::manufacturer);
        return builder.build();
    }
}
