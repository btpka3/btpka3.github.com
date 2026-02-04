package me.test.jdk.java.lang;

public class Autoboxing1 {

    public static void main(String[] args) {
        for (int i = 0; i < 3; i++) {

            Boolean b = i % 3 == 0 ? true : i % 3 == 1 ? false : null;
            // Boolean b = i % 3 == 0 ? Boolean.TRUE : i % 3 == 1 ? false :
            // null;
            System.out.println("i=" + i + " : b=" + b);
        }
    }
}
