package com.excilys.cdb.servlet.pagination;

import java.util.Objects;

public class PaginationParameters {

    private static final int MIN_PAGE_SIZE = 10;
    private static final int MAX_PAGE_SIZE = 100;
    private static final int DEFAULT_PAGE_SIZE = 50;
    private static final String PARAMETER_NEXT = "next";
    private static final String PARAMETER_PAGE = "page";
    private static final String PARAMETER_PAGES = "pages";
    private static final String PARAMETER_PREVIOUS = "previous";
    private static final String PARAMETER_SIZE = "size";

    static final PaginationParameters DEFAULT_PAGINATION_PARAMETERS = new PaginationParameters(MIN_PAGE_SIZE, MAX_PAGE_SIZE, DEFAULT_PAGE_SIZE,
            PARAMETER_SIZE, PARAMETER_PAGE, PARAMETER_PREVIOUS, PARAMETER_NEXT, PARAMETER_PAGES);

    private final int minPageSize;
    private final int maxPageSize;
    private final int defaultSize;
    private final String next;
    private final String page;
    private final String pages;
    private final String previous;
    private final String size;

    public PaginationParameters(int minPageSize, int maxPageSize, int defaultSize, String size, String page, String previous, String next,
                                String pages) {
        super();
        Objects.requireNonNull(size);
        Objects.requireNonNull(page);
        Objects.requireNonNull(previous);
        Objects.requireNonNull(next);
        Objects.requireNonNull(pages);

        this.minPageSize = minPageSize;
        this.maxPageSize = maxPageSize;
        this.defaultSize = defaultSize;
        this.size = size;
        this.page = page;
        this.previous = previous;
        this.next = next;
        this.pages = pages;
    }

    public int getMinPageSize() {
        return minPageSize;
    }

    public int getMaxPageSize() {
        return maxPageSize;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        PaginationParameters other = (PaginationParameters) obj;
        return defaultSize == other.defaultSize && Objects.equals(next, other.next) && Objects.equals(page, other.page)
                && Objects.equals(pages, other.pages) && Objects.equals(previous, other.previous)
                && Objects.equals(size, other.size);
    }

    public long getDefaultSize() {
        return defaultSize;
    }

    public String getNext() {
        return next;
    }

    public String getPage() {
        return page;
    }

    public String getPages() {
        return pages;
    }

    public String getPrevious() {
        return previous;
    }

    public String getSize() {
        return size;
    }

    @Override
    public int hashCode() {
        return Objects.hash(defaultSize, next, page, pages, previous, size);
    }

    @Override
    public String toString() {
        return "PaginationParameters [defaultSize=" + defaultSize + ", next=" + next + ", page=" + page + ", pages="
                + pages + ", previous=" + previous + ", size=" + size + "]";
    }

}
