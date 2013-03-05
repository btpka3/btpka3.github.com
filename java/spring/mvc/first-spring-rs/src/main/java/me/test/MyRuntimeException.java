package me.test;

import javax.xml.bind.annotation.XmlTransient;

@XmlTransient
public class MyRuntimeException extends RuntimeException {
	public MyRuntimeException() {
		super();
	}

	@XmlTransient
	@Override
	public String getMessage() {
		return super.getMessage();
	}

	@XmlTransient
	@Override
	public String getLocalizedMessage() {
		return super.getLocalizedMessage();
	}

	@XmlTransient
	@Override
	public Throwable getCause() {
		return super.getCause();
	}

	@XmlTransient
	@Override
	public StackTraceElement[] getStackTrace() {
		return super.getStackTrace();
	}

	@Override
	public void setStackTrace(StackTraceElement[] stackTrace) {
		super.setStackTrace(stackTrace);
	}

	@XmlTransient
	private static final long serialVersionUID = 1L;
}
