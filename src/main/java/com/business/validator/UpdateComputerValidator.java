package com.business.validator;

import java.util.Map;
import java.util.Objects;

import com.business.dto.UpdateComputerDTO;

public class UpdateComputerValidator extends AbstractValidator<UpdateComputerDTO> {

    public UpdateComputerValidator(UpdateComputerDTO t) {
	super(t);
    }

    @Override
    protected void check(UpdateComputerDTO t, Map<String, String> errors) {
	if (Objects.isNull(t.getName()) || t.getName().trim().isEmpty()) {
	    errors.put("name", "Le nom ne peux pas être nul ou vide.");
	}

	if (Objects.nonNull(t.getIntroduced()) && Objects.nonNull(t.getDiscontinued())
		&& t.getIntroduced().isAfter(t.getDiscontinued())) {
	    errors.put("discontinued", "Discontinued ne peux pas être avant Introduced");
	}
    }

}
