package me.test.jdk.java.security;


import java.io.*;
import java.nio.charset.*;
import java.security.*;
import java.security.cert.*;
import java.security.spec.*;
import java.util.*;

/**
 *
 */
public class LoadPem {


    public static void main(String[] args) throws IOException {

        // pem 文件的格式为 "-----BEGIN PRIVATE KEY-----\n...\n...\n-----END PRIVATE KEY-----"
        // 这里的内容需要移除第一行、最后一行、和回车
        String priKeyStr = "MIIEvgIBA....";

        String pubKeyStr = "-----BEGIN CERTIFICATE-----\n...\n...\n-----END CERTIFICATE-----";

        PrivateKey privateKey = toRsaPriKey(priKeyStr);
        //PublicKey publicKey = toRsaPubKey(pubKeyStr);
        PublicKey publicKey = toRsaPubKey111111(pubKeyStr.getBytes(StandardCharsets.UTF_8));

        String data = "helloworld";
        String sign = rsaSign(data.getBytes(StandardCharsets.UTF_8), privateKey);
        boolean result = rsaSignVerify(data.getBytes(StandardCharsets.UTF_8), sign, publicKey);
        System.out.println(result);
    }

    public static String rsaSign(byte[] bytes, PrivateKey priKey) {
        try {
            Signature signature = Signature.getInstance("SHA1withRSA");
            signature.initSign(priKey);

            signature.update(bytes);
            byte[] result = signature.sign();
            String sign = Base64.getEncoder().encodeToString(result);
            return sign;

        } catch (NoSuchAlgorithmException
                | SignatureException
                | InvalidKeyException e) {
            throw new RuntimeException(e);
        }
    }

    public static PrivateKey toRsaPriKey(String priKeyInBase64) {
        byte[] priKeyBytes = Base64.getDecoder().decode(priKeyInBase64);
        PKCS8EncodedKeySpec priKeySpec = new PKCS8EncodedKeySpec(priKeyBytes);
        try {
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            PrivateKey priKey = keyFactory.generatePrivate(priKeySpec);
            return priKey;
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new RuntimeException(e);
        }
    }

    public static PublicKey toRsaPubKey(String pubKeyInBase64) {
        byte[] priKeyBytes = Base64.getDecoder().decode(pubKeyInBase64);
        return toRsaPubKey(priKeyBytes);
    }


    public static PublicKey toRsaPubKey111111(byte[] pubKeyBytes) {
        try {
            CertificateFactory fact = CertificateFactory.getInstance("X.509");
            X509Certificate cer = (X509Certificate) fact.generateCertificate(new ByteArrayInputStream(pubKeyBytes));
            PublicKey key = cer.getPublicKey();
            return key;
        } catch (CertificateException e) {
            throw new RuntimeException(e);
        }
    }

    public static PublicKey toRsaPubKey(byte[] pubKeyBytes) {
        try {
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(pubKeyBytes);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            PublicKey pubKey = keyFactory.generatePublic(keySpec);
            return pubKey;
        } catch (NoSuchAlgorithmException
                | InvalidKeySpecException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean rsaSignVerify(
            byte[] bytes,
            String sign,
            PublicKey pubKey
    ) {
        try {
            Signature signature = Signature.getInstance("SHA1withRSA");
            signature.initVerify(pubKey);

            signature.update(bytes);
            return signature.verify(Base64.getDecoder().decode(sign));

        } catch (NoSuchAlgorithmException
                | SignatureException
                | InvalidKeyException e) {
            throw new RuntimeException(e);
        }
    }


    public static String signWithRsa(
            String strToSign,
            String charset,
            String privateKeyInBase64
    ) {


        byte[] priKeyBytes = Base64.getDecoder().decode(privateKeyInBase64);
        PKCS8EncodedKeySpec priKeySpec = new PKCS8EncodedKeySpec(priKeyBytes);

        try {
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");

            PrivateKey priKey = keyFactory.generatePrivate(priKeySpec);
            Signature signature = Signature.getInstance("SHA1withRSA");
            signature.initSign(priKey);

            byte[] bytes = charset == null || charset.length() == 0
                    ? strToSign.getBytes()
                    : strToSign.getBytes(charset);

            signature.update(bytes);
            byte[] result = signature.sign();
            String sign = Base64.getEncoder().encodeToString(result);
            return sign;

        } catch (NoSuchAlgorithmException
                | UnsupportedEncodingException
                | SignatureException
                | InvalidKeyException
                | InvalidKeySpecException e) {
            throw new RuntimeException(e);
        }
    }
}
