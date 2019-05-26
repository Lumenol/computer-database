package com.excilys.cdb.shared.pagination;

import java.util.Objects;

public class Pageable {
    private Page page;
    private OrderBy orderBy;

    public Pageable() {
        this(new Page(), new OrderBy());
    }

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
        return "Pageable{" + "page=" + page + ", orderBy=" + orderBy + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Pageable pageable = (Pageable) o;
        return Objects.equals(page, pageable.page) && Objects.equals(orderBy, pageable.orderBy);
    }

    @Override
    public int hashCode() {
        return Objects.hash(page, orderBy);
    }

    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }

    public OrderBy getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(OrderBy orderBy) {
        this.orderBy = orderBy;
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

        @Override
        public String toString() {
            return "Pageable.PageableBuilder(page=" + this.page + ", orderBy=" + this.orderBy + ")";
        }
    }
}
