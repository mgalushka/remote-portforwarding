package org.enterprisepower.net.portforward;

/**
 * @author Maxim Galushka
 * @since 11.01.2011
 */
public class ListenerException extends RuntimeException{
    private static final long serialVersionUID = 6827666496770206670L;

    public ListenerException() {
        super();
    }

    public ListenerException(String message) {
        super(message);
    }

    public ListenerException(String message, Throwable cause) {
        super(message, cause);
    }

    public ListenerException(Throwable cause) {
        super(cause);
    }
}
