package com.dwp.userlocator;

public class UserLocatorException extends RuntimeException {

    public UserLocatorException() {
        super();
    }

    public UserLocatorException(String message) {
        super(message);
    }
    
    public UserLocatorException(Throwable cause) {
        super(cause);
    }

    public UserLocatorException(String message, Throwable cause) {
        super(message, cause);
    }
}