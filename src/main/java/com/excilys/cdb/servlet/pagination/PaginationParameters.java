package com.excilys.cdb.servlet.pagination;

import java.util.Objects;

public class PaginationParameters {

    private static final int DEFAULT_PAGE_SIZE = 50;
    private static final String PARAMETER_NEXT = "next";
    private static final String PARAMETER_PAGE = "page";
    private static final String PARAMETER_PAGES = "pages";
    private static final String PARAMETER_PREVIOUS = "previous";
    private static final String PARAMETER_SIZE = "size";

    public static final PaginationParameters DEFAULT_PAGINATION_PARAMETERS = new PaginationParameters(DEFAULT_PAGE_SIZE,
            PARAMETER_SIZE, PARAMETER_PAGE, PARAMETER_PREVIOUS, PARAMETER_NEXT, PARAMETER_PAGES);

    private long defaultSize;
    private String next;
    private String page;
    private String pages;
    private String previous;
    private String size;

    public PaginationParameters(long defaultSize, String size, String page, String previous, String next,
                                String pages) {
        super();
        Objects.requireNonNull(size);
        Objects.requireNonNull(page);
        Objects.requireNonNull(previous);
        Objects.requireNonNull(next);
        Objects.requireNonNull(pages);

        this.defaultSize = defaultSize;
        this.size = size;
        this.page = page;
        this.previous = previous;
        this.next = next;
        this.pages = pages;
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
