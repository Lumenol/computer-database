package com.ui;

import java.util.List;

import com.ui.dto.CompanyListDTO;
import com.ui.dto.ComputerDetailDTO;
import com.ui.dto.ComputerListDTO;
import com.ui.dto.CreateComputerDTO;

public interface Ui {
    void showMenu();

    Action getInput();

    void showListComputer(List<ComputerListDTO> computers);

    void showListCompany(List<CompanyListDTO> companies);
    
    void showDetail(ComputerDetailDTO computer);

    long getComputerId();

    CreateComputerDTO getCreateComputerDTO();
    
    void showComputerNotFound();
}
