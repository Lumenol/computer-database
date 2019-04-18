package com.ui.cli;

import java.util.List;
import java.util.Scanner;

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
	while (!scanner.hasNextLong()) {
	    scanner.nextLine();
	}
	return scanner.nextLong();
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
    public Action getInputMenu() {
	while (true) {
	    System.out.println("Le menu");
	    System.out.println("0 - Quitter");
	    System.out.println("1 - Lister les companies");
	    System.out.println("2 - Lister les ordinateurs");
	    System.out.println("3 - Détail d'un ordinateur");
	    System.out.println("4 - Supprimer un ordinateur");
	    System.out.println("5 - Crée un ordinateur");
	    System.out.println("6 - Mettre à jour un ordinateur");
	    while (!scanner.hasNextInt()) {
		scanner.nextLine();
	    }
	    switch (scanner.nextInt()) {
	    case 0:
		return Action.QUIT;
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
		break;
	    }
	}

    }

    @Override
    public com.ui.dto.UpdateComputerDTO getUpdateComputerDTO() {
	com.ui.dto.UpdateComputerDTO dto = new com.ui.dto.UpdateComputerDTO();

	System.out.print("Id: ");
	while (!scanner.hasNextLong()) {
	    scanner.nextLine();
	}
	dto.setId(scanner.nextLong());

	System.out.print("Nom: ");
	dto.setName(scanner.next());

	System.out.print("Date de mise en service aaaa-mm-jj: ");
	dto.setIntroduced(scanner.next());

	System.out.print("Date de mise hors service aaaa-mm-jj: ");
	dto.setDiscontinued(scanner.next());

	System.out.print("Identifiant du fabricant: ");
	dto.setMannufacturer(scanner.next());

	return dto;
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
    public void showGoodBye() {
	System.out.println("Au revoir");
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
    public Action getInputPage(boolean previous, boolean next) {
	while (true) {
	    System.out.println("0 - Menu");
	    if (previous) {
		System.out.println("1 - Précédent");
	    }
	    if (next) {
		System.out.println("2 - Suivant");
	    }
	    while (!scanner.hasNextInt()) {
		scanner.nextLine();
	    }
	    switch (scanner.nextInt()) {
	    case 0:
		return Action.RETURN;
	    case 1:
		if (previous) {
		    return Action.PREVIOUS;
		}
	    case 2:
		if (next) {
		    return Action.NEXT;
		}
	    default:
		break;
	    }
	}
    }

    @Override
    public void showMessage(String message) {
	System.out.println(message);
    }

}
