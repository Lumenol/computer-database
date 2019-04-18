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

    Action getInputPage(boolean previous, boolean next);

    UpdateComputerDTO getUpdateComputerDTO();

    void showComputerNotFound();

    void showDetail(ComputerDetailDTO computer);

    void showGoodBye();

    void showListCompany(List<CompanyListDTO> companies);

    void showListComputer(List<ComputerListDTO> computers);

    Action getInputMenu();

    void showMessage(String message);
}
