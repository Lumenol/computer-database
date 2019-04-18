package com.ui;

import java.util.List;

import com.ui.dto.CompanyListDTO;
import com.ui.dto.ComputerDetailDTO;
import com.ui.dto.ComputerListDTO;
import com.ui.dto.CreateComputerDTO;
import com.ui.dto.UpdateComputerDTO;

public interface Ui {
    long getComputerId();

    CreateComputerDTO getCreateComputerDTO();

    Action getInputMenu();

    UpdateComputerDTO getUpdateComputerDTO();

    void showComputerNotFound();

    void showDetail(ComputerDetailDTO computer);

    void showGoodBye();

    void showListCompany(List<CompanyListDTO> companies);

    void showListComputer(List<ComputerListDTO> computers);

    void showMenu();
}
