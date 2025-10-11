package me.test.jdk.javax.crypto;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.*;
import javax.xml.bind.DatatypeConverter;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.Security;

// NOT USING "Java Cryptography Extension (JCE) Unlimited Strength Jurisdiction Policy Files 6"
// http://bouncycastle.org/wiki/display/JA1/Provider+Installation
// http://docs.oracle.com/javase/7/docs/technotes/guides/security/StandardNames.html
public class DESTest {

    public static void main(String[] args) throws InterruptedException, UnsupportedEncodingException,
            NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException,
            InvalidKeyException, BadPaddingException, IllegalBlockSizeException {

        // 请注意避免重复加入
        Security.addProvider(new BouncyCastleProvider());

        // 生成随机秘钥
        KeyGenerator keygenerator = KeyGenerator.getInstance("DES");
        SecretKey desKey = keygenerator.generateKey();

        // 生成加密器
        Cipher cipher = Cipher.getInstance("DES/ECB/PKCS7Padding");
        cipher.init(Cipher.ENCRYPT_MODE, desKey);

        // 准备明文数据
        String txt = "Hello World~";

        // 加密
        byte[] encData = cipher.doFinal(txt.getBytes(StandardCharsets.UTF_8));
        System.out.println("ENC DATA : " + DatatypeConverter.printHexBinary(encData));

        // 解密
        cipher.init(Cipher.DECRYPT_MODE, desKey);
        byte[] decData = cipher.doFinal(encData);
        System.out.println("DEC DATA : " + new String(decData, StandardCharsets.UTF_8));
    }

}
