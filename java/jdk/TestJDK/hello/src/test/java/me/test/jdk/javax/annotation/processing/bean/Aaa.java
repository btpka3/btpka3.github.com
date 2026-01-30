package me.test.jdk.javax.annotation.processing.bean;

import me.test.jdk.javax.annotation.processing.MyAnnotation;

/**
 *
 */
@MyAnnotation
public class Aaa {

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Aaa{" +
                "name='" + name + '\'' +
                '}';
    }

    public static void main(String[] args) {
        System.out.println("hello ~~~~~");
    }
}
