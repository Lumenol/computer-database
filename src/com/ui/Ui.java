package com.ui;

import com.ui.dto.CompanyListDTO;
import com.ui.dto.ComputerDetailDTO;
import com.ui.dto.ComputerListDTO;
import com.ui.dto.CreateComputerDTO;
import com.ui.dto.PageDTO;
import com.ui.dto.UpdateComputerDTO;

public interface Ui {
    long getComputerId();

    CreateComputerDTO getCreateComputerDTO();

    UpdateComputerDTO getUpdateComputerDTO();

    void showComputerNotFound();

    void showDetail(ComputerDetailDTO computer);

    void showGoodBye();

    Action showListCompany(PageDTO<CompanyListDTO> page);

    Action showListComputer(PageDTO<ComputerListDTO> page);

    Action getInputMenu();

    void showMessage(String message);
}
