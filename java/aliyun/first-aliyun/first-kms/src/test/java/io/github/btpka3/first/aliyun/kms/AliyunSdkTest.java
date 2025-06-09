package io.github.btpka3.first.aliyun.kms;

import com.aliyun.kms20160120.Client;
import com.aliyun.kms20160120.models.*;
import com.aliyun.teaopenapi.models.Config;
import com.aliyun.teautil.models.RuntimeOptions;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

/**
 * kms 的 阿里云SDK 提供加密、解密、计算签名、验证签名的接口。
 *
 * @author dangqian.zll
 * @date 2025/6/9
 * @see <a href="https://help.aliyun.com/zh/kms/key-management-service/developer-reference/classic-kms-sdkclassic-kms-sdk/">阿里云SDK</a>
 */
public class AliyunSdkTest {

    /**
     * 初始化客户端（共享网关）
     */
    @SneakyThrows
    @Bean
    Client client(
            @Value("${your.spring.placeholder.access-key-id}") String accessKeyId,
            @Value("${your.spring.placeholder.access-key-secret}") String accessKeySecret,
            @Value("${your.spring.placeholder.endpoint}") String endpoint
    ) {
        Config config = new Config()
                .setAccessKeyId(accessKeyId)
                .setAccessKeySecret(accessKeySecret)
                .setEndpoint(endpoint);
        return new Client(config);
    }

    /**
     * 初始化客户端（专属网关）
     */
    @SneakyThrows
    @Bean
    Client client2(
            @Value("${your.spring.placeholder.access-key-id}") String accessKeyId,
            @Value("${your.spring.placeholder.access-key-secret}") String accessKeySecret,
            @Value("${your.spring.placeholder.endpoint}") String endpoint,
            @Value("${your.spring.placeholder.ca}") String ca
    ) {
        Config config = new Config()
                .setAccessKeyId(accessKeyId)
                .setAccessKeySecret(accessKeySecret)
                .setEndpoint(endpoint)
                .setCa(ca);
        return new Client(config);
    }


    @SneakyThrows
    public String encrypt(
            Client client,
            @Value("${your.spring.placeholder.clear-txt}") String clearTxt,
            @Value("${your.spring.placeholder.key-id}") String keyId
    ) {
        EncryptRequest encryptRequest = new EncryptRequest()
                .setPlaintext(clearTxt)
                .setKeyId(keyId);
        RuntimeOptions runtime = new RuntimeOptions();
        EncryptResponse resp = client.encryptWithOptions(encryptRequest, runtime);
        EncryptResponseBody respBody = resp.getBody();
        return respBody.getCiphertextBlob();
    }

    @SneakyThrows
    public String encrypt(
            Client client,
            @Value("${your.spring.placeholder.encrypted-txt}") String encryptedTxt
    ) {
        // FIXME: 为何解密不需要 keyId ？ 密文中已经包含了keyId?
        DecryptRequest decryptRequest = new DecryptRequest()
                .setCiphertextBlob(encryptedTxt);
        RuntimeOptions runtime = new RuntimeOptions();

        DecryptResponse resp = client.decryptWithOptions(decryptRequest, runtime);
        DecryptResponseBody respBody = resp.getBody();
        return respBody.getPlaintext();
    }

    /**
     * 调用AsymmetricSign接口使用非对称密钥签名
     */
    @SneakyThrows
    public String calcSign(
            Client client,
            @Value("${your.spring.placeholder.key-id}") String keyId,
            @Value("${your.spring.placeholder.key-version-id}") String keyVersionId,
            @Value("${your.spring.placeholder.algorithm}") String algorithm,
            String digest // digest=base64(sha256(xxxBytes))
    ) {
        AsymmetricSignRequest asymmetricSignRequest = new AsymmetricSignRequest()
                .setKeyId(keyId)
                .setKeyVersionId(keyVersionId)
                .setAlgorithm(algorithm)
                .setDigest(digest);
        RuntimeOptions runtime = new RuntimeOptions();
        AsymmetricSignResponse resp = client.asymmetricSignWithOptions(asymmetricSignRequest, runtime);
        // FIXME : 如何检查 resp.getStatusCode();
        AsymmetricSignResponseBody respBody = resp.getBody();
        return respBody.getValue();
    }

    /**
     * 调用AsymmetricVerify接口使用非对称密钥验签
     */
    @SneakyThrows
    public boolean verifySign(
            Client client,
            @Value("${your.spring.placeholder.key-id}") String keyId,
            @Value("${your.spring.placeholder.key-version-id}") String keyVersionId,
            @Value("${your.spring.placeholder.algorithm}") String algorithm,
            String digest, // digest=base64(sha256(xxxBytes))
            String sign
    ) {
        AsymmetricVerifyRequest asymmetricVerifyRequest = new AsymmetricVerifyRequest()
                .setKeyId(keyId)
                .setKeyVersionId(keyVersionId)
                .setAlgorithm(algorithm)
                .setDigest(digest)
                .setValue(sign);
        RuntimeOptions runtime = new RuntimeOptions();
        AsymmetricVerifyResponse resp = client.asymmetricVerifyWithOptions(asymmetricVerifyRequest, runtime);
        // FIXME : 如何检查 resp.getStatusCode();
        AsymmetricVerifyResponseBody respBody = resp.getBody();
        return respBody.getValue();
    }
}
