package me.test;

import org.springframework.beans.factory.annotation.Autowired;

public class Bbb {

    @Autowired
    private Ccc ccc;

    public String hello() {
        return "Bbb#Hello : " + ccc;
    }

    public Ccc getCcc() {
        return ccc;
    }

    public void setCcc(Ccc ccc) {
        this.ccc = ccc;
    }
}
