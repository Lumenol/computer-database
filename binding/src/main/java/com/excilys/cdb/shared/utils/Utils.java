package com.excilys.cdb.shared.utils;

import com.excilys.cdb.shared.pagination.OrderBy;
import com.excilys.cdb.shared.pagination.Page;
import com.excilys.cdb.shared.pagination.Pageable;
import com.excilys.cdb.shared.paging.PagingParameters;
import com.excilys.cdb.shared.paging.SortingParameters;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Objects;

public final class Utils {
    private Utils() {
    }

    public static String createPagingUrl(String baseUrl, Page page, PagingParameters pagingParameters) throws UnsupportedEncodingException {
        return createPagingUrl(baseUrl, page, null, null, null, pagingParameters, null);
    }

    public static String createPagingUrl(String baseUrl, Pageable pageable, String search, String searchParameter, PagingParameters pagingParameters, SortingParameters sortingParameters) throws UnsupportedEncodingException {
        return createPagingUrl(baseUrl, pageable.getPage(), pageable.getOrderBy(), search, searchParameter, pagingParameters, sortingParameters);
    }

    private static String createPagingUrl(String baseUrl, Page page, OrderBy order, String search, String searchParameter, PagingParameters pagingParameters, SortingParameters sortingParameters) throws UnsupportedEncodingException {
        final StringBuilder stringBuilder = new StringBuilder(baseUrl).append("?");

        stringBuilder.append(pagingParameters.getPage()).append("=").append(page.getPage());
        stringBuilder.append("&");
        stringBuilder.append(pagingParameters.getSize()).append("=").append(page.getSize());

        if (Objects.nonNull(search)) {
            stringBuilder.append("&");
            stringBuilder.append(encode(searchParameter)).append("=").append(encode(search));
        }

        if (Objects.nonNull(order)) {
            if (Objects.nonNull(order.getField())) {
                stringBuilder.append("&");
                stringBuilder.append(encode(sortingParameters.getOrderBy())).append("=")
                        .append(encode(order.getField().getIdentifier()));
            }

            if (Objects.nonNull(order.getDirection())) {
                stringBuilder.append("&");
                stringBuilder.append(encode(sortingParameters.getDirection())).append("=")
                        .append(encode(order.getDirection().getIdentifier()));
            }
        }

        return stringBuilder.toString();
    }

    private static String encode(String s) throws UnsupportedEncodingException {
        return URLEncoder.encode(s, "UTF-8");
    }

    public static long indexLastPage(long numberOfEntities, long pageSize) {
        return (long) Math.ceil(numberOfEntities / pageSize) + 1;
    }

}
