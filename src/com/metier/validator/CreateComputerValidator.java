package com.metier.validator;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.metier.dto.CreateComputerDTO;

public class CreateComputerValidator implements Validator<CreateComputerDTO> {

	private Map<String, String> errors;

	public CreateComputerValidator(CreateComputerDTO createComputerDTO) {
		errors = check(createComputerDTO);
	}

	private Map<String, String> check(CreateComputerDTO createComputerDTO) {
		Map<String, String> errors = new HashMap<>();
		//TODO check
		return Collections.unmodifiableMap(errors);
	}

	@Override
	public Map<String, String> errors() {
		return errors;
	}

}
