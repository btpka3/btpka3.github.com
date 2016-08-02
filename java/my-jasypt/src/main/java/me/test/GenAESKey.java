package me.test;

import java.security.MessageDigest;
import java.util.Arrays;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class GenAESKey {
    public static void main(String[] args) throws Exception {
        SecretKey key = null;
        key = getSecretKeyBaseOnUserPassword("123", 128);
        System.out.println(Arrays.toString(key.getEncoded()));
        System.out.println(Arrays.toString(key.getEncoded()));
    }

    private static SecretKey getSecretKeyBaseOnUserPassword(
            String userPassword, int keySize) {
        try {
            // AES 的密码长度有 128, 192, 256 三种，故在选择满足其最大长度的摘要算法。
            // 注意：如果要使用 256 位的AES，需要下载 JCE
            MessageDigest digester = MessageDigest.getInstance("SHA-256");

            byte[] digest = digester.digest(userPassword.getBytes("UTF-8"));
            byte[] key = new byte[keySize / 8];
            System.arraycopy(digest, 0, key, 0, keySize / 8);

            return new SecretKeySpec(key, "AES");

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
