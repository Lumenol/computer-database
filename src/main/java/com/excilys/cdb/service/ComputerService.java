package com.excilys.cdb.service;

import java.util.List;

import com.excilys.cdb.dto.ComputerDTO;
import com.excilys.cdb.dto.CreateComputerDTO;
import com.excilys.cdb.dto.UpdateComputerDTO;

public interface ComputerService {
    void create(CreateComputerDTO computer);

    void delete(long id);

    boolean exist(long id);

    List<ComputerDTO> findAll();

    List<ComputerDTO> findAll(long from, long to);

    ComputerDTO findById(long id);

    void update(UpdateComputerDTO computer);
}
