package me.test.jdk.java.security;


import org.bouncycastle.util.encoders.*;

import javax.crypto.*;
import java.security.*;
import java.security.interfaces.*;
import java.security.spec.*;

public class RsaTest01 {

    public static void main(String[] args)
            throws Exception {
        test01();
    }


    // 加密
    public static void test01() throws Exception {

        System.out.println("============================ test01");

        KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
        kpg.initialize(512);
        KeyPair keyPair = kpg.genKeyPair();
        System.out.println("keyPair.class: " + keyPair.getClass());

        RSAPublicKey pubKey = (RSAPublicKey) keyPair.getPublic();
        System.out.println("pub.Modulus  : " + pubKey.getModulus());
        System.out.println("pub.Exponent : " + pubKey.getPublicExponent());
        System.out.println("pub          : " + Base64.toBase64String(pubKey.getEncoded()));
        RSAPrivateKey priKey = (RSAPrivateKey) keyPair.getPrivate();
        System.out.println("pri.Exponent : " + priKey.getPrivateExponent());
        System.out.println("pri          : " + Base64.toBase64String(priKey.getEncoded()));


        String msg = "Hello World!";
        System.out.println("msg          : " + msg);

        Cipher encCipher = Cipher.getInstance("RSA");
        encCipher.init(Cipher.ENCRYPT_MODE, keyPair.getPublic(), (SecureRandom) null);
        String encMsg = Hex.toHexString(encCipher.doFinal(msg.getBytes()));
        System.out.println("encMsg       : " + encMsg);

        encCipher.init(Cipher.ENCRYPT_MODE, keyPair.getPublic(), (SecureRandom) null);
        encMsg = Hex.toHexString(encCipher.doFinal(msg.getBytes()));
        System.out.println("encMsg       : " + encMsg);


        Cipher decCipher = Cipher.getInstance("RSA");
        decCipher.init(Cipher.DECRYPT_MODE, keyPair.getPrivate());
        String decMsg = new String(decCipher.doFinal(Hex.decode(encMsg)));
        System.out.println("decMsg       : " + decMsg);
    }

    public static RSAPublicKey loadPublicKeyByStr(String publicKeyStr)
            throws Exception {

        byte[] buffer = Base64.decode(publicKeyStr);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(buffer);
        return (RSAPublicKey) keyFactory.generatePublic(keySpec);
    }

    public static RSAPrivateKey loadPrivateKeyByStr(String privateKeyStr)
            throws Exception {

        byte[] buffer = Base64.decode(privateKeyStr);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(buffer);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return (RSAPrivateKey) keyFactory.generatePrivate(keySpec);

    }
}
