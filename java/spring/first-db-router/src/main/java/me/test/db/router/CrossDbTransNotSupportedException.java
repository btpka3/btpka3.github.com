
package me.test.db.router;

public class CrossDbTransNotSupportedException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public CrossDbTransNotSupportedException() {

        super();
    }

    public CrossDbTransNotSupportedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {

        super(message, cause, enableSuppression, writableStackTrace);
    }

    public CrossDbTransNotSupportedException(String message, Throwable cause) {

        super(message, cause);
    }

    public CrossDbTransNotSupportedException(String message) {

        super(message);
    }

    public CrossDbTransNotSupportedException(Throwable cause) {

        super(cause);
    }

}
