package com.ebi.technicaltest.exception;

import org.springframework.util.StringUtils;

public class ExceptionHelper {
    private ExceptionHelper() {
    }

    public static String getMessage(final String title, final String detail) {
        StringBuilder builder = new StringBuilder(title);
        if (!StringUtils.isEmpty(detail)) {
            builder = builder.append(" - ").append(detail);
        }

        return builder.toString();
    }
}
