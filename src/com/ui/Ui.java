package com.ui;

import java.util.List;

import com.business.dto.UpdateComputerDTO;
import com.ui.dto.CompanyListDTO;
import com.ui.dto.ComputerDetailDTO;
import com.ui.dto.ComputerListDTO;
import com.ui.dto.CreateComputerDTO;

public interface Ui {
    long getComputerId();

    CreateComputerDTO getCreateComputerDTO();

    Action getInput();

    UpdateComputerDTO getUpdateComputerDTO();

    void showComputerNotFound();

    void showDetail(ComputerDetailDTO computer);

    void showListCompany(List<CompanyListDTO> companies);

    void showListComputer(List<ComputerListDTO> computers);

    void showMenu();
}
