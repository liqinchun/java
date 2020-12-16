package com.example.java.exception;

public class FailedException extends RuntimeException {
    private static final long serialVersionUID = 266049754055616595L;

    /**
     * Constructs a new runtime exception with no error message.
     */
    public FailedException() {
        super();
    }

    /**
     * Constructs a new runtime exception with the given error message.
     *
     * @param message error message
     */
    public FailedException(String message) {
        super(message);
    }

    /**
     * Constructs a new runtime exception with the given error message.
     *
     * @param message error message
     * @param cause cause
     */
    public FailedException(String message, Throwable cause) {
        super(message, cause);
    }

}
