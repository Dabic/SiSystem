package com.se.source.shared;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionHandlerController {

    private final Logger logger = LoggerFactory.getLogger(ExceptionHandlerController.class);

    @ExceptionHandler(ErrorMessageException.class)
    public ResponseEntity handleException(ErrorMessageException e) {
        logger.error(e.getText());
        return ResponseEntity
                .status(e.getHttpStatus())
                .body(e.getText());
    }
}
