package com.db.portforward;

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
        super(message);
    }

    public ApplicationException(String message, Throwable cause) {
        super(message, cause);
    }

    public ApplicationException(Throwable cause) {
        super(cause);
    }
}
