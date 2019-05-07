package com.excilys.cdb.database;

import java.time.LocalDate;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;

import com.excilys.cdb.dto.CompanyDTO;
import com.excilys.cdb.dto.ComputerDTO;

public class ITDatabase {
    private static ITDatabase instance = new ITDatabase();

    private Map<Long, CompanyDTO> companies = new TreeMap<>();
    private Map<Long, ComputerDTO> computers = new TreeMap<>();

    private ITDatabase() {
	addCompanies();
	addComputers();
    }

    public static ITDatabase getInstance() {
	return instance;
    }

    private void addCompanies() {
	addCompany(1, "Apple Inc.");
	addCompany(6, "Commodore International");
	addCompany(13, "IBM");
	addCompany(19, "NeXT");
	addCompany(29, "ACVS");
    }

    private void addCompany(long id, String name) {
	final CompanyDTO company = CompanyDTO.builder().id(id).name(name).build();
	companies.put(id, company);
    }

    private void addComputers() {
	addComputer(1, "MacBook Pro 15.4 inch", null, null, 1L);
	addComputer(16, "Apple II", LocalDate.of(1977, 4, 1), LocalDate.of(1993, 10, 1), 1L);
	addComputer(17, "Apple III Plus", LocalDate.of(1983, 12, 1), LocalDate.of(1984, 4, 1), 1L);
	addComputer(50, "Commodore PET", null, null, 6L);
	addComputer(31, "Powerbook 100", null, null, null);
	addComputer(39, "Macintosh LC", LocalDate.of(1990, 1, 1), null, 1L);
	addComputer(40, "Macintosh LC II", LocalDate.of(1990, 1, 1), null, 1L);
	addComputer(51, "Commodore 64", LocalDate.of(1982, 8, 1), LocalDate.of(1994, 1, 1), 6L);
	addComputer(91, "NeXTcube", LocalDate.of(1988, 1, 1), LocalDate.of(1993, 1, 1), 19L);
	addComputer(100, "Z4", null, null, null);
	addComputer(151, "Xerox Alto", null, null, null);
	addComputer(200, "PowerBook 500 series", null, null, null);
	addComputer(201, "Power Macintosh G3", null, null, null);
	addComputer(250, "MSX2", LocalDate.of(1986, 1, 1), null, 29L);
	addComputer(501, "AN/FSQ-32", LocalDate.of(1980, 1, 1), null, 13L);
	addComputer(516, "MacBook Parts", null, null, 1L);
	addComputer(574, "iPhone 4S", LocalDate.of(2011, 10, 14), null, 1L);
    }

    private void addComputer(long id, String name, LocalDate introduced, LocalDate discontinued, Long companyId) {
	final ComputerDTO.ComputerDTOBuilder builder = ComputerDTO.builder().id(id).name(name)
		.introduced(Objects.toString(introduced, null)).discontinued(Objects.toString(discontinued, null));

	final CompanyDTO company = findCompanyById(companyId);
	if (Objects.nonNull(company)) {
	    builder.mannufacturer(company.getName());
	}

	computers.put(id, builder.build());
    }

    public CompanyDTO findCompanyById(Long id) {
	return Objects.nonNull(id) ? companies.get(id) : null;
    }

    public ComputerDTO findComputerById(long id) {
	return computers.get(id);
    }
}
