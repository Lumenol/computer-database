package com.excilys.cdb.servlet.pagination;

import java.util.Objects;

public class PaginationParameters {

    private final int minPageSize;
    private final int maxPageSize;
    private final int defaultSize;
    private final String next;
    private final String page;
    private final String pages;
    private final String previous;
    private final String size;
    private final String orderBy;
    private final String meaning;

    public PaginationParameters(int minPageSize, int maxPageSize, int defaultSize, String size, String page,
	    String previous, String next, String pages, String orderBy, String meaning) {
	super();
	Objects.requireNonNull(size);
	Objects.requireNonNull(page);
	Objects.requireNonNull(previous);
	Objects.requireNonNull(next);
	Objects.requireNonNull(pages);
	Objects.requireNonNull(orderBy);
	Objects.requireNonNull(meaning);

	this.minPageSize = minPageSize;
	this.maxPageSize = maxPageSize;
	this.defaultSize = defaultSize;
	this.size = size;
	this.page = page;
	this.previous = previous;
	this.next = next;
	this.pages = pages;
	this.orderBy = orderBy;
	this.meaning = meaning;
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
	return defaultSize == other.defaultSize && maxPageSize == other.maxPageSize
		&& Objects.equals(meaning, other.meaning) && minPageSize == other.minPageSize
		&& Objects.equals(next, other.next) && Objects.equals(orderBy, other.orderBy)
		&& Objects.equals(page, other.page) && Objects.equals(pages, other.pages)
		&& Objects.equals(previous, other.previous) && Objects.equals(size, other.size);
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

    public String getOrderBy() {
	return orderBy;
    }

    public String getMeaning() {
	return meaning;
    }

    @Override
    public int hashCode() {
	return Objects.hash(defaultSize, maxPageSize, meaning, minPageSize, next, orderBy, page, pages, previous, size);
    }

    @Override
    public String toString() {
	return "PaginationParameters [minPageSize=" + minPageSize + ", maxPageSize=" + maxPageSize + ", defaultSize="
		+ defaultSize + ", next=" + next + ", page=" + page + ", pages=" + pages + ", previous=" + previous
		+ ", size=" + size + ", orderBy=" + orderBy + ", meaning=" + meaning + "]";
    }

}
