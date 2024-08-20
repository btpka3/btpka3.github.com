package me.test.jdk.java.security;

import org.junit.jupiter.api.Test;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.Base64;

/**
 * PBE: password-based encryption
 *
 * @author dangqian.zll
 * @date 2024/8/19
 */
public class PBETest {

    @Test
    public void test01() throws Exception {
        SecureRandom random = new SecureRandom(Long.toString(System.currentTimeMillis()).getBytes());

        String masterPassword = "myMasterPassword";

        int iterationCount = 1000;
        int keyLength = 56;

        //String algo = "PBEWithMD5AndTripeDES";
        //String algo = "PBEWITHMD5andDES";
        String algo = "PBEWITHHMACSHA512ANDAES_256";
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(algo);

        byte[] salt = new byte[8];
        random.nextBytes(salt);

        byte[] iv = new byte[16];
        random.nextBytes(iv);


        PBEKeySpec pbeKeySpec = new PBEKeySpec(
                masterPassword.toCharArray(),
                salt,
                iterationCount,
                keyLength
        );
        SecretKey secretKey = keyFactory.generateSecret(pbeKeySpec);

        String encryptedStr = null;
        {
            IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);
            PBEParameterSpec parameterSpec = new PBEParameterSpec(salt, iterationCount, ivParameterSpec);
            Cipher cipher = Cipher.getInstance(algo);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, parameterSpec);

            String dataStr = "123456";
            byte[] data = dataStr.getBytes();
            byte[] encryptedBytes = cipher.doFinal(data);

            encryptedStr = Base64.getEncoder().encodeToString(encryptedBytes);
            System.out.println("encryptedStr = " + encryptedStr);
        }
        {
            IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);
            PBEParameterSpec parameterSpec = new PBEParameterSpec(salt, iterationCount, ivParameterSpec);
            Cipher cipher = Cipher.getInstance(algo);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, parameterSpec);

            String dataStr = "123456";
            byte[] data = dataStr.getBytes();
            byte[] encryptedBytes = cipher.doFinal(data);

            encryptedStr = Base64.getEncoder().encodeToString(encryptedBytes);
            System.out.println("encryptedStr = " + encryptedStr);
        }

        {
            IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);
            PBEParameterSpec parameterSpec = new PBEParameterSpec(salt, iterationCount, ivParameterSpec);
            Cipher cipher = Cipher.getInstance(algo);
            cipher.init(Cipher.DECRYPT_MODE, secretKey, parameterSpec);
            byte[] encryptedBytes = Base64.getDecoder().decode(encryptedStr);
            byte[] decryptedBytes = cipher.doFinal(encryptedBytes);
            String decryptedStr = new String(decryptedBytes, StandardCharsets.UTF_8);
            System.out.println("decryptedStr = " + decryptedStr);
        }
    }
}
