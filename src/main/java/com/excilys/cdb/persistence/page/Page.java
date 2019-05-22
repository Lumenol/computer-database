package com.excilys.cdb.persistence.page;

import java.util.Objects;

public class Page {
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

    public static PageBuilder builder() {
	return new PageBuilder();
    }

    private long page;

    private long size;

    public Page() {
	this(1, 50);
    }

    Page(long page, long size) {
	this.page = page;
	this.size = size;
    }

    @Override
    public boolean equals(Object o) {
	if (this == o)
	    return true;
	if (o == null || getClass() != o.getClass())
	    return false;
	Page page = (Page) o;
	return this.page == page.page && size == page.size;
    }

    public long getSize() {
	return size;
    }

    public long getOffset() {
	return (page - 1) * size;
    }

    public long getPage() {
	return page;
    }

    @Override
    public int hashCode() {
	return Objects.hash(page, size);
    }

    public void setSize(long size) {
	this.size = size;
    }

    public void setPage(long page) {
	this.page = page;
    }

    @Override
    public String toString() {
	return "Page{" + "page=" + page + ", offset=" + getOffset() + ", size=" + size + '}';
    }
}
