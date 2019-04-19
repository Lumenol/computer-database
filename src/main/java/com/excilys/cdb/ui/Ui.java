package com.excilys.cdb.ui;

import com.excilys.cdb.ui.dto.CompanyListDTO;
import com.excilys.cdb.ui.dto.ComputerDetailDTO;
import com.excilys.cdb.ui.dto.ComputerListDTO;
import com.excilys.cdb.ui.dto.CreateComputerDTO;
import com.excilys.cdb.ui.dto.PageDTO;
import com.excilys.cdb.ui.dto.UpdateComputerDTO;

public interface Ui {
    long getComputerId();

    CreateComputerDTO getCreateComputerDTO();

    Action getInputMenu();

    UpdateComputerDTO getUpdateComputerDTO();

    void showComputerNotFound();

    void showDetail(ComputerDetailDTO computer);

    void showGoodBye();

    Action showListCompany(PageDTO<CompanyListDTO> page);

    Action showListComputer(PageDTO<ComputerListDTO> page);

    void showMessage(String message);
}
