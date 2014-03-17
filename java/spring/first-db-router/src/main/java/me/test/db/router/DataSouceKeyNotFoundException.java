
package me.test.db.router;

public class DataSouceKeyNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public DataSouceKeyNotFoundException() {

        super();
    }

    public DataSouceKeyNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {

        super(message, cause, enableSuppression, writableStackTrace);
    }

    public DataSouceKeyNotFoundException(String message, Throwable cause) {

        super(message, cause);
    }

    public DataSouceKeyNotFoundException(String message) {

        super(message);
    }

    public DataSouceKeyNotFoundException(Throwable cause) {

        super(cause);
    }

}
