package com.metier.validator;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.metier.dto.UpdateComputerDTO;

public class UpdateComputerValidator implements Validator<UpdateComputerDTO> {
    private Map<String, String> errors;

	public UpdateComputerValidator(UpdateComputerDTO updateComputerDTO) {
		errors = check(updateComputerDTO);
	}

	private Map<String, String> check(UpdateComputerDTO updateComputerDTO) {
		Map<String, String> errors = new HashMap<>();
		//TODO check
		return Collections.unmodifiableMap(errors);
	}

	@Override
	public Map<String, String> errors() {
		return errors;
	}
}
