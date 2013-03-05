package me.test;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class StackTraceElementAdapter extends
		XmlAdapter<String, StackTraceElement> {
	public StackTraceElement unmarshal(String val) throws Exception {
		return null;
	}

	@Override
	public String marshal(StackTraceElement v) throws Exception {
		return null;
	}
}
