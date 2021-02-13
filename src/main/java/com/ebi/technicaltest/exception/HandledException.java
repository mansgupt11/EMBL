package com.ebi.technicaltest.exception;

import org.springframework.http.HttpStatus;

public abstract class HandledException extends RuntimeException {
    private static final long serialVersionUID = -1063005707917268934L;
    protected final HttpStatus httpStatus;
    protected final String title;
    protected final String detail;

    protected HandledException(final HttpStatus httpStatus, final String title) {
        this((HttpStatus)httpStatus, (String)title, (String)null);
    }

    protected HandledException(final HttpStatus httpStatus, final String title, final String detail) {
        super(ExceptionHelper.getMessage(title, detail));
        this.httpStatus = httpStatus;
        this.title = title;
        this.detail = detail;
    }

    protected HandledException(final Throwable cause, final HttpStatus httpStatus, final String title) {
        this(cause, httpStatus, title, (String)null);
    }

    protected HandledException(final Throwable cause, final HttpStatus httpStatus, final String title, final String detail) {
        super(ExceptionHelper.getMessage(title, detail), cause);
        this.httpStatus = httpStatus;
        this.title = title;
        this.detail = detail;
    }

    public abstract String getProblemName();

    public final HttpStatus getHttpStatus() {
        return this.httpStatus;
    }

    public final String getTitle() {
        return this.title;
    }

    public final String getDetail() {
        return this.detail;
    }
}