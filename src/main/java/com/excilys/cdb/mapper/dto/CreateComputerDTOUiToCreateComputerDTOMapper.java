package com.excilys.cdb.mapper.dto;

import com.excilys.cdb.dto.CreateComputerDTO;
import com.excilys.cdb.dto.CreateComputerDTOUi;
import com.excilys.cdb.exception.MapperException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Objects;

public class CreateComputerDTOUiToCreateComputerDTOMapper implements Mapper<CreateComputerDTOUi, CreateComputerDTO> {
    private static CreateComputerDTOUiToCreateComputerDTOMapper instance;
    private final Logger logger = LoggerFactory.getLogger(getClass());

    private CreateComputerDTOUiToCreateComputerDTOMapper() {
    }

    public static CreateComputerDTOUiToCreateComputerDTOMapper getInstance() {
        if (Objects.isNull(instance)) {
            instance = new CreateComputerDTOUiToCreateComputerDTOMapper();
        }
        return instance;
    }

    private static LocalDate parseDate(String date) {
        try {
            return LocalDate.parse(date);
        } catch (DateTimeParseException e) {
            return null;
        }
    }

    private static Long parseId(String id) {
        try {
            return Long.valueOf(id);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    @Override
    public CreateComputerDTO map(CreateComputerDTOUi createComputerDTOUi) {
        try {
            final CreateComputerDTO createComputerDTO = new CreateComputerDTO();
            createComputerDTO.setName(createComputerDTOUi.getName());
            createComputerDTO.setMannufacturerId(parseId(createComputerDTOUi.getMannufacturerId()));
            createComputerDTO.setIntroduced(parseDate(createComputerDTOUi.getIntroduced()));
            createComputerDTO.setDiscontinued(parseDate(createComputerDTOUi.getDiscontinued()));
            return createComputerDTO;
        } catch (NumberFormatException | DateTimeParseException e) {
            logger.warn("map(" + createComputerDTOUi + ")", e);
            throw new MapperException(e);
        }
    }

}
