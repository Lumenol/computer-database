package com.excilys.cdb.database;

import com.excilys.cdb.dto.CompanyDTO;
import com.excilys.cdb.dto.ComputerDTO;

import java.time.LocalDate;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;

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
        final CompanyDTO company = new CompanyDTO();
        company.setId(id);
        company.setName(name);
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

        addComputer(50, "Commodore PET", null, null, 6L);
    }

    private void addComputer(long id, String name, LocalDate introduced, LocalDate discontinued, Long companyId) {
        final ComputerDTO computer = new ComputerDTO();
        computer.setId(id);
        computer.setName(name);
        computer.setIntroduced(introduced);
        computer.setDiscontinued(discontinued);
        computer.setMannufacturer(findCompanyById(companyId));

        computers.put(id, computer);
    }

    public CompanyDTO findCompanyById(Long id) {
        return Objects.nonNull(id) ? companies.get(id) : null;
    }

    public ComputerDTO findComputerById(long id) {
        return computers.get(id);
    }
}
