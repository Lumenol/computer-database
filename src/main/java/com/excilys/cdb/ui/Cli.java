package com.excilys.cdb.ui;

import com.excilys.cdb.controller.Controller;
import com.excilys.cdb.dto.ComputerDTO;
import com.excilys.cdb.dto.CreateComputerDTOUi;
import com.excilys.cdb.dto.UpdateComputerDTOUi;
import com.excilys.cdb.exception.ControllerException;
import com.excilys.cdb.mapper.dto.CreateComputerDTOUiToCreateComputerDTOMapper;
import com.excilys.cdb.mapper.dto.UpdateComputerDTOUiToUpdateComputerDTOMapper;
import com.excilys.cdb.validator.CreateComputerUIValidator;
import com.excilys.cdb.validator.UpdateComputerUIValidator;
import com.excilys.cdb.validator.Validator;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class Cli {

    public static final String DATE_FORMAT_ALLOWED = "Les dates doivents être null ou aaaa-mm-jj";
    public static final String ID_MANNUFACTURER_FORMAT_ALLOWED = "L'id du fabricant peut être null";
    private static final int PAGE_SIZE = 20;
    private final Controller controller = Controller.getInstance();
    private final Scanner in;
    private final PrintStream out;
    private final UpdateComputerUIValidator updateComputerUIValidator = UpdateComputerUIValidator.getInstance();
    private final UpdateComputerDTOUiToUpdateComputerDTOMapper updateComputerDTOUiToUpdateComputerDTOMapper = UpdateComputerDTOUiToUpdateComputerDTOMapper.getInstance();
    private final CreateComputerUIValidator createComputerUIValidator = CreateComputerUIValidator.getInstance();
    private final CreateComputerDTOUiToCreateComputerDTOMapper createComputerDTOUiToCreateComputerDTOMapper = CreateComputerDTOUiToCreateComputerDTOMapper.getInstance();
    private boolean quit = false;

    public Cli(InputStream in, PrintStream out) {
        this.in = new Scanner(in);
        this.out = out;
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

    private void showMenu() {
        printMenu();
        readInputMenu();
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
                default:
                    break;
            }
        }
    }

    private void quit() {
        quit = true;
        out.println("Au revoir");
    }

    private void updateComputer() {
        out.println("id | nom | date debut | date fin | id fabriquant");
        out.println(DATE_FORMAT_ALLOWED);
        out.println(ID_MANNUFACTURER_FORMAT_ALLOWED);
        final String delim = "\\|";
        final List<String> tokens = readLine(delim);
        final int numberOfToken = 5;
        if (tokens.size() == numberOfToken) {
            final UpdateComputerDTOUi dtoUi = new UpdateComputerDTOUi();
            dtoUi.setId(tokens.get(0));
            dtoUi.setName(tokens.get(1));
            dtoUi.setIntroduced(tokens.get(2));
            dtoUi.setDiscontinued(tokens.get(3));
            dtoUi.setMannufacturerId(tokens.get(4));
            final Validator.Result result = updateComputerUIValidator.check(dtoUi);
            if (result.isValid()) {
                controller.updateComputer(updateComputerDTOUiToUpdateComputerDTOMapper.map(dtoUi));
            } else {
                result.values().forEach(out::println);
            }
        } else {
            out.println("Il manque des informations");
        }
    }

    private String readLine() {
        while (true) {
            final String line = in.nextLine();
            if (!line.trim().isEmpty()) {
                return line;
            }
        }
    }

    private void createComputer() {
        out.println("nom | date debut | date fin | id fabriquant");
        out.println(DATE_FORMAT_ALLOWED);
        out.println(ID_MANNUFACTURER_FORMAT_ALLOWED);

        final String delim = "\\|";
        final List<String> tokens = readLine(delim);
        final int numberOfToken = 4;
        if (tokens.size() == numberOfToken) {
            final CreateComputerDTOUi dtoUi = new CreateComputerDTOUi();
            dtoUi.setName(tokens.get(0));
            dtoUi.setIntroduced(tokens.get(1));
            dtoUi.setDiscontinued(tokens.get(2));
            dtoUi.setMannufacturerId(tokens.get(3));
            final Validator.Result result = createComputerUIValidator.check(dtoUi);
            if (result.isValid()) {
                controller.createComputer(createComputerDTOUiToCreateComputerDTOMapper.map(dtoUi));
            } else {
                result.values().forEach(out::println);
            }
        } else {
            out.println("Il manque des informations");
        }
    }

    private List<String> readLine(String delim) {
        final String input = readLine();
        return Arrays.stream(input.split(delim)).map(String::trim).collect(Collectors.toList());
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

    private long readLong() {
        while (!in.hasNextLong()) {
            in.nextLine();
        }
        return in.nextLong();
    }

    private int readInt() {
        while (!in.hasNextInt()) {
            in.nextLine();
        }
        return in.nextInt();
    }

    private void showComputers() {
        showPage(offset -> controller.getComputers(offset, PAGE_SIZE).forEach(out::println), controller::numberOfComputers);
    }

    private void showPage(Consumer<Long> printEntities, Supplier<Long> getNumberOfEntities) {
        long offset = 0;
        while (true) {
            printEntities.accept(offset);
            boolean hasPrevious = offset > 0;
            final long numberOfEntities = getNumberOfEntities.get();
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

    private void showCompanies() {
        showPage(offset -> controller.getCompanies(offset, PAGE_SIZE).forEach(out::println), controller::numberOfCompanies);
    }

    private PageAction showInputPage(boolean hasPrevious, boolean hasNext) {
        printInputPage(hasPrevious, hasNext);
        return readInputPage(hasPrevious, hasNext);
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
                case 2:
                    if (hasNext) {
                        return PageAction.NEXT;
                    }
                default:
                    break;
            }
        }
    }

    private enum PageAction {
        MENU, NEXT, PREVIOUS
    }

}