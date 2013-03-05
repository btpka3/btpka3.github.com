package me.test.first.spring.rs.exception;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;

public class SystemException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	private HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
	private List<String> messages = new ArrayList<String>();

	public SystemException() {
		super();
	}

	public SystemException(String message, Throwable cause) {
		super(message, cause);
	}

	public SystemException(String message) {
		super(message);
	}

	public SystemException(Throwable cause) {
		super(cause);
	}

	public HttpStatus getHttpStatus() {
		return httpStatus;
	}

	public void setHttpStatus(HttpStatus httpStatus) {
		this.httpStatus = httpStatus;
	}

	public List<String> getMessages() {
		return messages;
	}

	public void setMessages(List<String> messages) {
		this.messages = messages;
	}
}
