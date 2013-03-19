package me.test.cas.server;

import org.jasig.cas.authentication.handler.DefaultPasswordEncoder;
import org.jasig.cas.authentication.handler.PasswordEncoder;

public class MyPasswordEncoder implements PasswordEncoder {
	private DefaultPasswordEncoder passwordEncoder = null;

	public MyPasswordEncoder() {
		this("MD5");
	}

	public MyPasswordEncoder(String encodingAlgorithm) {
		this.passwordEncoder = new DefaultPasswordEncoder(encodingAlgorithm);
	}

	public String encode(String password) {
		// TODO Auto-generated method stub
		return null;
	}
}
