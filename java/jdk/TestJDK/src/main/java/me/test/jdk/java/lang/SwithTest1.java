package me.test.jdk.java.lang;

public class SwithTest1 {

    public static void main(String[] args) {
        for (int i = 1; i < 5; i++) {
            switch (i) {
                case 1 :
                    System.out.println("111");
                    break;
                default :
                    System.out.println(i + "kk");
                case 2 :
                    System.out.println("222");
                    break;
            }
        }
    }

}
