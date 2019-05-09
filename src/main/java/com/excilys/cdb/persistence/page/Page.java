package com.excilys.cdb.persistence.page;

import java.util.Objects;

public class Page {
    private final long page;
    private final long limit;

    Page(long page, long limit) {
	this.page = page;
	this.limit = limit;
    }

    public static PageBuilder builder() {
	return new PageBuilder();
    }

    @Override
    public String toString() {
	return "Page{" + "page=" + page + ", offset=" + getOffset() + ", limit=" + limit + '}';
    }

    @Override
    public boolean equals(Object o) {
	if (this == o)
	    return true;
	if (o == null || getClass() != o.getClass())
	    return false;
	Page page = (Page) o;
	return this.page == page.page && limit == page.limit;
    }

    @Override
    public int hashCode() {
	return Objects.hash(page, limit);
    }

    public long getOffset() {
	return (page - 1) * limit;
    }

    public long getPage() {
	return page;
    }

    public long getLimit() {
	return limit;
    }

    public static class PageBuilder {
	private long page;
	private long limit;

	PageBuilder() {
	}

	public PageBuilder page(long page) {
	    this.page = page;
	    return this;
	}

	public PageBuilder limit(long limit) {
	    this.limit = limit;
	    return this;
	}

	public Page build() {
	    return new Page(page, limit);
	}

	@Override
	public String toString() {
	    return "Page.PageBuilder(offset=" + this.page + ", limit=" + this.limit + ")";
	}
    }
}
