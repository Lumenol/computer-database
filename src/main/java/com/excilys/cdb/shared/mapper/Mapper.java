package com.excilys.cdb.shared.mapper;

public interface Mapper<From, To> {
    To map(From from);
}
