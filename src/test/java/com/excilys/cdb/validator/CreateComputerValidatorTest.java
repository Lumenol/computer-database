package com.excilys.cdb.validator;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;

import com.excilys.cdb.dto.CreateComputerDTO;
import com.excilys.cdb.validator.Validator.Result;

class CreateComputerValidatorTest {

    @Test
    void validWithoutDateAndMannufacturerId() {
	final CreateComputerDTO createComputerDTO = new CreateComputerDTO();
	createComputerDTO.setName("Un nom correct");
	final Result result = CreateComputerValidator.getInstance().check(createComputerDTO);
	assertTrue(result.isValid());
    }
    
    @Test 
    void validWithoutDateWithMannufacturerId() throws NoSuchFieldException, SecurityException{
	fail("il faut un mock pour verifier si id fabriquant exist");
	final CreateComputerDTO createComputerDTO = new CreateComputerDTO();
	createComputerDTO.setName("Un nom correct");
	createComputerDTO.setMannufacturerId(5L);
	final Result result = CreateComputerValidator.getInstance().check(createComputerDTO);
	assertTrue(result.isValid());
    }

}
