package com.excilys.cdb.mapper.dto;

public interface Mapper<From, To> {
    To map(From from);
}
