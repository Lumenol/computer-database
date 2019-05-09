package com.excilys.cdb.servlet.pagination;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;

import javax.servlet.http.HttpServletRequest;

import com.excilys.cdb.persistence.page.Page;

public class Pagination {

    public static final Pagination DEFAULT_PAGINATION = new Pagination(
	    PaginationParameters.DEFAULT_PAGINATION_PARAMETERS);

    private final PaginationParameters parameters;

    public Pagination(PaginationParameters parameters) {
	Objects.requireNonNull(parameters);
	this.parameters = parameters;
    }

    private long getPageIndex(HttpServletRequest request) {
	final Long pageIndex = getParameterAsLong(request, parameters.getPage());
	return Objects.nonNull(pageIndex) ? pageIndex : 1;
    }

    private long getPageSize(HttpServletRequest request) {
	final Long pageSize = getParameterAsLong(request, parameters.getSize());
	if (Objects.nonNull(pageSize)) {
	    return Math.max(parameters.getMinPageSize(), Math.min(parameters.getMaxPageSize(), pageSize));
	} else {
	    return parameters.getDefaultSize();
	}
    }

    private Long getParameterAsLong(HttpServletRequest request, String parameterName) {
	final String parameter = request.getParameter(parameterName);
	try {
	    return Long.parseLong(parameter);
	} catch (NumberFormatException e) {
	    return null;
	}
    }

    public PaginationParameters getParameters() {
	return parameters;
    }

    public long indexLastPage(double numberOfEntities, long pageSize) {
	return (long) Math.ceil(numberOfEntities / pageSize);
    }

    private List<Long> indexOfPages(long pageCurrent, long pageSize, long numberOfEntities) {
	final Set<Long> pages = new TreeSet<>();
	int shift = 0;
	boolean more = true;
	while (pages.size() < 5 && more) {
	    more = false;

	    final long before = pageCurrent - shift;
	    if (before >= 1) {
		pages.add(before);
		more = true;
	    }

	    final long next = pageCurrent + shift;
	    if ((next - 1) * pageSize < numberOfEntities) {
		pages.add(next);
		more = true;
	    }

	    shift++;
	}
	return new ArrayList<>(pages);
    }

    private void setCurrentPageIndex(HttpServletRequest request, long pageCurrent) {
	request.setAttribute(parameters.getPage(), pageCurrent);
    }

    private void setNextPage(HttpServletRequest request, long pageCurrent, long numberOfEntities, long pageSize) {
	if (pageCurrent * pageSize < numberOfEntities) {
	    request.setAttribute(parameters.getNext(), pageCurrent + 1);
	}
    }

    public Page getPage(HttpServletRequest request) {
	return Page.builder().page(getPageIndex(request)).limit(getPageSize(request)).build();
    }

    /**
     * si la valeur est positive elle donne la page qui doit etre affichée
     *
     * @param request
     * @param numberOfEntities
     * @return
     * @throws IOException
     */
    public long redirectIfPageOutOfRange(HttpServletRequest request, double numberOfEntities) throws IOException {
	long pageIndex = getPageIndex(request);
	long pageSize = getPageSize(request);
	long indexLastPage = indexLastPage(numberOfEntities, pageSize);
	if (pageIndex < 1) {
	    return 1;
	}
	if (pageIndex > 1) {
	    if (pageIndex > indexLastPage) {
		return Math.max(indexLastPage, 1);
	    }
	}
	return -1;
    }

    private void setPageSize(HttpServletRequest request, long pageSize) {
	request.setAttribute(parameters.getSize(), pageSize);
    }

    private void setPagesNumbers(HttpServletRequest request, long pageCurrent, long numberOfEntries, long pageSize) {
	final List<Long> pages = indexOfPages(pageCurrent, pageSize, numberOfEntries);
	request.setAttribute(parameters.getPages(), pages);
    }

    public void setPageParameters(HttpServletRequest request, Page page, long numberOfEntities) {
	setPreviousPage(request, page.getPage());
	setNextPage(request, page.getPage(), numberOfEntities, page.getLimit());
	setPagesNumbers(request, page.getPage(), numberOfEntities, page.getLimit());
	setPageSize(request, page.getLimit());
	setCurrentPageIndex(request, page.getPage());
    }

    private void setPreviousPage(HttpServletRequest request, long pageCurrent) {
	if (pageCurrent > 1) {
	    request.setAttribute(parameters.getPrevious(), pageCurrent - 1);
	}
    }
}
