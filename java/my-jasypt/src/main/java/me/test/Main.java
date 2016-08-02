package me.test;

import org.jasypt.util.password.StrongPasswordEncryptor;

public class Main {

    public static void main(String[] args) {

        String pwd = "123456";
        StrongPasswordEncryptor passwordEncryptor = new StrongPasswordEncryptor();
        String encPwd1 = passwordEncryptor.encryptPassword(pwd);
        String encPwd2 = passwordEncryptor.encryptPassword(pwd);
        System.out.println("pwd = " + pwd);
        System.out.println("encPwd1 = " + encPwd1 + " : " + passwordEncryptor.checkPassword(pwd, encPwd1));
        System.out.println("encPwd2 = " + encPwd2 + " : " + passwordEncryptor.checkPassword(pwd, encPwd1));
    }

}
