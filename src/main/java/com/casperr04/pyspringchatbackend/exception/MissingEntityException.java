package com.casperr04.pyspringchatbackend.exception;

public class MissingEntityException extends RuntimeException{
    public MissingEntityException(String message) {
        super(message);
    }
}
