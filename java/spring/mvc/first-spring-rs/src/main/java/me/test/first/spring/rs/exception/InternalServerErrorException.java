package me.test.first.spring.rs.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class InternalServerErrorException extends RuntimeException {
	private static final long serialVersionUID = 1L;
//	private String messageId;
//	private Object[] messageParams;

	public InternalServerErrorException() {
		super();
	}

	public InternalServerErrorException(String message, Throwable cause) {
		super(message, cause);
	}

	public InternalServerErrorException(String message) {
		super(message);
	}

	public InternalServerErrorException(Throwable cause) {
		super(cause);
	}
	//
	// public String getMessageId() {
	// return messageId;
	// }
	//
	// public void setMessageId(String messageId) {
	// this.messageId = messageId;
	// }
	//
	// public Object[] getMessageParams() {
	// return messageParams;
	// }
	//
	// public void setMessageParams(Object... messageParams) {
	// this.messageParams = messageParams;
	// }
}
