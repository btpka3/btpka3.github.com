package me.test.first.spring.boot.test.security;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Hex;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.encrypt.BytesEncryptor;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.security.crypto.encrypt.TextEncryptor;

import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 *
 * @author dangqian.zll
 * @date 2026/1/5
 */
@Slf4j
public class SpringSecurityEncryptorTest {
    @Test
    public void x() {
        String password = "password";
        String salt = "salt";
        {
            TextEncryptor textEncryptor = Encryptors.text(
                    Hex.encodeHexString(password.getBytes(StandardCharsets.UTF_8)),
                    Hex.encodeHexString(salt.getBytes(StandardCharsets.UTF_8))
            );
            String clearText = "hello world";
            String encryptedText = textEncryptor.encrypt(clearText);
            String decryptedText = textEncryptor.decrypt(encryptedText);
            log.info("clearText: {}, encryptedText: {}, decryptedText: {}", clearText, encryptedText, decryptedText);
            assertEquals(clearText, decryptedText);
        }
        {
            BytesEncryptor bytesEncryptor = Encryptors.standard(
                    Hex.encodeHexString(password.getBytes(StandardCharsets.UTF_8)),
                    Hex.encodeHexString(salt.getBytes(StandardCharsets.UTF_8))
            );
            byte[] clearBytes = "hello world".getBytes(StandardCharsets.UTF_8);
            byte[] encryptedBytes = bytesEncryptor.encrypt(clearBytes);
            byte[] decryptedBytes = bytesEncryptor.decrypt(encryptedBytes);
            assertArrayEquals(clearBytes, decryptedBytes);
        }
    }
}
