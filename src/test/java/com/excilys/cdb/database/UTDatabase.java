package com.excilys.cdb.database;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.TreeMap;
import java.util.stream.Collectors;

import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.persistence.ConnectionManager;
import com.excilys.cdb.persistence.page.OrderBy;
import com.excilys.cdb.persistence.page.Pageable;

public class UTDatabase {

    private static final String ENTRIES_SQL = "entriesUT.sql";

    private static final String SCHEMA_SQL = "schema.sql";

    private static UTDatabase instance = new UTDatabase();

    private Map<Long, Company> companies = new TreeMap<>();
    private Map<Long, Computer> computers = new TreeMap<>();

    private UTDatabase() {
	addCompanies();
	addComputers();
    }

    public static UTDatabase getInstance() {
	return instance;
    }

    /*
     * Ne retire pas les lignes de commentaires risque de ne pas marcher avec
     */
    private static void executeScript(String filename) throws SQLException, IOException {
	try (final Connection connection = ConnectionManager.getInstance().getConnection();
		final Statement statement = connection.createStatement();
		final InputStream resourceAsStream = UTDatabase.class.getClassLoader().getResourceAsStream(filename);
		final Scanner scanner = new Scanner(resourceAsStream)) {

	    StringBuilder sb = new StringBuilder();
	    while (scanner.hasNextLine()) {
		final String nextLine = scanner.nextLine();
		sb.append(nextLine);
	    }
	    final StringTokenizer stringTokenizer = new StringTokenizer(sb.toString(), ";");

	    while (stringTokenizer.hasMoreTokens()) {
		final String nextToken = stringTokenizer.nextToken();
		statement.execute(nextToken);
	    }
	}
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
	final Comparator<Computer> byName = Comparator.comparing(Computer::getName);
	switch (field) {
	case ID:
	    comparator = Comparator.comparingLong(Computer::getId);
	    break;
	case NAME:
	    comparator = byName;
	    break;
	case INTRODUCED:
	    comparator = (o1, o2) -> {
		if (o1.getIntroduced() == o2.getIntroduced()) {
		    return byName.compare(o1, o2);
		} else if (Objects.isNull(o1.getIntroduced())) {
		    return -1;
		} else if (Objects.isNull(o2.getIntroduced())) {
		    return 1;
		} else {
		    return o1.getIntroduced().compareTo(o2.getIntroduced());
		}
	    };
	    break;
	case DISCONTINUED:
	    comparator = (o1, o2) -> {
		if (o1.getDiscontinued() == o2.getDiscontinued()) {
		    return byName.compare(o1, o2);
		} else if (Objects.isNull(o1.getDiscontinued())) {
		    return -1;
		} else if (Objects.isNull(o2.getDiscontinued())) {
		    return 1;
		} else {
		    return o1.getDiscontinued().compareTo(o2.getDiscontinued());
		}
	    };
	    break;
	case COMPANY:
	    comparator = (o1, o2) -> {
		if (o1.getManufacturer() == o2.getManufacturer()) {
		    return byName.compare(o1, o2);
		} else if (Objects.isNull(o1.getManufacturer())) {
		    return -1;
		} else if (Objects.isNull(o2.getManufacturer())) {
		    return 1;
		} else {
		    return o1.getManufacturer().getName().compareTo(o2.getManufacturer().getName());
		}
	    };
	    break;
	default:
	    throw new IllegalArgumentException("Il n'y à pas de comparateur pour ce champ " + field);
	}

	comparator = comparator.thenComparing(byName);
	if (pageable.getOrderBy().getMeaning() == OrderBy.Meaning.DESC) {
	    comparator = comparator.reversed();
	}

	return computers.stream().sorted(comparator).skip(pageable.getPage().getOffset())
		.limit(pageable.getPage().getLimit()).collect(Collectors.toList());
    }

    public List<Company> findAllCompanies() {
	return companies.values().stream().sorted(Comparator.comparing(Company::getName)).collect(Collectors.toList());
    }

    public void reload() throws IOException, SQLException {
	executeScript(SCHEMA_SQL);
	executeScript(ENTRIES_SQL);
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
