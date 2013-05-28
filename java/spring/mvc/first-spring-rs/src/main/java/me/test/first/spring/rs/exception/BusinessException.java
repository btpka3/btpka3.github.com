package me.test.first.spring.rs.exception;


public class BusinessException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    private int httpStatus = 400;

    public BusinessException() {
        super();
    }

    public BusinessException(String message, Throwable cause) {
        super(message, cause);
    }

    public BusinessException(String message) {
        super(message);
    }

    public BusinessException(Throwable cause) {
        super(cause);
    }

    public BusinessException(int httpStatus) {
        super();
        this.httpStatus = httpStatus;
    }

    public BusinessException(int httpStatus, String message, Throwable cause) {
        super(message, cause);
        this.httpStatus = httpStatus;
    }

    public BusinessException(int httpStatus, String message) {
        super(message);
        this.httpStatus = httpStatus;
    }

    public BusinessException(int httpStatus, Throwable cause) {
        super(cause);
        this.httpStatus = httpStatus;
    }

    public int getHttpStatus() {
        return httpStatus;
    }
}
