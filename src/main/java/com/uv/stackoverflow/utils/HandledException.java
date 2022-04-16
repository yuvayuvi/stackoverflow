package com.uv.stackoverflow.utils;

public class HandledException extends Exception {

    public HandledException(String message) {
        super(message);
    }

    public HandledException( String message, Throwable cause) {
        super(message, cause);

    }
}