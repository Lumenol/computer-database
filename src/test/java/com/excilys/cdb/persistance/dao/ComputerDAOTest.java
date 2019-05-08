package com.excilys.cdb.persistance.dao;

import com.excilys.cdb.database.UTDatabase;
import com.excilys.cdb.exception.ComputerDAOException;
import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.model.Computer.ComputerBuilder;
import com.excilys.cdb.persistence.dao.ComputerDAO;
import com.excilys.cdb.persistence.page.OrderBy;
import com.excilys.cdb.persistence.page.Page;
import com.excilys.cdb.persistence.page.Pageable;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.Assert.*;

@RunWith(JUnitParamsRunner.class)
public class ComputerDAOTest {

    public Object[] provideComputerId() {
        final Stream.Builder<Long> builder = Stream.builder();
        final List<Computer> allComputers = UTDatabase.getInstance().findAllComputers();
        final Computer lastComputers = allComputers.get(allComputers.size() - 1);
        for (long i = -5; i < lastComputers.getId() + 5; i++) {
            builder.add(i);
        }
        return builder.build().toArray();
    }

    public Object[] providePageable() {
        final ArrayList<Object[]> params = new ArrayList<>();

        //Sorted By ID ASC
        {
            final OrderBy orderBy = OrderBy.builder().build();
            Page page = Page.builder().offset(0).limit(30).build();
            final Pageable pageable = Pageable.builder().page(page).orderBy(orderBy).build();
            params.add(new Object[]{pageable});
        }

        {
            final OrderBy orderBy = OrderBy.builder().build();
            Page page = Page.builder().offset(0).limit(5).build();
            final Pageable pageable = Pageable.builder().page(page).orderBy(orderBy).build();
            params.add(new Object[]{pageable});
        }

        {
            final OrderBy orderBy = OrderBy.builder().build();
            Page page = Page.builder().offset(5).limit(5).build();
            final Pageable pageable = Pageable.builder().page(page).orderBy(orderBy).build();
            params.add(new Object[]{pageable});
        }

        {
            final OrderBy orderBy = OrderBy.builder().build();
            Page page = Page.builder().offset(13).limit(25).build();
            final Pageable pageable = Pageable.builder().page(page).orderBy(orderBy).build();
            params.add(new Object[]{pageable});
        }

        //Sorted By ID DESC
        {
            final OrderBy orderBy = OrderBy.builder().meaning(OrderBy.Meaning.DESC).build();
            Page page = Page.builder().offset(0).limit(30).build();
            final Pageable pageable = Pageable.builder().page(page).orderBy(orderBy).build();
            params.add(new Object[]{pageable});
        }

        {
            final OrderBy orderBy = OrderBy.builder().meaning(OrderBy.Meaning.DESC).build();
            Page page = Page.builder().offset(0).limit(5).build();
            final Pageable pageable = Pageable.builder().page(page).orderBy(orderBy).build();
            params.add(new Object[]{pageable});
        }

        {
            final OrderBy orderBy = OrderBy.builder().meaning(OrderBy.Meaning.DESC).build();
            Page page = Page.builder().offset(5).limit(5).build();
            final Pageable pageable = Pageable.builder().page(page).orderBy(orderBy).build();
            params.add(new Object[]{pageable});
        }

        {
            final OrderBy orderBy = OrderBy.builder().meaning(OrderBy.Meaning.DESC).build();
            Page page = Page.builder().offset(13).limit(25).build();
            final Pageable pageable = Pageable.builder().page(page).orderBy(orderBy).build();
            params.add(new Object[]{pageable});
        }

        //Sorted By NAME ASC
        {
            final OrderBy orderBy = OrderBy.builder().field(OrderBy.Field.NAME).build();
            Page page = Page.builder().offset(0).limit(30).build();
            final Pageable pageable = Pageable.builder().page(page).orderBy(orderBy).build();
            params.add(new Object[]{pageable});
        }

        {
            final OrderBy orderBy = OrderBy.builder().field(OrderBy.Field.NAME).build();
            Page page = Page.builder().offset(0).limit(5).build();
            final Pageable pageable = Pageable.builder().page(page).orderBy(orderBy).build();
            params.add(new Object[]{pageable});
        }

        {
            final OrderBy orderBy = OrderBy.builder().field(OrderBy.Field.NAME).build();
            Page page = Page.builder().offset(5).limit(5).build();
            final Pageable pageable = Pageable.builder().page(page).orderBy(orderBy).build();
            params.add(new Object[]{pageable});
        }

        {
            final OrderBy orderBy = OrderBy.builder().field(OrderBy.Field.NAME).build();
            Page page = Page.builder().offset(13).limit(25).build();
            final Pageable pageable = Pageable.builder().page(page).orderBy(orderBy).build();
            params.add(new Object[]{pageable});
        }

        //Sorted By NAME DESC
        {
            final OrderBy orderBy = OrderBy.builder().field(OrderBy.Field.NAME).meaning(OrderBy.Meaning.DESC).build();
            Page page = Page.builder().offset(0).limit(30).build();
            final Pageable pageable = Pageable.builder().page(page).orderBy(orderBy).build();
            params.add(new Object[]{pageable});
        }

        {
            final OrderBy orderBy = OrderBy.builder().field(OrderBy.Field.NAME).meaning(OrderBy.Meaning.DESC).build();
            Page page = Page.builder().offset(0).limit(5).build();
            final Pageable pageable = Pageable.builder().page(page).orderBy(orderBy).build();
            params.add(new Object[]{pageable});
        }

        {
            final OrderBy orderBy = OrderBy.builder().field(OrderBy.Field.NAME).meaning(OrderBy.Meaning.DESC).build();
            Page page = Page.builder().offset(5).limit(5).build();
            final Pageable pageable = Pageable.builder().page(page).orderBy(orderBy).build();
            params.add(new Object[]{pageable});
        }

        {
            final OrderBy orderBy = OrderBy.builder().field(OrderBy.Field.NAME).meaning(OrderBy.Meaning.DESC).build();
            Page page = Page.builder().offset(13).limit(25).build();
            final Pageable pageable = Pageable.builder().page(page).orderBy(orderBy).build();
            params.add(new Object[]{pageable});
        }
        //Sorted By INTRODUCED ASC
        {
            final OrderBy orderBy = OrderBy.builder().field(OrderBy.Field.INTRODUCED).build();
            Page page = Page.builder().offset(0).limit(30).build();
            final Pageable pageable = Pageable.builder().page(page).orderBy(orderBy).build();
            params.add(new Object[]{pageable});
        }

        {
            final OrderBy orderBy = OrderBy.builder().field(OrderBy.Field.INTRODUCED).build();
            Page page = Page.builder().offset(0).limit(5).build();
            final Pageable pageable = Pageable.builder().page(page).orderBy(orderBy).build();
            params.add(new Object[]{pageable});
        }

        {
            final OrderBy orderBy = OrderBy.builder().field(OrderBy.Field.INTRODUCED).build();
            Page page = Page.builder().offset(5).limit(5).build();
            final Pageable pageable = Pageable.builder().page(page).orderBy(orderBy).build();
            params.add(new Object[]{pageable});
        }

        {
            final OrderBy orderBy = OrderBy.builder().field(OrderBy.Field.INTRODUCED).build();
            Page page = Page.builder().offset(13).limit(25).build();
            final Pageable pageable = Pageable.builder().page(page).orderBy(orderBy).build();
            params.add(new Object[]{pageable});
        }

        //Sorted By INTRODUCED DESC
        {
            final OrderBy orderBy = OrderBy.builder().field(OrderBy.Field.INTRODUCED).meaning(OrderBy.Meaning.DESC).build();
            Page page = Page.builder().offset(0).limit(30).build();
            final Pageable pageable = Pageable.builder().page(page).orderBy(orderBy).build();
            params.add(new Object[]{pageable});
        }

        {
            final OrderBy orderBy = OrderBy.builder().field(OrderBy.Field.INTRODUCED).meaning(OrderBy.Meaning.DESC).build();
            Page page = Page.builder().offset(0).limit(5).build();
            final Pageable pageable = Pageable.builder().page(page).orderBy(orderBy).build();
            params.add(new Object[]{pageable});
        }

        {
            final OrderBy orderBy = OrderBy.builder().field(OrderBy.Field.INTRODUCED).meaning(OrderBy.Meaning.DESC).build();
            Page page = Page.builder().offset(5).limit(5).build();
            final Pageable pageable = Pageable.builder().page(page).orderBy(orderBy).build();
            params.add(new Object[]{pageable});
        }

        {
            final OrderBy orderBy = OrderBy.builder().field(OrderBy.Field.INTRODUCED).meaning(OrderBy.Meaning.DESC).build();
            Page page = Page.builder().offset(13).limit(25).build();
            final Pageable pageable = Pageable.builder().page(page).orderBy(orderBy).build();
            params.add(new Object[]{pageable});
        }

        //Sorted By DISCONTINUED ASC
        {
            final OrderBy orderBy = OrderBy.builder().field(OrderBy.Field.DISCONTINUED).build();
            Page page = Page.builder().offset(0).limit(30).build();
            final Pageable pageable = Pageable.builder().page(page).orderBy(orderBy).build();
            params.add(new Object[]{pageable});
        }

        {
            final OrderBy orderBy = OrderBy.builder().field(OrderBy.Field.DISCONTINUED).build();
            Page page = Page.builder().offset(0).limit(5).build();
            final Pageable pageable = Pageable.builder().page(page).orderBy(orderBy).build();
            params.add(new Object[]{pageable});
        }

        {
            final OrderBy orderBy = OrderBy.builder().field(OrderBy.Field.DISCONTINUED).build();
            Page page = Page.builder().offset(5).limit(5).build();
            final Pageable pageable = Pageable.builder().page(page).orderBy(orderBy).build();
            params.add(new Object[]{pageable});
        }

        {
            final OrderBy orderBy = OrderBy.builder().field(OrderBy.Field.DISCONTINUED).build();
            Page page = Page.builder().offset(13).limit(25).build();
            final Pageable pageable = Pageable.builder().page(page).orderBy(orderBy).build();
            params.add(new Object[]{pageable});
        }

        //Sorted By DISCONTINUED DESC
        {
            final OrderBy orderBy = OrderBy.builder().field(OrderBy.Field.DISCONTINUED).meaning(OrderBy.Meaning.DESC).build();
            Page page = Page.builder().offset(0).limit(30).build();
            final Pageable pageable = Pageable.builder().page(page).orderBy(orderBy).build();
            params.add(new Object[]{pageable});
        }

        {
            final OrderBy orderBy = OrderBy.builder().field(OrderBy.Field.DISCONTINUED).meaning(OrderBy.Meaning.DESC).build();
            Page page = Page.builder().offset(0).limit(5).build();
            final Pageable pageable = Pageable.builder().page(page).orderBy(orderBy).build();
            params.add(new Object[]{pageable});
        }

        {
            final OrderBy orderBy = OrderBy.builder().field(OrderBy.Field.DISCONTINUED).meaning(OrderBy.Meaning.DESC).build();
            Page page = Page.builder().offset(5).limit(5).build();
            final Pageable pageable = Pageable.builder().page(page).orderBy(orderBy).build();
            params.add(new Object[]{pageable});
        }

        {
            final OrderBy orderBy = OrderBy.builder().field(OrderBy.Field.DISCONTINUED).meaning(OrderBy.Meaning.DESC).build();
            Page page = Page.builder().offset(13).limit(25).build();
            final Pageable pageable = Pageable.builder().page(page).orderBy(orderBy).build();
            params.add(new Object[]{pageable});
        }

        //Sorted By COMPANY ASC
        {
            final OrderBy orderBy = OrderBy.builder().field(OrderBy.Field.COMPANY).build();
            Page page = Page.builder().offset(0).limit(30).build();
            final Pageable pageable = Pageable.builder().page(page).orderBy(orderBy).build();
            params.add(new Object[]{pageable});
        }

        {
            final OrderBy orderBy = OrderBy.builder().field(OrderBy.Field.COMPANY).build();
            Page page = Page.builder().offset(0).limit(5).build();
            final Pageable pageable = Pageable.builder().page(page).orderBy(orderBy).build();
            params.add(new Object[]{pageable});
        }

        {
            final OrderBy orderBy = OrderBy.builder().field(OrderBy.Field.COMPANY).build();
            Page page = Page.builder().offset(5).limit(5).build();
            final Pageable pageable = Pageable.builder().page(page).orderBy(orderBy).build();
            params.add(new Object[]{pageable});
        }

        {
            final OrderBy orderBy = OrderBy.builder().field(OrderBy.Field.COMPANY).build();
            Page page = Page.builder().offset(13).limit(25).build();
            final Pageable pageable = Pageable.builder().page(page).orderBy(orderBy).build();
            params.add(new Object[]{pageable});
        }

        //Sorted By COMPANY DESC
        {
            final OrderBy orderBy = OrderBy.builder().field(OrderBy.Field.COMPANY).meaning(OrderBy.Meaning.DESC).build();
            Page page = Page.builder().offset(0).limit(30).build();
            final Pageable pageable = Pageable.builder().page(page).orderBy(orderBy).build();
            params.add(new Object[]{pageable});
        }

        {
            final OrderBy orderBy = OrderBy.builder().field(OrderBy.Field.COMPANY).meaning(OrderBy.Meaning.DESC).build();
            Page page = Page.builder().offset(0).limit(5).build();
            final Pageable pageable = Pageable.builder().page(page).orderBy(orderBy).build();
            params.add(new Object[]{pageable});
        }

        {
            final OrderBy orderBy = OrderBy.builder().field(OrderBy.Field.COMPANY).meaning(OrderBy.Meaning.DESC).build();
            Page page = Page.builder().offset(5).limit(5).build();
            final Pageable pageable = Pageable.builder().page(page).orderBy(orderBy).build();
            params.add(new Object[]{pageable});
        }

        {
            final OrderBy orderBy = OrderBy.builder().field(OrderBy.Field.COMPANY).meaning(OrderBy.Meaning.DESC).build();
            Page page = Page.builder().offset(13).limit(25).build();
            final Pageable pageable = Pageable.builder().page(page).orderBy(orderBy).build();
            params.add(new Object[]{pageable});
        }

        return params.toArray();
    }

    public Object[] providePageableAndSearch() {
        final ArrayList<Object[]> params = new ArrayList<>();

        //Sorted By ID ASC
        {
            final OrderBy orderBy = OrderBy.builder().build();
            Page page = Page.builder().offset(0).limit(2).build();
            final Pageable pageable = Pageable.builder().page(page).orderBy(orderBy).build();
            params.add(new Object[]{pageable, "Apple"});
        }

        {
            final OrderBy orderBy = OrderBy.builder().build();
            Page page = Page.builder().offset(10).limit(4).build();
            final Pageable pageable = Pageable.builder().page(page).orderBy(orderBy).build();
            params.add(new Object[]{pageable, "Apple"});
        }

        {
            final OrderBy orderBy = OrderBy.builder().build();
            Page page = Page.builder().offset(0).limit(5).build();
            final Pageable pageable = Pageable.builder().page(page).orderBy(orderBy).build();
            params.add(new Object[]{pageable, "pLe"});
        }

        {
            final OrderBy orderBy = OrderBy.builder().build();
            Page page = Page.builder().offset(5).limit(10).build();
            final Pageable pageable = Pageable.builder().page(page).orderBy(orderBy).build();
            params.add(new Object[]{pageable, "ApPl"});
        }
        //Sorted By ID DESC
        {
            final OrderBy orderBy = OrderBy.builder().meaning(OrderBy.Meaning.DESC).build();
            Page page = Page.builder().offset(0).limit(2).build();
            final Pageable pageable = Pageable.builder().page(page).orderBy(orderBy).build();
            params.add(new Object[]{pageable, "Apple"});
        }

        {
            final OrderBy orderBy = OrderBy.builder().meaning(OrderBy.Meaning.DESC).build();
            Page page = Page.builder().offset(10).limit(4).build();
            final Pageable pageable = Pageable.builder().page(page).orderBy(orderBy).build();
            params.add(new Object[]{pageable, "Apple"});
        }

        {
            final OrderBy orderBy = OrderBy.builder().meaning(OrderBy.Meaning.DESC).build();
            Page page = Page.builder().offset(0).limit(5).build();
            final Pageable pageable = Pageable.builder().page(page).orderBy(orderBy).build();
            params.add(new Object[]{pageable, "pLe"});
        }

        {
            final OrderBy orderBy = OrderBy.builder().meaning(OrderBy.Meaning.DESC).build();
            Page page = Page.builder().offset(5).limit(10).build();
            final Pageable pageable = Pageable.builder().page(page).orderBy(orderBy).build();
            params.add(new Object[]{pageable, "ApPl"});
        }

        //Sorted By NAME ASC
        {
            final OrderBy orderBy = OrderBy.builder().field(OrderBy.Field.NAME).build();
            Page page = Page.builder().offset(0).limit(2).build();
            final Pageable pageable = Pageable.builder().page(page).orderBy(orderBy).build();
            params.add(new Object[]{pageable, "Apple"});
        }

        {
            final OrderBy orderBy = OrderBy.builder().field(OrderBy.Field.NAME).build();
            Page page = Page.builder().offset(10).limit(4).build();
            final Pageable pageable = Pageable.builder().page(page).orderBy(orderBy).build();
            params.add(new Object[]{pageable, "Apple"});
        }

        {
            final OrderBy orderBy = OrderBy.builder().field(OrderBy.Field.NAME).build();
            Page page = Page.builder().offset(0).limit(5).build();
            final Pageable pageable = Pageable.builder().page(page).orderBy(orderBy).build();
            params.add(new Object[]{pageable, "pLe"});
        }

        {
            final OrderBy orderBy = OrderBy.builder().field(OrderBy.Field.NAME).build();
            Page page = Page.builder().offset(5).limit(10).build();
            final Pageable pageable = Pageable.builder().page(page).orderBy(orderBy).build();
            params.add(new Object[]{pageable, "ApPl"});
        }
        //Sorted By NAME DESC
        {
            final OrderBy orderBy = OrderBy.builder().field(OrderBy.Field.NAME).meaning(OrderBy.Meaning.DESC).build();
            Page page = Page.builder().offset(0).limit(2).build();
            final Pageable pageable = Pageable.builder().page(page).orderBy(orderBy).build();
            params.add(new Object[]{pageable, "Apple"});
        }

        {
            final OrderBy orderBy = OrderBy.builder().field(OrderBy.Field.NAME).meaning(OrderBy.Meaning.DESC).build();
            Page page = Page.builder().offset(10).limit(4).build();
            final Pageable pageable = Pageable.builder().page(page).orderBy(orderBy).build();
            params.add(new Object[]{pageable, "Apple"});
        }

        {
            final OrderBy orderBy = OrderBy.builder().field(OrderBy.Field.NAME).meaning(OrderBy.Meaning.DESC).build();
            Page page = Page.builder().offset(0).limit(5).build();
            final Pageable pageable = Pageable.builder().page(page).orderBy(orderBy).build();
            params.add(new Object[]{pageable, "pLe"});
        }

        {
            final OrderBy orderBy = OrderBy.builder().field(OrderBy.Field.NAME).meaning(OrderBy.Meaning.DESC).build();
            Page page = Page.builder().offset(5).limit(10).build();
            final Pageable pageable = Pageable.builder().page(page).orderBy(orderBy).build();
            params.add(new Object[]{pageable, "ApPl"});
        }

        //Sorted By INTRODUCED ASC
        {
            final OrderBy orderBy = OrderBy.builder().field(OrderBy.Field.INTRODUCED).build();
            Page page = Page.builder().offset(0).limit(2).build();
            final Pageable pageable = Pageable.builder().page(page).orderBy(orderBy).build();
            params.add(new Object[]{pageable, "Apple"});
        }

        {
            final OrderBy orderBy = OrderBy.builder().field(OrderBy.Field.INTRODUCED).build();
            Page page = Page.builder().offset(10).limit(4).build();
            final Pageable pageable = Pageable.builder().page(page).orderBy(orderBy).build();
            params.add(new Object[]{pageable, "Apple"});
        }

        {
            final OrderBy orderBy = OrderBy.builder().field(OrderBy.Field.INTRODUCED).build();
            Page page = Page.builder().offset(0).limit(5).build();
            final Pageable pageable = Pageable.builder().page(page).orderBy(orderBy).build();
            params.add(new Object[]{pageable, "pLe"});
        }

        {
            final OrderBy orderBy = OrderBy.builder().field(OrderBy.Field.INTRODUCED).build();
            Page page = Page.builder().offset(5).limit(10).build();
            final Pageable pageable = Pageable.builder().page(page).orderBy(orderBy).build();
            params.add(new Object[]{pageable, "ApPl"});
        }
        //Sorted By INTRODUCED DESC
        {
            final OrderBy orderBy = OrderBy.builder().field(OrderBy.Field.INTRODUCED).meaning(OrderBy.Meaning.DESC).build();
            Page page = Page.builder().offset(0).limit(2).build();
            final Pageable pageable = Pageable.builder().page(page).orderBy(orderBy).build();
            params.add(new Object[]{pageable, "Apple"});
        }

        {
            final OrderBy orderBy = OrderBy.builder().field(OrderBy.Field.INTRODUCED).meaning(OrderBy.Meaning.DESC).build();
            Page page = Page.builder().offset(10).limit(4).build();
            final Pageable pageable = Pageable.builder().page(page).orderBy(orderBy).build();
            params.add(new Object[]{pageable, "Apple"});
        }

        {
            final OrderBy orderBy = OrderBy.builder().field(OrderBy.Field.INTRODUCED).meaning(OrderBy.Meaning.DESC).build();
            Page page = Page.builder().offset(0).limit(5).build();
            final Pageable pageable = Pageable.builder().page(page).orderBy(orderBy).build();
            params.add(new Object[]{pageable, "pLe"});
        }

        {
            final OrderBy orderBy = OrderBy.builder().field(OrderBy.Field.INTRODUCED).meaning(OrderBy.Meaning.DESC).build();
            Page page = Page.builder().offset(5).limit(10).build();
            final Pageable pageable = Pageable.builder().page(page).orderBy(orderBy).build();
            params.add(new Object[]{pageable, "ApPl"});
        }

        //Sorted By DISCONTINUED ASC
        {
            final OrderBy orderBy = OrderBy.builder().field(OrderBy.Field.DISCONTINUED).build();
            Page page = Page.builder().offset(0).limit(2).build();
            final Pageable pageable = Pageable.builder().page(page).orderBy(orderBy).build();
            params.add(new Object[]{pageable, "Apple"});
        }

        {
            final OrderBy orderBy = OrderBy.builder().field(OrderBy.Field.DISCONTINUED).build();
            Page page = Page.builder().offset(10).limit(4).build();
            final Pageable pageable = Pageable.builder().page(page).orderBy(orderBy).build();
            params.add(new Object[]{pageable, "Apple"});
        }

        {
            final OrderBy orderBy = OrderBy.builder().field(OrderBy.Field.DISCONTINUED).build();
            Page page = Page.builder().offset(0).limit(5).build();
            final Pageable pageable = Pageable.builder().page(page).orderBy(orderBy).build();
            params.add(new Object[]{pageable, "pLe"});
        }

        {
            final OrderBy orderBy = OrderBy.builder().field(OrderBy.Field.DISCONTINUED).build();
            Page page = Page.builder().offset(5).limit(10).build();
            final Pageable pageable = Pageable.builder().page(page).orderBy(orderBy).build();
            params.add(new Object[]{pageable, "ApPl"});
        }
        //Sorted By DISCONTINUED DESC
        {
            final OrderBy orderBy = OrderBy.builder().field(OrderBy.Field.DISCONTINUED).meaning(OrderBy.Meaning.DESC).build();
            Page page = Page.builder().offset(0).limit(2).build();
            final Pageable pageable = Pageable.builder().page(page).orderBy(orderBy).build();
            params.add(new Object[]{pageable, "Apple"});
        }

        {
            final OrderBy orderBy = OrderBy.builder().field(OrderBy.Field.DISCONTINUED).meaning(OrderBy.Meaning.DESC).build();
            Page page = Page.builder().offset(10).limit(4).build();
            final Pageable pageable = Pageable.builder().page(page).orderBy(orderBy).build();
            params.add(new Object[]{pageable, "Apple"});
        }

        {
            final OrderBy orderBy = OrderBy.builder().field(OrderBy.Field.DISCONTINUED).meaning(OrderBy.Meaning.DESC).build();
            Page page = Page.builder().offset(0).limit(5).build();
            final Pageable pageable = Pageable.builder().page(page).orderBy(orderBy).build();
            params.add(new Object[]{pageable, "pLe"});
        }

        {
            final OrderBy orderBy = OrderBy.builder().field(OrderBy.Field.DISCONTINUED).meaning(OrderBy.Meaning.DESC).build();
            Page page = Page.builder().offset(5).limit(10).build();
            final Pageable pageable = Pageable.builder().page(page).orderBy(orderBy).build();
            params.add(new Object[]{pageable, "ApPl"});
        }

        //Sorted By COMPANY ASC
        {
            final OrderBy orderBy = OrderBy.builder().field(OrderBy.Field.COMPANY).build();
            Page page = Page.builder().offset(0).limit(2).build();
            final Pageable pageable = Pageable.builder().page(page).orderBy(orderBy).build();
            params.add(new Object[]{pageable, "Apple"});
        }

        {
            final OrderBy orderBy = OrderBy.builder().field(OrderBy.Field.COMPANY).build();
            Page page = Page.builder().offset(10).limit(4).build();
            final Pageable pageable = Pageable.builder().page(page).orderBy(orderBy).build();
            params.add(new Object[]{pageable, "Apple"});
        }

        {
            final OrderBy orderBy = OrderBy.builder().field(OrderBy.Field.COMPANY).build();
            Page page = Page.builder().offset(0).limit(5).build();
            final Pageable pageable = Pageable.builder().page(page).orderBy(orderBy).build();
            params.add(new Object[]{pageable, "pLe"});
        }

        {
            final OrderBy orderBy = OrderBy.builder().field(OrderBy.Field.COMPANY).build();
            Page page = Page.builder().offset(5).limit(10).build();
            final Pageable pageable = Pageable.builder().page(page).orderBy(orderBy).build();
            params.add(new Object[]{pageable, "ApPl"});
        }
        //Sorted By COMPANY DESC
        {
            final OrderBy orderBy = OrderBy.builder().field(OrderBy.Field.COMPANY).meaning(OrderBy.Meaning.DESC).build();
            Page page = Page.builder().offset(0).limit(2).build();
            final Pageable pageable = Pageable.builder().page(page).orderBy(orderBy).build();
            params.add(new Object[]{pageable, "Apple"});
        }

        {
            final OrderBy orderBy = OrderBy.builder().field(OrderBy.Field.COMPANY).meaning(OrderBy.Meaning.DESC).build();
            Page page = Page.builder().offset(10).limit(4).build();
            final Pageable pageable = Pageable.builder().page(page).orderBy(orderBy).build();
            params.add(new Object[]{pageable, "Apple"});
        }

        {
            final OrderBy orderBy = OrderBy.builder().field(OrderBy.Field.COMPANY).meaning(OrderBy.Meaning.DESC).build();
            Page page = Page.builder().offset(0).limit(5).build();
            final Pageable pageable = Pageable.builder().page(page).orderBy(orderBy).build();
            params.add(new Object[]{pageable, "pLe"});
        }

        {
            final OrderBy orderBy = OrderBy.builder().field(OrderBy.Field.COMPANY).meaning(OrderBy.Meaning.DESC).build();
            Page page = Page.builder().offset(5).limit(10).build();
            final Pageable pageable = Pageable.builder().page(page).orderBy(orderBy).build();
            params.add(new Object[]{pageable, "ApPl"});
        }

        return params.toArray();
    }

    @Before
    public void loadEnttries() throws IOException, SQLException {
        UTDatabase.getInstance().reload();
    }

    @Test
    public void create() {
        final Company company = UTDatabase.getInstance().findCompanyById(5L);
        final ComputerBuilder builder = Computer.builder().introduced(LocalDate.of(2012, 4, 14))
                .discontinued(LocalDate.of(2020, 5, 10)).name("Le modifié").manufacturer(company);
        final Computer cree = builder.build();
        final long id = ComputerDAO.getInstance().create(cree);

        final Computer expected = builder.id(id).build();
        final Computer actual = ComputerDAO.getInstance().findById(id).get();
        assertEquals(expected, actual);
    }

    @Test(expected = ComputerDAOException.class)
    public void createWithForeinKeyConflict() {
        final Company company = Company.builder().id(404L).name("La company").build();
        final ComputerBuilder builder = Computer.builder().introduced(LocalDate.of(2012, 4, 14))
                .discontinued(LocalDate.of(2020, 5, 10)).name("Le modifié").manufacturer(company);
        final Computer cree = builder.build();
        ComputerDAO.getInstance().create(cree);
    }

    @Test
    public void deleteById() {
        final int id = 5;
        final Optional<Computer> le5avant = ComputerDAO.getInstance().findById(id);
        ComputerDAO.getInstance().deleteById(id);
        final Optional<Computer> le5apres = ComputerDAO.getInstance().findById(id);
        assertTrue(le5avant.isPresent());
        assertFalse(le5apres.isPresent());
    }

    @Test
    @Parameters(method = "providePageable")
    public void findAll(Pageable pageable) {
        final List<Computer> actual = ComputerDAO.getInstance().findAll(pageable);
        final List<Computer> expected = UTDatabase.getInstance().findAllComputers(pageable);
        assertEquals(expected, actual);
    }

    @Test
    @Parameters(method = "provideComputerId")
    public void findById(long id) {
        final Optional<Computer> expected = Optional.ofNullable(UTDatabase.getInstance().findComputerById(id));
        final Optional<Computer> actual = ComputerDAO.getInstance().findById(id);
        assertEquals(expected, actual);
    }

    @Test
    public void update() {
        final long id = 5;
        final Computer expected = Computer.builder().id(id).introduced(LocalDate.of(2012, 4, 14))
                .discontinued(LocalDate.of(2020, 5, 10)).name("Le modifié").build();
        ComputerDAO.getInstance().update(expected);
        final Computer actual = ComputerDAO.getInstance().findById(id).get();
        assertEquals(expected, actual);
    }

    @Test
    public void count() {
        final long count = ComputerDAO.getInstance().count();
        assertEquals(UTDatabase.getInstance().findAllComputers().size(), count);
    }

    @Test
    @Parameters(value = {"Apple | 11", "App | 11", "aPp | 11"})
    public void countSearch(String search, long expected) {
        final long actual = ComputerDAO.getInstance().countSearch(search);
        assertEquals(expected, actual);
    }

    @Test
    @Parameters(method = "providePageableAndSearch")
    public void search(Pageable pageable, String search) {
        final List<Computer> actual = ComputerDAO.getInstance().search(pageable, search);
        final List<Computer> expected = UTDatabase.getInstance().searchComputer(pageable, search);
        assertEquals(expected, actual);
    }
}
