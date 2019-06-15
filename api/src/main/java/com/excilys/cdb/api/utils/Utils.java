package com.excilys.cdb.api.utils;

public final class Utils {
    private Utils() {
    }

    public static String createLink(String url, String rel) {
        return String.format("<%s>; rel=\"%s\"", url, rel);
    }
}
