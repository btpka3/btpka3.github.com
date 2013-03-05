package me.test.first.spring.rs.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public NotFoundException() {
		super();
	}

	public NotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public NotFoundException(String message) {
		super(message);
	}

	public NotFoundException(Throwable cause) {
		super(cause);
	}

	// private String messageId;
	// private Object[] messageParams;
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
