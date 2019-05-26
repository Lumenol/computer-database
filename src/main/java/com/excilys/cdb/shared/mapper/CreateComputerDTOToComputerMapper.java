package com.excilys.cdb.shared.mapper;

import com.excilys.cdb.model.Computer;
import com.excilys.cdb.model.Computer.ComputerBuilder;
import com.excilys.cdb.shared.dto.CreateComputerDTO;
import com.excilys.cdb.shared.exception.CompanyServiceException;
import com.excilys.cdb.shared.exception.MapperException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeParseException;
import java.util.Objects;
import java.util.Optional;

@Component
public class CreateComputerDTOToComputerMapper implements Mapper<CreateComputerDTO, Computer> {

    private final FindCompanyById findCompanyById;
    private final Logger logger = LoggerFactory.getLogger(getClass());

    public CreateComputerDTOToComputerMapper(FindCompanyById findCompanyById) {
        this.findCompanyById = findCompanyById;
    }

    @Override
    public Computer map(CreateComputerDTO dto) {
        Objects.requireNonNull(dto);
        try {
            ComputerBuilder builder = Computer.builder().name(dto.getName().trim()).introduced(dto.getIntroduced())
                    .discontinued(dto.getDiscontinued());

            Optional.ofNullable(dto.getMannufacturerId()).flatMap(findCompanyById::findById)
                    .ifPresent(builder::manufacturer);

            return builder.build();
        } catch (CompanyServiceException | DateTimeParseException | NumberFormatException e) {
            logger.warn("map(" + dto + ")", e);
            throw new MapperException(e);
        }
    }
}
