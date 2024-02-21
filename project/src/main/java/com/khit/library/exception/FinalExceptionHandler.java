package com.khit.library.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

@RestController
@ControllerAdvice
public class FinalExceptionHandler {

    @ExceptionHandler(value = FinalException.class)
    public String globalExceptionHandler(FinalException e){
        return "<h2>" + e.getMessage() + "</h2>";
    }
}
