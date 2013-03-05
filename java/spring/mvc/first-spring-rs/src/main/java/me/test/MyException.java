package me.test;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "myException")
@XmlAccessorType(XmlAccessType.NONE)
@XmlType(name = "myException")
public class MyException extends MyRuntimeException {
	private static final long serialVersionUID = 1L;
	@XmlAttribute
	private String errorCode;
	@XmlElement(required = true)
	private String message;

	public MyException() {
		super();
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
