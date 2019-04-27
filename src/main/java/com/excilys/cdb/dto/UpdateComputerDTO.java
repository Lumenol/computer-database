package com.excilys.cdb.dto;

public class UpdateComputerDTO extends CreateComputerDTO {
    private long id;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
