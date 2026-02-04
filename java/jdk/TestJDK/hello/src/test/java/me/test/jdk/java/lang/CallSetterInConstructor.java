package me.test.jdk.java.lang;

public class CallSetterInConstructor {

    public static void main(String[] args) {
        A a = new B();
        System.out.println("------------------------------");
        System.out.println(a.toString());
        a.setName("B222");
        System.out.println("------------------------------");
        System.out.println(a.toString());
    }
}

class A {

    private String name = "AAA";

    public A() {
        setName("B11");
    }

    public String toString() {
        return "[A] {name: \"" + this.name + "\"}";
    }

    public void setName(String name) {
        this.name = name;
    }
}

class B extends A {

    private String name = "BBB";

    // public B() {
    // super();
    // }

    public String toString() {
        return super.toString() + "\n[B] {name: \"" + this.name + "\"}";
    }

    public void setName(String name) {
        this.name = name;
    }
}
