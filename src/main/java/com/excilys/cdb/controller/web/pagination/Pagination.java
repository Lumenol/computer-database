package com.excilys.cdb.controller.web.pagination;

import com.excilys.cdb.persistence.page.Page;
import org.springframework.web.servlet.ModelAndView;

import java.util.*;

public class Pagination {

    private final PaginationParameters parameters;

    public Pagination(PaginationParameters parameters) {
        Objects.requireNonNull(parameters);
        this.parameters = parameters;
    }

    public PaginationParameters getParameters() {
        return parameters;
    }

    private long indexLastPage(double numberOfEntities, long pageSize) {
        return (long) Math.ceil(numberOfEntities / pageSize);
    }

    private List<Long> indexOfPages(long pageCurrent, long pageSize, long numberOfEntities) {
        final Set<Long> pages = new TreeSet<>();
        int shift = 0;
        boolean more = true;
        while (pages.size() < 5 && more) {
            more = false;

            final long before = pageCurrent - shift;
            if (before >= 1) {
                pages.add(before);
                more = true;
            }

            final long next = pageCurrent + shift;
            if ((next - 1) * pageSize < numberOfEntities) {
                pages.add(next);
                more = true;
            }

            shift++;
        }
        return new ArrayList<>(pages);
    }

    private void setCurrentPageIndex(ModelAndView modelAndView, long pageCurrent) {
        modelAndView.addObject(parameters.getPage(), pageCurrent);
    }

    private void setNextPage(ModelAndView modelAndView, long pageCurrent, long numberOfEntities, long pageSize) {
        if (pageCurrent * pageSize < numberOfEntities) {
            modelAndView.addObject(parameters.getNext(), pageCurrent + 1);
        }
    }

    public Optional<Page> redirectIfPageOutOfRange(Page page, double numberOfEntities) {
        long pageIndex = page.getPage();
        long pageSize = page.getSize();
        long indexLastPage = indexLastPage(numberOfEntities, pageSize);
        final Page newPage = Page.builder().page(page.getPage()).build();
        if (pageIndex < 1) {
            newPage.setPage(1);
        } else if (pageIndex > 1) {
            if (pageIndex > indexLastPage) {
                final long max = Math.max(indexLastPage, 1);
                newPage.setPage(max);
            }
        }
        if (page.getSize() > 0) {
            newPage.setSize(page.getSize());
        }
        return Optional.of(newPage).filter(p -> !p.equals(page));
    }

    private void setPageSize(ModelAndView modelAndView, long pageSize) {
        modelAndView.addObject(parameters.getSize(), pageSize);
    }

    private void setPagesNumbers(ModelAndView modelAndView, long pageCurrent, long numberOfEntries, long pageSize) {
        final List<Long> pages = indexOfPages(pageCurrent, pageSize, numberOfEntries);
        modelAndView.addObject(parameters.getPages(), pages);
    }

    public void setPageParameters(ModelAndView modelAndView, Page page, long numberOfEntities) {
        setPreviousPage(modelAndView, page.getPage());
        setNextPage(modelAndView, page.getPage(), numberOfEntities, page.getSize());
        setPagesNumbers(modelAndView, page.getPage(), numberOfEntities, page.getSize());
        setPageSize(modelAndView, page.getSize());
        setCurrentPageIndex(modelAndView, page.getPage());
    }

    private void setPreviousPage(ModelAndView modelAndView, long pageCurrent) {
        if (pageCurrent > 1) {
            modelAndView.addObject(parameters.getPrevious(), pageCurrent - 1);
        }
    }
}
