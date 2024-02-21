package com.khit.library.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class FinalException extends RuntimeException{
    private static final long serialVersionUID = -7347272714527192706L;

    public FinalException(String message){
        super(message);
    }
}
