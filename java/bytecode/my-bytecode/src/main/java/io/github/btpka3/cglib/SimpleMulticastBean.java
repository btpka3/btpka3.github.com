package io.github.btpka3.cglib;

public class SimpleMulticastBean implements DelegatationProvider {
    private String value;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}