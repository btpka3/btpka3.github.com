package me.test.first.chanpay.api;

/**
 *
 */
public class CpApiException extends RuntimeException {

    public CpApiException() {
    }

    public CpApiException(String message) {
        super(message);
    }

    public CpApiException(String message, Throwable cause) {
        super(message, cause);
    }

    public CpApiException(Throwable cause) {
        super(cause);
    }

    public CpApiException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
