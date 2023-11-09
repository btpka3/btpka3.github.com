package me.test;

import org.springframework.beans.factory.annotation.Autowired;

public class Aaa {

    @Autowired
    String str;

    @Autowired
    private Bbb bbb;

    public String hi() {
        return "Aaa#hi : " + str + " : " + (bbb != null ? bbb.hello() + "@@@" : "---");
    }

    public Bbb getBbb() {
        return bbb;
    }

    public void setBbb(Bbb bbb) {
        this.bbb = bbb;
    }
}
