package io.github.btpka3.first.aliyun.kms.it;

import com.aliyun.kms20160120.Client;
import com.aliyun.kms20160120.models.*;
import com.aliyun.teaopenapi.models.Config;
import com.aliyun.teautil.models.RuntimeOptions;
import lombok.SneakyThrows;
import org.apache.commons.codec.digest.DigestUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.Bean;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * kms 的 阿里云SDK 提供加密、解密、计算签名、验证签名的接口。
 * <p>
 * 通过公网访问时密码运算操作的QPS都不超过1000
 *
 * @author dangqian.zll
 * @date 2025/6/9
 * @see <a href="https://help.aliyun.com/zh/kms/key-management-service/developer-reference/classic-kms-sdkclassic-kms-sdk/">阿里云SDK</a>
 * @see <a href="https://help.aliyun.com/zh/kms/key-management-service/user-guide/access-keys-of-a-kms-instance-over-the-internet">通过公网访问KMS实例中的密钥</a>
 * @see <a href="https://help.aliyun.com/zh/kms/key-management-service/support/generate-and-verify-a-digital-signature-by-using-an-asymmetric-cmk">签名预处理：计算消息摘要</a>
 */
public class AliyunSdkIT {

    Client client = client();
    String encryptKeyId = "key-bjj684678af01gzg3rbnt";
    String singKeyId = "key-bjj684678fb1v5w4nm8fu";
    String singKeyVersion = "key-bjj684678fb1v5w4nm8fu-s5m1764mk5";
    String singKeyAlgorithm = "RSA_PSS_SHA_256";


    public static String calcDigest(byte[] bytes) {
        byte[] sha256Bytes = DigestUtils.sha256(bytes);
        return Base64.getEncoder().encodeToString(sha256Bytes);
    }

    /**
     * 初始化客户端（共享网关）
     */
    @SneakyThrows
    @Bean
    Client client() {
        AkSkConf akSkConf = AkSkConf.getAkSkConf("AkSkConf.cro_deliver.dangqian_test1.json");
        String endpoint = "kms.cn-beijing.aliyuncs.com";
        Config config = new Config()
                .setAccessKeyId(akSkConf.getAccessKeyId())
                .setAccessKeySecret(akSkConf.getAccessKeySecret())
                .setEndpoint(endpoint);
        return new Client(config);
    }

    @Test
    public void testEncryptAndDecrypt() {
        System.out.println("================ testEncryptAndDecrypt");
        String clearTxt = "hello world~";
        String encryptedTxt = encrypt(clearTxt);
        String clearTxt2 = decrypt(encryptedTxt);
        System.out.println("clearTxt=" + clearTxt);
        System.out.println("encryptedTxt=" + encryptedTxt);
        System.out.println("clearTxt2=" + clearTxt2);
        Assertions.assertNotEquals(clearTxt, encryptedTxt);
        Assertions.assertEquals(clearTxt, clearTxt2);
    }


    @Test
    public void testSign() {
        System.out.println("================ testSign");
        String str = "hello world~";
        String digest = calcDigest(str.getBytes(StandardCharsets.UTF_8));
        String sign = asymmetricSign(digest);

        boolean result = asymmetricVerify(digest, sign);
        System.out.println("sign=" + sign);
        System.out.println("digest=" + digest);
        System.out.println("result=" + result);
        Assertions.assertTrue(result);
    }

    @SneakyThrows
    public String encrypt(
            String clearTxt
    ) {
        EncryptRequest encryptRequest = new EncryptRequest()
                .setPlaintext(clearTxt)
                .setKeyId(encryptKeyId);
        RuntimeOptions runtime = new RuntimeOptions();
        EncryptResponse resp = client.encryptWithOptions(encryptRequest, runtime);
        EncryptResponseBody respBody = resp.getBody();
        String encryptedTxt = respBody.getCiphertextBlob();
        return encryptedTxt;
    }

    @SneakyThrows
    public String decrypt(
            String encryptedTxt
    ) {
        DecryptRequest decryptRequest = new DecryptRequest()
                .setCiphertextBlob(encryptedTxt);
        RuntimeOptions runtime = new RuntimeOptions();

        DecryptResponse resp = client.decryptWithOptions(decryptRequest, runtime);
        DecryptResponseBody respBody = resp.getBody();
        String clearTxt = respBody.getPlaintext();
        return clearTxt;
    }

    /**
     * 调用AsymmetricSign接口使用非对称密钥签名
     */
    @SneakyThrows
    public String asymmetricSign(
            String digest
    ) {
        AsymmetricSignRequest asymmetricSignRequest = new AsymmetricSignRequest()
                .setKeyId(singKeyId)
                .setKeyVersionId(singKeyVersion)
                .setAlgorithm(singKeyAlgorithm)
                .setDigest(digest);

        RuntimeOptions runtime = new RuntimeOptions();

        AsymmetricSignResponse resp = client.asymmetricSignWithOptions(asymmetricSignRequest, runtime);
        // FIXME : 如何检查 resp.getStatusCode();

        AsymmetricSignResponseBody respBody = resp.getBody();

        // 签名
        String sign = respBody.getValue();
        return sign;
    }

    /**
     * 调用AsymmetricVerify接口使用非对称密钥验签
     */
    @SneakyThrows
    public boolean asymmetricVerify(
            String digest,
            String sign

    ) {

        AsymmetricVerifyRequest asymmetricVerifyRequest = new AsymmetricVerifyRequest()
                .setKeyId(singKeyId)
                .setKeyVersionId(singKeyVersion)
                .setAlgorithm(singKeyAlgorithm)
                .setDigest(digest)
                .setValue(sign);


        RuntimeOptions runtime = new RuntimeOptions();

        AsymmetricVerifyResponse resp = client.asymmetricVerifyWithOptions(asymmetricVerifyRequest, runtime);
        // FIXME : 如何检查 resp.getStatusCode();

        AsymmetricVerifyResponseBody respBody = resp.getBody();

        // 签名
        Boolean isSignPassed = respBody.getValue();
        return isSignPassed;
    }

}
