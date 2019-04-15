package com.metier.service;

import java.util.List;

import com.metier.dto.ComputerDTO;
import com.metier.dto.CreateComputerDTO;
import com.metier.dto.UpdateComputerDTO;

public interface ComputerService {
List<ComputerDTO> findAll();
ComputerDTO findById(long id);
void update(UpdateComputerDTO computer);
void create(CreateComputerDTO computer);
void delete(long id);
}
