package com.github.btpka3.first.felix.my.module.b.impl;

public class B1 {

    public Aaa aaa;

    public void startUp() {
        System.out.println("B1 started.");
    }

    public Aaa getAaa() {
        return aaa;
    }

    public void setAaa(Aaa aaa) {
        this.aaa = aaa;
    }
}
