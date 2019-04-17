package com.metier.validator;

import java.util.Map;
import java.util.Objects;

import com.metier.dto.CreateComputerDTO;

public class CreateComputerValidator extends AbstractValidator<CreateComputerDTO> {

    public CreateComputerValidator(CreateComputerDTO t) {
	super(t);
    }

    @Override
    protected void check(CreateComputerDTO t, Map<String, String> errors) {
	if (Objects.nonNull(t.getIntroduced()) && Objects.nonNull(t.getDiscontinued())
		&& t.getIntroduced().isAfter(t.getDiscontinued())) {
	    errors.put("discontinued", "Discontinued ne peux pas être avant Introduced");
	}
    }

}
