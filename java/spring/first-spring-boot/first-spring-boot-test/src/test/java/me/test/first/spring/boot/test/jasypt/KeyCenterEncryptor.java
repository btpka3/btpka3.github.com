package me.test.first.spring.boot.test.jasypt;


import org.springframework.stereotype.Component;

/**
 * @author dangqian.zll
 * @date 2024/8/19
 */
@Component("jasyptStringEncryptor")
public class KeyCenterEncryptor implements
//org.springframework.security.crypto.encrypt.TextEncryptor,
        //org.springframework.security.crypto.encrypt.BytesEncryptor,
        //org.jasypt.util.text.TextEncryptor,
        //org.jasypt.util.binary.BinaryEncryptor,
        org.jasypt.encryption.StringEncryptor,
        org.jasypt.encryption.ByteEncryptor {

    private Cryptograph cryptograph;
    private String keyName;

    public KeyCenterEncryptor(Cryptograph cryptograph, String keyName) {
        this.cryptograph = cryptograph;
        this.keyName = keyName;
    }

    @Override
    public String encrypt(String text) {
        return cryptograph.encrypt(text, keyName);
    }

    @Override
    public String decrypt(String encryptedText) {
        return cryptograph.decrypt(encryptedText, keyName);
    }

    @Override
    public byte[] encrypt(byte[] byteArray) {
        return cryptograph.encrypt(byteArray, keyName);
    }

    @Override
    public byte[] decrypt(byte[] encryptedByteArray) {
        return cryptograph.decrypt(encryptedByteArray, keyName);
    }
}
