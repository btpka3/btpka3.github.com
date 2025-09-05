package me.test.first.spring.boot.test.mockito.parent;


public abstract class Parent {
    protected String findRealName(String name) {
        return name + ".real";
    }
}