package me.test.jdk.javax.crypto;

import java.nio.ByteBuffer;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by zll on 26/05/2017.
 */
public class AesTest {


    public static void main(String[] args) throws IllegalBlockSizeException, InvalidKeyException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException {

        String txt = "iBBU7lr2ewT8jAGfaXtefHG82XQjRLKuZDmJp3UxlT8fdz+gKhyt2sNRwTeXcXecjMlVxqb1XxNizUulDLsPOQ==";
        byte[] encdData = Base64.getDecoder().decode(txt);


        byte[] decdData = aesDec(encdData);
        System.out.println(new String(decdData));


        ByteBuffer wrapped = ByteBuffer.wrap(decdData, 16, 4);
        int msgLength = wrapped.getInt();
        System.out.println("msgLength : " + msgLength);

        String msg = new String(decdData, 16 + 4, msgLength);
        System.out.println("msg       : " + msg);

        String corpId = new String(decdData, 16 + 4 + msgLength, decdData.length - (16 + 4 + msgLength));
        System.out.println("corpId    : " + corpId);

    }

    public static final String KEY_ALGORITHM = "AES";
    public static final String ALGORITHM = "AES/CBC/PKCS5Padding";
    static final String token = "3MiXGzsw7CHRmImawgiVLItUj";
    static final String aesKeyInBase64 = "9eCbjyAqoXDYrLwjObW1pfsaU5LmT96IlWsOfbSuyii";
    static final byte[] aesKey = Base64.getDecoder().decode(aesKeyInBase64);
    static final IvParameterSpec iv = new IvParameterSpec(aesKey, 0, 16);

    private static byte[] aesEnc(byte[] msg) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException, InvalidAlgorithmParameterException {


        Key key = new SecretKeySpec(aesKey, KEY_ALGORITHM);
        Cipher c = Cipher.getInstance(ALGORITHM);
        c.init(Cipher.ENCRYPT_MODE, key, iv);

        byte[] result = c.doFinal(msg);
        return result;
    }

    private static byte[] aesDec(byte[] msg) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException, InvalidAlgorithmParameterException {

        SecretKey key = new SecretKeySpec(aesKey, KEY_ALGORITHM);
        Cipher c = Cipher.getInstance(ALGORITHM);
        c.init(Cipher.DECRYPT_MODE, key, iv);
        byte[] result = c.doFinal(msg);
        return result;
    }

}
