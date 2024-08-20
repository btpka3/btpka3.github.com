package me.test.first.spring.boot.test.jasypt;

import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.junit.jupiter.api.Test;

/**
 * 只使用 jasypt 的类进行验证，不使用 jasypt-spring-boot 的类。
 *
 * @author dangqian.zll
 * @date 2024/8/19
 */
public class JasyptTest {

    @Test
    public void encText() {
        PooledPBEStringEncryptor textEncryptor = new PooledPBEStringEncryptor();
        textEncryptor.setAlgorithm("PBEWITHHMACSHA512ANDAES_256");
        textEncryptor.setPassword("myMasterPassword");
        textEncryptor.setProviderName("SunJCE");
        textEncryptor.setSaltGenerator(new org.jasypt.salt.RandomSaltGenerator());
        textEncryptor.setIvGenerator(new org.jasypt.iv.RandomIvGenerator());
        textEncryptor.setStringOutputType("base64");
        textEncryptor.setPoolSize(1);

        // 加密
        String passwordToBeEncrypted = "123456";
        String myEncryptedText = textEncryptor.encrypt(passwordToBeEncrypted);
        System.out.println("myEncryptedText = " + myEncryptedText);
        myEncryptedText = textEncryptor.encrypt(passwordToBeEncrypted);
        System.out.println("myEncryptedText = " + myEncryptedText);
        myEncryptedText = textEncryptor.encrypt(passwordToBeEncrypted);
        System.out.println("myEncryptedText = " + myEncryptedText);

        // 解密
        String passwordDecrypted = textEncryptor.decrypt(myEncryptedText);
        System.out.println("passwordDecrypted = " + passwordDecrypted);
    }
}
