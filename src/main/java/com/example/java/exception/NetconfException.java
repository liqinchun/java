package com.example.java.exception;

import java.io.IOException;

/**
 * Represents class of errors related to NETCONF SB protocol.
 */
public class NetconfException extends IOException {
    /**
     * Constructs an exception with the specified message.
     *
     * @param message the message describing the specific nature of the error
     */
    public NetconfException(String message) {
        super(message);
    }

    /**
     * Constructs an exception with the specified message and the underlying cause.
     *
     * @param message the message describing the specific nature of the error
     * @param cause   the underlying cause of this error
     */
    public NetconfException(String message, Throwable cause) {
        super(message, cause);
    }
}