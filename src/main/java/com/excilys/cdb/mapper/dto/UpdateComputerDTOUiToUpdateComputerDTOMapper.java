package com.excilys.cdb.mapper.dto;

import com.excilys.cdb.dto.UpdateComputerDTO;
import com.excilys.cdb.dto.UpdateComputerDTOUi;
import com.excilys.cdb.exception.MapperException;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Objects;

public class UpdateComputerDTOUiToUpdateComputerDTOMapper implements Mapper<UpdateComputerDTOUi, UpdateComputerDTO> {
    private static final String NULL = "null";
    private static UpdateComputerDTOUiToUpdateComputerDTOMapper instance;

    private UpdateComputerDTOUiToUpdateComputerDTOMapper() {
    }

    private static LocalDate parseDate(String date) {
        if (date.equals(NULL)) {
            return null;
        }
        return LocalDate.parse(date);
    }

    private static Long parseId(String id) {
        if (id.equals(NULL)) {
            return null;
        } else {
            return Long.valueOf(id);
        }
    }

    public static UpdateComputerDTOUiToUpdateComputerDTOMapper getInstance() {
        if (Objects.isNull(instance)) {
            instance = new UpdateComputerDTOUiToUpdateComputerDTOMapper();
        }
        return instance;
    }

    @Override
    public UpdateComputerDTO map(UpdateComputerDTOUi updateComputerDTOUi) {
        try {
            final UpdateComputerDTO updateComputerDTO = new UpdateComputerDTO();
            final Long id = parseId(updateComputerDTOUi.getId());
            if (Objects.isNull(id)) {
                throw new MapperException();
            }
            updateComputerDTO.setId(id);
            updateComputerDTO.setName(updateComputerDTOUi.getName());
            updateComputerDTO.setMannufacturerId(parseId(updateComputerDTOUi.getMannufacturerId()));
            updateComputerDTO.setIntroduced(parseDate(updateComputerDTOUi.getIntroduced()));
            updateComputerDTO.setDiscontinued(parseDate(updateComputerDTOUi.getDiscontinued()));
            return updateComputerDTO;
        } catch (NumberFormatException | DateTimeParseException e) {
            throw new MapperException(e);
        }
    }

}
