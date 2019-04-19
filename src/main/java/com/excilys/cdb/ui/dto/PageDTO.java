package com.excilys.cdb.ui.dto;

import java.util.List;

public class PageDTO<T> {
    private List<T> content;
    private boolean hasNext;
    private boolean hasPrevious;

    public PageDTO(List<T> content, boolean hasPrevious, boolean hasNext) {
	super();
	this.content = content;
	this.hasPrevious = hasPrevious;
	this.hasNext = hasNext;
    }

    public List<T> getContent() {
	return content;
    }

    public boolean hasNext() {
	return hasNext;
    }

    public boolean hasPrevious() {
	return hasPrevious;
    }

}
