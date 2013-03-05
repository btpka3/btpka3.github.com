package me.test.first.spring.rs.exception;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;

public class BusinessException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	private HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
	private List<String> messages = new ArrayList<String>();

	public BusinessException() {
		super();
	}

	public BusinessException(String message, Throwable cause) {
		super(message, cause);
		messages.add(message);
	}

	public BusinessException(String message) {
		super(message);
		messages.add(message);
	}

	public BusinessException(Throwable cause) {
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
