package com.excilys.cdb.shared.mapper;

public interface Mapper<I, O> {
    O map(I i);
}
