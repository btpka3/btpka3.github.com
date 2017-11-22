package io.github.btpka3.asm;

public class Hi {

    private static String name = "zhang" + new String("3");

    public static void main(String[] args) {
        String newName = name + "4";
        System.out.println("Hello " + newName + "  " + Math.max(101, 202));
    }
}
