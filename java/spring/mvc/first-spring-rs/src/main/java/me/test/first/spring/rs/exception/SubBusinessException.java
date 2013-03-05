package me.test.first.spring.rs.exception;


public class SubBusinessException extends BusinessException {
	private static final long serialVersionUID = 1L;

	public SubBusinessException() {
		super();
	}

	public SubBusinessException(String message, Throwable cause) {
		super(message, cause);
	}

	public SubBusinessException(String message) {
		super(message);
	}

	public SubBusinessException(Throwable cause) {
		super(cause);
	}

}
