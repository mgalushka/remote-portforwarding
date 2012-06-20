package com.maximgalushka.portforward;

/**
 * @author Maxim Galushka
 * @since 30.12.2010
 */
public class ApplicationException extends Exception{
    private static final long serialVersionUID = -6629307626095557482L;

    public ApplicationException() {
        super();
    }

    public ApplicationException(String message) {
        super("Application exception: " + message);
    }

    public ApplicationException(Throwable cause) {
        super(cause);
    }

    public ApplicationException(String message, Throwable cause) {
        super(message, cause);
    }
}
