package com.se.source.shared;

import lombok.Data;
import org.springframework.http.HttpStatus;

public class ErrorMessageException extends Exception {
    private String text;
    private HttpStatus httpStatus;

    public ErrorMessageException(String text, HttpStatus httpStatus) {
        this.text = text;
        this.httpStatus = httpStatus;
    }

    public String getText() {
        return text;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
