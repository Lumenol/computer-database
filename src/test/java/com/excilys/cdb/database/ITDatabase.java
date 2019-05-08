package com.excilys.cdb.database;

import com.excilys.cdb.dto.CompanyDTO;
import com.excilys.cdb.dto.ComputerDTO;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;

public class ITDatabase {
    private static ITDatabase instance;
    private Map<Long, CompanyDTO> companies = new TreeMap<>();
    private Map<Long, ComputerDTO> computers = new TreeMap<>();

    private ITDatabase() {
        addCompanies();
        addComputers();
    }

    public static ITDatabase getInstance() {
        if (Objects.isNull(instance)) {
            instance = new ITDatabase();
        }
        return instance;
    }

    private void addCompanies() {
        addCompany(1, "Apple Inc.");
        addCompany(24, "Nintendo");
        addCompany(25, "Sinclair Research Ltd");
        addCompany(38, "Amstrad");
    }

    private void addCompany(long id, String name) {
        final CompanyDTO company = CompanyDTO.builder().id(id).name(name).build();
        companies.put(id, company);
    }

    private LocalDate date(String date) {
        if (Objects.isNull(date)) {
            return null;
        }
        try {
            return LocalDate.parse(date);
        } catch (DateTimeParseException e) {
            return null;
        }
    }

    private void addComputers() {
        addComputer(10, "Apple IIc Plus", date(null), date(null), null);
        addComputer(13, "Apple Lisa", date(null), date(null), 1L);
        addComputer(55, "Apple I", date("1976-04-01"), date("1977-10-01"), 1L);
        addComputer(79, "Macintosh SE", date("1987-03-02"), date("1989-08-01"), 1L);
        addComputer(155, "Super Famicom", date(null), date(null), null);
        addComputer(160, "Game & Watch", date(null), date(null), 24L);
        addComputer(162, "IBM System/4 Pi", date(null), date(null), null);
        addComputer(281, "Game Boy", date(null), date(null), 24L);
        addComputer(314, "iPhone", date("2007-06-01"), date(null), 1L);
        addComputer(323, "ZX Spectrum 48K", date("1982-01-01"), date(null), 25L);
        addComputer(355, "ARRA", date(null), date(null), null);
        addComputer(367, "BINAC", date(null), date(null), null);
        addComputer(381, "ACE", date(null), date(null), null);
        addComputer(385, "ASCI Blue Mountain", date(null), date(null), null);
        addComputer(403, "Apple Network Server", date(null), date(null), null);
        addComputer(407, "CER-10", date(null), date(null), null);
        addComputer(413, "Amstrad CPC 664", date(null), date(null), 38L);
        addComputer(522, "D.K.COMMUNICATION", date(null), date(null), null);
        addComputer(566, "Upcoming iPhone 5", date(null), date(null), 1L);
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
