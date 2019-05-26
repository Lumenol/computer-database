package com.excilys.cdb.web.config;

import com.excilys.cdb.service.config.ServiceConfig;
import com.excilys.cdb.shared.config.SharedConfig;
import com.excilys.cdb.web.pagination.Pagination;
import com.excilys.cdb.web.pagination.PaginationParameters;
import com.excilys.cdb.web.sorting.Sorting;
import com.excilys.cdb.web.sorting.SortingParameters;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@ComponentScan(basePackages = {"com.excilys.cdb.web.controller"}, excludeFilters = @ComponentScan.Filter(Configuration.class))
@Import({SharedConfig.class, ServiceConfig.class})
public class WebConfig {

    @Bean
    public PaginationParameters paginationParameters() {
        final String PARAMETER_NEXT = "next";
        final String PARAMETER_PAGE = "page";
        final String PARAMETER_PAGES = "pages";
        final String PARAMETER_PREVIOUS = "previous";
        final String PARAMETER_SIZE = "size";
        final String PARAMETER_ORDER_BY = "order-by";
        final String PARAMETER_DIRECTION = "direction";

        return new PaginationParameters(PARAMETER_SIZE, PARAMETER_PAGE, PARAMETER_PREVIOUS, PARAMETER_NEXT,
                PARAMETER_PAGES, PARAMETER_ORDER_BY, PARAMETER_DIRECTION);
    }

    @Bean
    public Pagination pagination(PaginationParameters parameters) {
        return new Pagination(parameters);
    }

    @Bean
    public SortingParameters sortingParameters() {
        final String PARAMETER_ORDER_BY = "order-by";
        final String PARAMETER_DIRECTION = "direction";
        final String PARAMETER_ORDER_BY_UTILS = "order-utils";

        return new SortingParameters(PARAMETER_ORDER_BY, PARAMETER_DIRECTION, PARAMETER_ORDER_BY_UTILS);
    }

    @Bean
    public Sorting sorting(SortingParameters parameters) {
        return new Sorting(parameters);
    }

}
