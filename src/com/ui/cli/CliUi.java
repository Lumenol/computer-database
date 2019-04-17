package com.ui.cli;

import java.util.List;
import java.util.Scanner;

import com.business.dto.UpdateComputerDTO;
import com.ui.Action;
import com.ui.Ui;
import com.ui.dto.CompanyListDTO;
import com.ui.dto.ComputerDetailDTO;
import com.ui.dto.ComputerListDTO;
import com.ui.dto.CreateComputerDTO;

public class CliUi implements Ui {
    private Scanner scanner;

    public CliUi() {
	scanner = new Scanner(System.in);
    }

    @Override
    public long getComputerId() {
	System.out.println("Entrer le numero de l'ordinateur");
	while (true) {
	    try {
		return scanner.nextLong();
	    } catch (Exception e) {
		e.printStackTrace();
	    }
	}
    }

    @Override
    public CreateComputerDTO getCreateComputerDTO() {
	CreateComputerDTO createComputerDTO = new CreateComputerDTO();

	System.out.print("Nom: ");
	createComputerDTO.setName(scanner.next());

	System.out.print("Date de mise en service aaaa-mm-jj: ");
	createComputerDTO.setIntroduced(scanner.next());

	System.out.print("Date de mise hors service aaaa-mm-jj: ");
	createComputerDTO.setDiscontinued(scanner.next());

	System.out.print("Identifiant du fabricant: ");
	createComputerDTO.setMannufacturer(scanner.next());

	return createComputerDTO;
    }

    @Override
    public Action getInput() {
	int input = scanner.nextInt();
	switch (input) {
	case 1:
	    return Action.LIST_COMPANY;
	case 2:
	    return Action.LIST_COMPUTER;
	case 3:
	    return Action.DETAIL_COMPUTER;
	case 4:
	    return Action.DELETE_COMPUTER;
	case 5:
	    return Action.CREATE_COMPUTER;
	case 6:
	    return Action.UPDATE_COMPUTER;
	default:
	    return Action.UNKNOW;
	}
    }

    @Override
    public UpdateComputerDTO getUpdateComputerDTO() {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public void showComputerNotFound() {
	System.out.println("L'ordinateur n'existe pas.");
    }

    @Override
    public void showDetail(ComputerDetailDTO computer) {
	System.out.println("id: " + computer.getId() + "\tnom:" + computer.getName() + "\t fabricant:"
		+ computer.getMannufacturer() + "\tmise en service:" + computer.getIntroduced() + "\tmise hors service:"
		+ computer.getDiscontinued());
    }

    @Override
    public void showListCompany(List<CompanyListDTO> companies) {
	System.out.println("Les companies");
	companies.forEach(c -> System.out.println("id: " + c.getId() + "\tnom: " + c.getName()));
    }

    @Override
    public void showListComputer(List<ComputerListDTO> computers) {
	System.out.println("Les ordinateus");
	computers.forEach(c -> System.out.println("id: " + c.getId() + "\tnom: " + c.getName()));
    }

    @Override
    public void showMenu() {
	System.out.println("Le menu");
	System.out.println("1 - Lister les companies");
	System.out.println("2 - Lister les ordinateurs");
	System.out.println("3 - Détail d'un ordinateur");
	System.out.println("4 - Supprimer un ordinateur");
	System.out.println("5 - Supprimer un ordinateur");
	System.out.println("6 - Crée un ordinateur");
    }

}
