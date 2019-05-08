package com.excilys.cdb.persistence.page;

import java.util.Objects;

public class Page {
    private final long offset;
    private final long limit;

    Page(long offset, long limit) {
        this.offset = offset;
        this.limit = limit;
    }

    public static PageBuilder builder() {
        return new PageBuilder();
    }

    @Override
    public String toString() {
        return "Page{" +
                "offset=" + offset +
                ", limit=" + limit +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Page page = (Page) o;
        return offset == page.offset &&
                limit == page.limit;
    }

    @Override
    public int hashCode() {
        return Objects.hash(offset, limit);
    }

    public long getOffset() {
        return offset;
    }

    public long getLimit() {
        return limit;
    }

    public static class PageBuilder {
        private long offset;
        private long limit;

        PageBuilder() {
        }

        public PageBuilder offset(long offset) {
            this.offset = offset;
            return this;
        }

        public PageBuilder limit(long limit) {
            this.limit = limit;
            return this;
        }

        public Page build() {
            return new Page(offset, limit);
        }

        public String toString() {
            return "Page.PageBuilder(offset=" + this.offset + ", limit=" + this.limit + ")";
        }
    }
}
