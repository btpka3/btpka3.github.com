package me.test.jdk.java.lang;

public class Sleep {
    public static void main(String[]args){
        try {
            Thread.sleep(60*60*1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
