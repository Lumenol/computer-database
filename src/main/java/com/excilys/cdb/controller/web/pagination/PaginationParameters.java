package com.excilys.cdb.controller.web.pagination;

import java.util.Objects;

public class PaginationParameters {

    private final String next;
    private final String page;
    private final String pages;
    private final String previous;
    private final String size;
    private final String orderBy;
    private final String meaning;

    public PaginationParameters(String size, String page, String previous, String next, String pages, String orderBy,
	    String meaning) {
	super();
	Objects.requireNonNull(size);
	Objects.requireNonNull(page);
	Objects.requireNonNull(previous);
	Objects.requireNonNull(next);
	Objects.requireNonNull(pages);
	Objects.requireNonNull(orderBy);
	Objects.requireNonNull(meaning);

	this.size = size;
	this.page = page;
	this.previous = previous;
	this.next = next;
	this.pages = pages;
	this.orderBy = orderBy;
	this.meaning = meaning;
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

}
