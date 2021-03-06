package com.excilys.cdb.persistence.dao;

import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.shared.pagination.OrderBy;
import com.excilys.cdb.shared.pagination.Pageable;

import java.time.LocalDate;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

class UTDatabase {

    private static UTDatabase instance;
    private final Map<Long, Company> companies = new TreeMap<>();
    private final Map<Long, Computer> computers = new TreeMap<>();

    private UTDatabase() {
        addCompanies();
        addComputers();
    }

    public static UTDatabase getInstance() {
        if (Objects.isNull(instance)) {
            instance = new UTDatabase();
        }
        return instance;
    }

    private void addCompanies() {
        addCompany(1, "Apple Inc.");
        addCompany(2, "Thinking Machines");
        addCompany(3, "RCA");
        addCompany(4, "Netronics");
        addCompany(5, "Tandy Corporation");
        addCompany(6, "Commodore International");
        addCompany(7, "MOS Technology");
        addCompany(8, "Micro Instrumentation and Telemetry Systems");
        addCompany(9, "IMS Associates, Inc.");
        addCompany(10, "Digital Equipment Corporation");
    }

    private void addCompany(long id, String name) {
        final Company company = Company.builder().id(id).name(name).build();
        companies.put(id, company);
    }

    private void addComputers() {
        addComputer(1, "MacBook Pro 15.4 inch", null, null, 1L);
        addComputer(2, "CM-2a", null, null, 2L);
        addComputer(3, "CM-200", null, null, 2L);
        addComputer(4, "CM-5e", null, null, 2L);
        addComputer(5, "CM-5", LocalDate.of(1991, 1, 1), null, 2L);
        addComputer(6, "MacBook Pro", LocalDate.of(2006, 1, 10), null, 1L);
        addComputer(7, "Apple IIe", null, null, null);
        addComputer(8, "Apple IIc", null, null, null);
        addComputer(9, "Apple IIGS", null, null, null);
        addComputer(10, "Apple IIc Plus", null, null, null);
        addComputer(11, "Apple II Plus", null, null, null);
        addComputer(12, "Apple III", LocalDate.of(1980, 5, 1), LocalDate.of(1984, 4, 1), 1L);
        addComputer(13, "Apple Lisa", null, null, 1L);
        addComputer(14, "CM-2", null, null, 2L);
        addComputer(15, "Connection Machine", LocalDate.of(1987, 1, 1), null, 2L);
        addComputer(16, "Apple II", LocalDate.of(1977, 4, 1), LocalDate.of(1993, 10, 1), 1L);
        addComputer(17, "Apple III Plus", LocalDate.of(1983, 12, 1), LocalDate.of(1984, 4, 1), 1L);
        addComputer(18, "COSMAC ELF", null, null, 3L);
        addComputer(19, "COSMAC VIP", LocalDate.of(1977, 1, 1), null, 3L);
        addComputer(20, "ELF II", LocalDate.of(1977, 1, 1), null, 4L);
    }

    private void addComputer(long id, String name, LocalDate introduced, LocalDate discontinued, Long companyId) {
        final Computer computer = Computer.builder().id(id).name(name).introduced(introduced).discontinued(discontinued)
                .manufacturer(findCompanyById(companyId)).build();
        computers.put(id, computer);
    }

    public Company findCompanyById(Long id) {
        return Objects.nonNull(id) ? companies.get(id) : null;
    }

    public Computer findComputerById(long id) {
        return computers.get(id);
    }

    public List<Computer> findAllComputers() {
        return computers.values().stream().sorted(Comparator.comparingLong(Computer::getId))
                .collect(Collectors.toList());
    }

    public List<Computer> findAllComputers(Pageable pageable) {
        return pagging(findAllComputers(), pageable);
    }

    private List<Computer> pagging(List<Computer> computers, Pageable pageable) {
        Comparator<Computer> comparator;
        final OrderBy.Field field = pageable.getOrderBy().getField();
        final OrderBy.Direction direction = pageable.getOrderBy().getDirection();
        final Comparator<Computer> byId = reverse(Comparator.comparingLong(Computer::getId), direction);
        final Comparator<Computer> byName = reverse(Comparator.comparing(Computer::getName), direction).thenComparing(byId);

        switch (field) {
            case ID:
                comparator = byId;
                break;
            case NAME:
                comparator = byName;
                break;
            case INTRODUCED: {
                final Function<Computer, LocalDate> keyExtractor = Computer::getIntroduced;
                comparator = comparatorComputer(keyExtractor, reverse(LocalDate::compareTo, direction)).thenComparing(byName);
            }
            break;
            case DISCONTINUED: {
                final Function<Computer, LocalDate> keyExtractor = Computer::getDiscontinued;
                comparator = comparatorComputer(keyExtractor, reverse(LocalDate::compareTo, direction)).thenComparing(byName);
            }
            break;
            case COMPANY: {
                final Function<Computer, Company> keyExtractor = Computer::getManufacturer;
                comparator = comparatorComputer(keyExtractor, reverse(Comparator.comparing(Company::getName), direction)).thenComparing(byName);
            }
            break;
            default:
                throw new IllegalArgumentException("Il n'y à pas de comparateur pour ce champ " + field);
        }

        return computers.stream().sorted(comparator).skip(pageable.getPage().getOffset())
                .limit(pageable.getPage().getSize()).collect(Collectors.toList());
    }

    private <T> Comparator<Computer> comparatorComputer(Function<Computer, T> keyExtractor, Comparator<T> comparator) {
        return (o1, o2) -> {
            final T k1 = keyExtractor.apply(o1);
            final T k2 = keyExtractor.apply(o2);
            if (k1 == k2) {
                return 0;
            } else if (Objects.isNull(k1)) {
                return 1;
            } else if (Objects.isNull(k2)) {
                return -1;
            } else {
                return comparator.compare(k1, k2);
            }
        };
    }

    private <T> Comparator<T> reverse(Comparator<T> comparator, OrderBy.Direction direction) {
        if (direction == OrderBy.Direction.DESC) {
            return comparator.reversed();
        } else {
            return comparator;
        }
    }

    public List<Company> findAllCompanies() {
        return companies.values().stream().sorted(Comparator.comparing(Company::getName)).collect(Collectors.toList());
    }

    public List<Company> findAllCompanies(long page, long limit) {
        final long offset = (page - 1) * limit;
        return findAllCompanies().stream().skip(offset).limit(limit).collect(Collectors.toList());
    }

    public List<Computer> searchComputer(Pageable pageable, String search) {
        String upper = search.toUpperCase();
        final List<Computer> computers = findAllComputers().stream()
                .filter(c -> c.getName().toUpperCase().contains(upper) || Objects.nonNull(c.getManufacturer())
                        && c.getManufacturer().getName().toUpperCase().contains(upper))
                .collect(Collectors.toList());

        return pagging(computers, pageable);
    }

}
