package com.kage.util;

public final class DomainGuardsUtil {
    private  DomainGuardsUtil() {}

    public static <T> T requireNonNull(T value, String message) {
        if (value == null) {
            throw new IllegalArgumentException(message);
        }
        return value;
    }

    public static String requireNonEmpty(String value, String message) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException(message);
        }
        return value;
    }

    public static String normalize(String value) {
        return value == null ? null : value.trim();
    }
}
