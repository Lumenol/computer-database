package com.ui.cli;

import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

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
    public void showMenu() {
	System.out.println("Le menu");
	System.out.println("1 - Lister les companies");
	System.out.println("2 - Lister les ordinateurs");
	System.out.println("3 - Détail d'un ordinateur");
	System.out.println("4 - Supprimer un ordinateur");
	System.out.println("5 - Crée un ordinateur");
    }

    @Override
    public Action getInput() {
	int input = scanner.nextInt();
	switch (input) {
	case 2:
	    return Action.LIST_COMPUTER;
	case 3:
	    return Action.DETAIL_COMPUTER;
	case 4:
	    return Action.DELETE_COMPUTER;
	case 5:
	    return Action.CREATE_COMPUTER;
	case 1:
	    return Action.LIST_COMPANY;
	default:
	    return Action.UNKNOW;
	}
    }

    @Override
    public void showListComputer(List<ComputerListDTO> computers) {
	System.out.println("Les ordinateus");
	computers.forEach(c -> System.out.println("id: " + c.getId() + "\tnom: " + c.getName()));
    }

    @Override
    public void showDetail(ComputerDetailDTO computer) {
	System.out.println("id: " + computer.getId() + "\tnom:" + computer.getName() + "\tmise en service:"
		+ computer.getIntroduced()+"\tmise hors service:"+computer.getDiscontinued());
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
	// TODO ne marche pas pour lire sur plusieurs ligne
	CreateComputerDTO createComputerDTO = new CreateComputerDTO();

	System.out.println("Nom");
	createComputerDTO.setName("Le nouveau");

	System.out.println("Date de mise en service aaaa-mm-jj");
	createComputerDTO.setIntroduced("2017-01-04");

	System.out.println("Date de mise hors service aaaa-mm-jj");
	createComputerDTO.setDiscontinued("");

	System.out.println("Identifiant du fabricant");
	createComputerDTO.setMannufacturer("1");

	return createComputerDTO;
    }

    @Override
    public void showListCompany(List<CompanyListDTO> companies) {
	System.out.println("Les companies");
	companies.forEach(c -> System.out.println("id: " + c.getId() + "\tnom: " + c.getName()));
    }

    @Override
    public void showComputerNotFound() {
	System.out.println("L'ordinateur n'existe pas.");
    }

}
