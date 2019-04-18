package com.ui.dto;

import java.util.List;

public class PageDTO<T> {
    private List<T> content;
    private boolean hasPrevious;
    private boolean hasNext;

    public PageDTO(List<T> content, boolean hasPrevious, boolean hasNext) {
	super();
	this.content = content;
	this.hasPrevious = hasPrevious;
	this.hasNext = hasNext;
    }

    public List<T> getContent() {
	return content;
    }

    public boolean hasPrevious() {
	return hasPrevious;
    }

    public boolean hasNext() {
	return hasNext;
    }

}
