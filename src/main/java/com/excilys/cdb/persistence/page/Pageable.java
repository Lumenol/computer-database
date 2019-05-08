package com.excilys.cdb.persistence.page;

import java.util.Objects;

public class Pageable {
    private final Page page;
    private final OrderBy orderBy;

    Pageable(Page page, OrderBy orderBy) {
        Objects.requireNonNull(page);
        Objects.requireNonNull(orderBy);
        this.page = page;
        this.orderBy = orderBy;
    }

    public static PageableBuilder builder() {
        return new PageableBuilder();
    }

    @Override
    public String toString() {
        return "Pageable{" +
                "page=" + page +
                ", orderBy=" + orderBy +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pageable pageable = (Pageable) o;
        return Objects.equals(page, pageable.page) &&
                Objects.equals(orderBy, pageable.orderBy);
    }

    @Override
    public int hashCode() {
        return Objects.hash(page, orderBy);
    }

    public Page getPage() {
        return page;
    }

    public OrderBy getOrderBy() {
        return orderBy;
    }

    public static class PageableBuilder {
        private Page page;
        private OrderBy orderBy;

        PageableBuilder() {
        }

        public PageableBuilder page(Page page) {
            this.page = page;
            return this;
        }

        public PageableBuilder orderBy(OrderBy orderBy) {
            this.orderBy = orderBy;
            return this;
        }

        public Pageable build() {
            return new Pageable(page, orderBy);
        }

        public String toString() {
            return "Pageable.PageableBuilder(page=" + this.page + ", orderBy=" + this.orderBy + ")";
        }
    }
}
