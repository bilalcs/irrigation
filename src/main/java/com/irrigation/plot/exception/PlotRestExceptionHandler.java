package com.irrigation.plot.exception;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class PlotRestExceptionHandler {

    @ExceptionHandler
    public String handleGeneralExceptions(Exception ex) {
        return ex.getMessage();
    }
}
