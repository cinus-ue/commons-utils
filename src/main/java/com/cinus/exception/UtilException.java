package com.cinus.exception;

public class UtilException extends RuntimeException {

    public UtilException(Throwable throwable) {
        super(throwable);
    }

    public UtilException(String message) {
        super(message);
    }

    public UtilException(String message, Throwable cause) {
        super(message, cause);
    }

}
