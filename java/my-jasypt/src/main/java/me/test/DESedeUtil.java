package me.test;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
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

public class DESedeUtil {

    public static final String ALGORITHM = "DESede";

    private String transformation = null;

    private static DESedeUtil instance = null;

    private byte[] ivParam = null;

    private DESedeUtil(String transformation) {
        this(transformation, new byte[8]);
    }

    private DESedeUtil(String transformation, byte[] ivParam) {
        super();
        this.transformation = transformation;
        this.ivParam = ivParam;
    }

    public synchronized static DESedeUtil getInstance() {
        if (instance == null) {
            instance = new DESedeUtil("DESede/CBC/PKCS5Padding");
        }
        return instance;
    }

    public byte[] encode(byte[] key, byte[] data) throws RuntimeException {
        try {
            SecretKeySpec skeySpec = new SecretKeySpec(key, ALGORITHM);
            IvParameterSpec iv = new IvParameterSpec(ivParam);

            Cipher cipher = Cipher.getInstance(transformation);
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
            return cipher.doFinal(data);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (NoSuchPaddingException e) {
            throw new RuntimeException(e);
        } catch (InvalidKeyException e) {
            throw new RuntimeException(e);
        } catch (InvalidAlgorithmParameterException e) {
            throw new RuntimeException(e);
        } catch (IllegalBlockSizeException e) {
            throw new RuntimeException(e);
        } catch (BadPaddingException e) {
            throw new RuntimeException(e);
        }
    }

    public byte[] decode(byte[] key, byte[] data) throws RuntimeException {
        try {
            SecretKeySpec skeySpec = new SecretKeySpec(key, ALGORITHM);
            IvParameterSpec iv = new IvParameterSpec(ivParam);
            Cipher cipher = Cipher.getInstance(transformation);
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
            return cipher.doFinal(data);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (NoSuchPaddingException e) {
            throw new RuntimeException(e);
        } catch (InvalidKeyException e) {
            throw new RuntimeException(e);
        } catch (InvalidAlgorithmParameterException e) {
            throw new RuntimeException(e);
        } catch (IllegalBlockSizeException e) {
            throw new RuntimeException(e);
        } catch (BadPaddingException e) {
            throw new RuntimeException(e);
        }
    }

    public byte[] generateRandomKey() throws RuntimeException {
        try {
            KeyGenerator kgen = KeyGenerator.getInstance(ALGORITHM);
            kgen.init(new SecureRandom());
            SecretKey skey = kgen.generateKey();
            return skey.getEncoded();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public byte[] generateKey(byte[] seed) throws RuntimeException {
        try {
            KeyGenerator kgen = KeyGenerator.getInstance(ALGORITHM);
            kgen.init(new SecureRandom(seed));
            SecretKey skey = kgen.generateKey();
            return skey.getEncoded();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) throws Exception {
        DESedeUtil desedeUtil = DESedeUtil.getInstance();

        byte[] key = desedeUtil.generateRandomKey();
        System.out.println("key length : " + key.length + " bytes ("
                + (key.length * 8) + " bits)");
        System.out.println("key data : " + Arrays.toString(key));

        String dataStr = "Hello world!";
        byte[] data = dataStr.getBytes();
        System.out.println("plain text : " + dataStr);
        System.out.println(" : " + Arrays.toString(data));

        byte[] encData = desedeUtil.encode(key, data);
        System.out.println("encrypted data : " + Arrays.toString(encData));

        byte[] decData = desedeUtil.decode(key, encData);
        System.out.println("decrypted data : " + Arrays.toString(decData));
        System.out.println("decrypted data (text) : " + new String(decData));
    }
}