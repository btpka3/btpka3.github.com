package me.test.csrf;

public class InvalidCsrfTokenException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public InvalidCsrfTokenException() {
        super();
    }

    public InvalidCsrfTokenException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidCsrfTokenException(String message) {
        super(message);
    }

    public InvalidCsrfTokenException(Throwable cause) {
        super(cause);
    }

}
