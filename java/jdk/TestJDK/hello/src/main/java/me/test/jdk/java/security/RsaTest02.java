package me.test.jdk.java.security;


import org.bouncycastle.jce.provider.*;
import org.bouncycastle.util.encoders.Base64;
import org.bouncycastle.util.encoders.*;
import sun.security.rsa.*;

import javax.crypto.*;
import java.math.*;
import java.security.*;
import java.security.interfaces.*;
import java.security.spec.*;
import java.util.*;

/*
应孟的请求帮助尝试模拟登录时，使用JS进行RSA加密的处理。

# 登录网站为：
http://introcs.cs.princeton.edu/java/99crypto/RSA.java.html

问题：
java 中的RSA的各种加密处理均无法与js中的加密结果保持一致。
参考了以下答案，https://stackoverflow.com/a/28938923/533317
说是 RSA.js 中的 padding 使用了非标准的padding方式（只padding 0)，
而 Java 中不支持。

因此，尝试使用在 JVM 中直接运行 JavaScript来解决，请参考
me.test.jdk.javax.script.TestJs
*/
public class RsaTest02 {

    public static void main(String[] args)
            throws Exception {
//        test01();

        Security.addProvider(new BouncyCastleProvider());
        test02();
    }

    private static List<String> names(String alg, String[] modes, String[] paddings) {
        List<String> list = new ArrayList<>(1 * modes.length * paddings.length);

        for (String mode : modes) {
            for (String padding : paddings) {
                list.add(alg + "/" + mode + "/" + padding);
            }
        }

        return list;
    }

    // 给定  pub.Modulus，pub.Exponent，pri.Exponent
    public static void test02() throws Exception {


        System.out.println("============================ test02");

        String pubModulus = "00ba3d972efea794358c211077993f867f0bb61cac53a1e5ab37518459da74c8f93ef648bcd42ebad48de5ad7ede2df77f65111c1f29277a27f17a6494b5e271e5e5c35e4c8c347d34df86a32579483fc26bdebe149c47cc3901ecbe0abc395fc2918b7f5ef8876c647f516bc7568e352f0217224a358bdbd489106e9258597577";
        String pubExponent = "010001";

        RSAPublicKey pubKey = new RSAPublicKeyImpl(
                new BigInteger(Hex.decode(pubModulus)),
                new BigInteger(Hex.decode(pubExponent)));

        String msg = "933125";
        System.out.println("msg: " + msg);

        String expectedEncMsg = "621dbdeb41dc4d2bce32133b63e5bf8313476066e24d239e71483be454fd5ccf038caa844a9a3eaf7fb012fc36896e519d463aef966492c5cf3fb6127524d24551f0634857547182defa08c84067f6c58e686422f9ba3aac7e1bbfecdfde0c335e0fbed226303d2bf3e6c5e6cbbde6481d49dcc7a50272f23ab5b2c74d40a225";
        System.out.println("expectedEncMsg: " + expectedEncMsg);


        String algorithm = "RSA";
        String[] modes = {
                "NONE",
                "CBC",
                "CFB",
                "CFBx",
                "CTR",
                "CTS",
                "ECB",
                "OFB",
                "OFBx",
                "PCBC"
        };

        String[] paddings = {
                "NoPadding",
                "ISO10126Padding",
                "OAEPPadding",
                //
                "PKCS1Padding",
                "PKCS5Padding",
                "SSL3Padding"
        };


        List<String> algNames = names(algorithm, modes, paddings);

        boolean found = false;
        String matchedAlg = null;
        for (String alg : algNames) {

            System.out.println("alg : " + alg);
            try {
                Cipher encCipher1 = Cipher.getInstance(alg);
                encCipher1.init(Cipher.ENCRYPT_MODE, pubKey);
                String actualEncMsg1 = Hex.toHexString(encCipher1.doFinal(msg.getBytes()));
                System.out.println("actualEncMsg  : " + actualEncMsg1);

                Cipher encCipher2 = Cipher.getInstance(alg);
                encCipher2.init(Cipher.ENCRYPT_MODE, pubKey);
                String actualEncMsg2 = Hex.toHexString(encCipher2.doFinal(msg.getBytes()));
                System.out.println("actualEncMsg  : " + actualEncMsg2);

                if (actualEncMsg1.equals(actualEncMsg2)) {
                    System.out.println("equals");
                }

                if (expectedEncMsg.equals(actualEncMsg1)) {
                    found = true;
                    matchedAlg = alg;
                    System.out.println("matches");
                    break;
                }
            } catch (Exception e) {
            }
        }
        if (found) {
            System.out.println("Found" + matchedAlg);
        } else {
            System.out.println("Not Found");
        }
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
