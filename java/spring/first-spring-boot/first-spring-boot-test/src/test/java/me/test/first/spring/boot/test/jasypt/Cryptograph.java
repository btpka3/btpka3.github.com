package me.test.first.spring.boot.test.jasypt;

/**
 * @author dangqian.zll
 * @date 2024/8/19
 */
public interface Cryptograph {
    String encrypt(String plainText, String keyName);
    String decrypt(String encryptedText, String keyName);

    byte[] encrypt(byte[] plainBytes, String keyName);
    byte[] decrypt(byte[] encryptedBytes, String keyName);
}
