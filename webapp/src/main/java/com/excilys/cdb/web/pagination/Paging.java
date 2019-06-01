package com.excilys.cdb.web.pagination;

import com.excilys.cdb.shared.pagination.Page;
import org.springframework.web.servlet.ModelAndView;

import java.util.*;

public class Paging {

    private final PagingParameters parameters;

    public Paging(PagingParameters parameters) {
        Objects.requireNonNull(parameters);
        this.parameters = parameters;
    }

    public PagingParameters getParameters() {
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
        long pageIndex = page.getIndex();
        long pageSize = page.getSize();
        long indexLastPage = indexLastPage(numberOfEntities, pageSize);
        final Page newPage = Page.builder().page(page.getIndex()).build();
        if (pageIndex < 1) {
            newPage.setIndex(1);
        } else if (pageIndex > 1 && pageIndex > indexLastPage) {
            final long max = Math.max(indexLastPage, 1);
            newPage.setIndex(max);
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
        setPreviousPage(modelAndView, page.getIndex());
        setNextPage(modelAndView, page.getIndex(), numberOfEntities, page.getSize());
        setPagesNumbers(modelAndView, page.getIndex(), numberOfEntities, page.getSize());
        setPageSize(modelAndView, page.getSize());
        setCurrentPageIndex(modelAndView, page.getIndex());
    }

    private void setPreviousPage(ModelAndView modelAndView, long pageCurrent) {
        if (pageCurrent > 1) {
            modelAndView.addObject(parameters.getPrevious(), pageCurrent - 1);
        }
    }
}
