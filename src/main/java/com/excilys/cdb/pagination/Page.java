package com.excilys.cdb.pagination;

import java.util.Objects;

public class Page {
    private long page;
    private long size;

    public Page() {
        this(1, 50);
    }

    Page(long page, long size) {
        this.page = page;
        this.size = size;
    }

    public static PageBuilder builder() {
        return new PageBuilder();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Page page1 = (Page) o;
        return page == page1.page &&
                size == page1.size;
    }

    @Override
    public int hashCode() {
        return Objects.hash(page, size);
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public long getOffset() {
        return (page - 1) * size;
    }

    public long getPage() {
        return page;
    }

    public void setPage(long page) {
        this.page = page;
    }

    @Override
    public String toString() {
        return "Page{" + "page=" + page + ", offset=" + getOffset() + ", size=" + size + '}';
    }

    public static class PageBuilder {
        private long page;
        private long size;

        PageBuilder() {
        }

        public Page build() {
            return new Page(page, size);
        }

        public PageBuilder size(long size) {
            this.size = size;
            return this;
        }

        public PageBuilder page(long page) {
            this.page = page;
            return this;
        }

        @Override
        public String toString() {
            return "Page.PageBuilder(offset=" + this.page + ", size=" + this.size + ")";
        }
    }
}
