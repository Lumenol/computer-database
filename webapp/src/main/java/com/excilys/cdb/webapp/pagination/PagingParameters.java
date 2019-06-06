package com.excilys.cdb.webapp.pagination;

import java.util.Objects;

public class PagingParameters {

    private final String next;
    private final String page;
    private final String pages;
    private final String previous;
    private final String size;

    public PagingParameters(String size, String page, String previous, String next, String pages) {
        super();
        Objects.requireNonNull(size);
        Objects.requireNonNull(page);
        Objects.requireNonNull(previous);
        Objects.requireNonNull(next);
        Objects.requireNonNull(pages);

        this.size = size;
        this.page = page;
        this.previous = previous;
        this.next = next;
        this.pages = pages;
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
    public String toString() {
        return "PagingParameters{" +
                "next='" + next + '\'' +
                ", page='" + page + '\'' +
                ", pages='" + pages + '\'' +
                ", previous='" + previous + '\'' +
                ", size='" + size + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PagingParameters that = (PagingParameters) o;
        return Objects.equals(next, that.next) &&
                Objects.equals(page, that.page) &&
                Objects.equals(pages, that.pages) &&
                Objects.equals(previous, that.previous) &&
                Objects.equals(size, that.size);
    }

    @Override
    public int hashCode() {
        return Objects.hash(next, page, pages, previous, size);
    }
}
