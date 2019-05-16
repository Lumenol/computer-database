package com.excilys.cdb.ui;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;
import java.util.Scanner;
import java.util.function.LongConsumer;
import java.util.function.LongSupplier;
import java.util.stream.Collectors;

import com.excilys.cdb.controller.Controller;
import com.excilys.cdb.dto.ComputerDTO;
import com.excilys.cdb.exception.ControllerException;
import com.excilys.cdb.exception.ValidationException;

public class Cli {

    private static final String DATE_FORMAT_ALLOWED = "Les dates doivents être vide ou aaaa-mm-jj";
    private static final String ID_MANNUFACTURER_FORMAT_ALLOWED = "L'id du fabricant peut être vide";
    private static final int PAGE_SIZE = 20;
    private final Controller controller;
    private final Scanner in;
    private final PrintStream out;
    private boolean quit = false;

    public Cli(Controller controller, InputStream in, PrintStream out) {
	this.controller = controller;
	this.in = new Scanner(in);
	this.out = out;
    }

    private void createComputer() {
	out.println("nom | date debut | date fin | id fabriquant");
	out.println(DATE_FORMAT_ALLOWED);
	out.println(ID_MANNUFACTURER_FORMAT_ALLOWED);

	final String delim = "\\|";
	final List<String> tokens = readLine(delim);
	final int numberOfToken = 4;
	if (tokens.size() == numberOfToken) {
	    final String name = tokens.get(0);
	    final String introduced = tokens.get(1);
	    final String discontinued = tokens.get(2);
	    final String mannufacturerId = tokens.get(3);
	    try {
		controller.createComputer(name, introduced, discontinued, mannufacturerId);
	    } catch (ValidationException e) {
		out.println(e.getMessage());
	    }
	} else {
	    out.println("Il manque des informations");
	}
    }

    private void deleteComputer() {
	out.print("Entré l'id d'un ordinateur: ");
	final long id = readLong();
	Optional<ComputerDTO> computer = controller.getComputerById(id);
	if (computer.isPresent()) {
	    controller.deleteComputer(id);
	    out.println("L'ordinateur " + id + " a été supprimé.");
	} else {
	    out.println("Il n'y as pas d'ordinateur avec l'id " + id);
	}
    }

    private void printInputPage(boolean hasPrevious, boolean hasNext) {
	out.println("0 - Menu");
	if (hasPrevious) {
	    out.println("1 - Précédent");
	}
	if (hasNext) {
	    out.println("2 - Suivant");
	}
    }

    private void printMenu() {
	out.println("Le menu");
	out.println("0 - Quitter");
	out.println("1 - Lister les companies");
	out.println("2 - Lister les ordinateurs");
	out.println("3 - Détail d'un ordinateur");
	out.println("4 - Supprimer un ordinateur");
	out.println("5 - Crée un ordinateur");
	out.println("6 - Mettre à jour un ordinateur");
	out.println("7 - Supprimer une companie");
    }

    private void quit() {
	quit = true;
	out.println("Au revoir");
    }

    private void readInputMenu() {
	while (true) {
	    switch (readInt()) {
	    case 0:
		quit();
		return;
	    case 1:
		showCompanies();
		return;
	    case 2:
		showComputers();
		return;
	    case 3:
		showComputerDetails();
		return;
	    case 4:
		deleteComputer();
		return;
	    case 5:
		createComputer();
		return;
	    case 6:
		updateComputer();
		return;
	    case 7:
		deleteCompany();
		return;
	    default:
		break;
	    }
	}
    }

    private void deleteCompany() {
	out.print("Entré l'id d'une companie: ");
	final long id = readLong();
	if (controller.companyExist(id)) {
	    controller.deleteCompany(id);
	    out.println("La company " + id + " a été supprimé.");
	} else {
	    out.println("Il n'y as pas de company avec l'id " + id);
	}
    }

    private PageAction readInputPage(boolean hasPrevious, boolean hasNext) {
	while (true) {
	    switch (readInt()) {
	    case 0:
		return PageAction.MENU;
	    case 1:
		if (hasPrevious) {
		    return PageAction.PREVIOUS;
		}
		break;
	    case 2:
		if (hasNext) {
		    return PageAction.NEXT;
		}
		break;
	    default:
		break;
	    }
	}
    }

    private int readInt() {
	while (!in.hasNextInt()) {
	    in.nextLine();
	}
	return in.nextInt();
    }

    private String readLine() {
	while (true) {
	    final String line = in.nextLine();
	    if (!line.trim().isEmpty()) {
		return line;
	    }
	}
    }

    private List<String> readLine(String delim) {
	final String input = readLine();
	return Arrays.stream(input.split(delim)).map(String::trim).collect(Collectors.toList());
    }

    private long readLong() {
	while (!in.hasNextLong()) {
	    in.nextLine();
	}
	return in.nextLong();
    }

    public void run() {
	while (!quit) {
	    try {
		showMenu();
	    } catch (ControllerException e) {
		out.println("Une erreur est survenue");
		if (Objects.nonNull(e.getMessage())) {
		    out.println(e.getMessage());
		}
	    } catch (NoSuchElementException e) {
		quit();
	    } catch (RuntimeException e) {
		out.println("Une erreur est survenue");
	    }
	}
    }

    private void showCompanies() {
	showPage(offset -> controller.getCompanies(offset, PAGE_SIZE).forEach(out::println),
		controller::numberOfCompanies);
    }

    private void showComputerDetails() {
	out.print("Entré l'id d'un ordinateur: ");
	final long id = readLong();
	Optional<ComputerDTO> computer = controller.getComputerById(id);
	if (computer.isPresent()) {
	    out.println(computer.get());
	} else {
	    out.println("Il n'y as pas d'ordinateur avec l'id " + id);
	}
    }

    private void showComputers() {
	showPage(offset -> controller.getComputers(offset, PAGE_SIZE).forEach(out::println),
		controller::numberOfComputers);
    }

    private PageAction showInputPage(boolean hasPrevious, boolean hasNext) {
	printInputPage(hasPrevious, hasNext);
	return readInputPage(hasPrevious, hasNext);
    }

    private void showMenu() {
	printMenu();
	readInputMenu();
    }

    private void showPage(LongConsumer printEntities, LongSupplier getNumberOfEntities) {
	long offset = 0;
	while (true) {
	    printEntities.accept(offset);
	    boolean hasPrevious = offset > 0;
	    final long numberOfEntities = getNumberOfEntities.getAsLong();
	    boolean hasNext = offset + PAGE_SIZE < numberOfEntities;
	    switch (showInputPage(hasPrevious, hasNext)) {
	    case MENU:
		return;
	    case NEXT:
		offset = Math.min(offset + PAGE_SIZE, numberOfEntities);
		break;
	    case PREVIOUS:
		offset = Math.max(offset - PAGE_SIZE, 0);
		break;
	    }
	}
    }

    private void updateComputer() {
	out.println("id | nom | date debut | date fin | id fabriquant");
	out.println(DATE_FORMAT_ALLOWED);
	out.println(ID_MANNUFACTURER_FORMAT_ALLOWED);
	final String delim = "\\|";
	final List<String> tokens = readLine(delim);
	final int numberOfToken = 5;
	if (tokens.size() == numberOfToken) {
	    final String id = tokens.get(0);
	    final String name = tokens.get(1);
	    final String introduced = tokens.get(2);
	    final String discontinued = tokens.get(3);
	    final String mannufacturerId = tokens.get(4);
	    try {
		controller.updateComputer(id, name, introduced, discontinued, mannufacturerId);
	    } catch (ValidationException e) {
		out.println(e.getMessage());
	    }
	} else {
	    out.println("Il manque des informations");
	}
    }

    private enum PageAction {
	MENU, NEXT, PREVIOUS
    }

}
