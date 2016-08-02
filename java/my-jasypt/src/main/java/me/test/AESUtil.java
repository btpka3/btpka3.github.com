package me.test;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.util.Arrays;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class AESUtil {

    public static final String ALGORITHM = "AES";

    /** 128, 192, 256 */
    // when keysize != 128

    // Download Java(TM) Cryptography Extension (JCE) Unlimited Strength

    // Jurisdiction Policy Files

    // file name : jce_policy-6.zip

    private int keySize = 128;

    private String transformation = null;

    private static AESUtil instance = null;

    private AESUtil(int keySize, String transformation) {
        super();
        this.keySize = keySize;
        this.transformation = transformation;
    }

    public synchronized static AESUtil getInstance() {
        if (instance == null) {
            instance = new AESUtil(128, "AES/CBC/PKCS5Padding");
        }
        return instance;
    }

    public byte[] encode(byte[] key, byte[] data) throws InvalidKeyException,
            InvalidAlgorithmParameterException, IllegalBlockSizeException,
            BadPaddingException, NoSuchProviderException {

        SecretKeySpec skeySpec = new SecretKeySpec(key, ALGORITHM);
        IvParameterSpec iv = new IvParameterSpec(new byte[16]);

        try {
            Cipher cipher = Cipher.getInstance(transformation);
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
            return cipher.doFinal(data);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (NoSuchPaddingException e) {
            throw new RuntimeException(e);
        }
    }

    public byte[] decode(byte[] key, byte[] data) throws InvalidKeyException,
            InvalidAlgorithmParameterException, IllegalBlockSizeException,
            BadPaddingException {
        try {
            SecretKeySpec skeySpec = new SecretKeySpec(key, ALGORITHM);
            IvParameterSpec iv = new IvParameterSpec(new byte[16]);
            Cipher cipher = Cipher.getInstance(transformation);
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
            return cipher.doFinal(data);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (NoSuchPaddingException e) {
            throw new RuntimeException(e);
        }
    }

    public byte[] generateRandomKey() throws RuntimeException {
        try {
            KeyGenerator kgen = KeyGenerator.getInstance(ALGORITHM);
            kgen.init(keySize, new SecureRandom());
            SecretKey skey = kgen.generateKey();
            return skey.getEncoded();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public byte[] generateKey(byte[] seed) throws RuntimeException {
        try {
            KeyGenerator kgen = KeyGenerator.getInstance(ALGORITHM);
            kgen.init(keySize, new SecureRandom(seed));
            SecretKey skey = kgen.generateKey();
            return skey.getEncoded();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) throws Exception {
        AESUtil aesUtil = AESUtil.getInstance();

        byte[] key = aesUtil.generateRandomKey();
        System.out.println("key length : " + key.length + " bytes ("
                + (key.length * 8) + " bits)");
        System.out.println("key data : " + Arrays.toString(key));

        String dataStr = "Hello world!";
        byte[] data = dataStr.getBytes();
        System.out.println("plain text : " + dataStr);
        System.out.println(" : " + Arrays.toString(data));

        byte[] encData = aesUtil.encode(key, data);
        System.out.println("encrypted data : " + Arrays.toString(encData));

        byte[] decData = aesUtil.decode(key, encData);
        System.out.println("decrypted data : " + Arrays.toString(decData));
        System.out.println("decrypted data (text) : " + new String(decData));
    }
}