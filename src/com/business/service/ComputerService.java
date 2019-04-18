package com.business.service;

import java.util.List;

import com.business.dto.ComputerDTO;
import com.business.dto.CreateComputerDTO;
import com.business.dto.UpdateComputerDTO;

public interface ComputerService {
    void create(CreateComputerDTO computer);

    void delete(long id);

    boolean exist(long id);

    List<ComputerDTO> findAll();

    ComputerDTO findById(long id);

    void update(UpdateComputerDTO computer);
}
