package com.kage.util;

public final class SanitizerUtil {

    private SanitizerUtil() {}

    public static String clean(String value) {
        if (value == null) return null;
        return value.trim();
    }
}
