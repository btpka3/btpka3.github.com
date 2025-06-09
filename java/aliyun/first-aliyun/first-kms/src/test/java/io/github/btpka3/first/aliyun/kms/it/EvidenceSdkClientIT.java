package io.github.btpka3.first.aliyun.kms.it;

import com.aliyuncs.kms.secretsmanager.client.SecretCacheClient;
import com.aliyuncs.kms.secretsmanager.client.SecretCacheClientBuilder;
import com.aliyuncs.kms.secretsmanager.client.model.SecretInfo;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

/**
 * 获取凭据的内容。
 * <p>
 * 支持 阿里云 V2.0 版本的SDK
 *
 * @author dangqian.zll
 * @date 2025/6/9
 * @see <a href="https://help.aliyun.com/zh/kms/key-management-service/developer-reference/secrets-manager-sdk/">凭据SDK</a>
 * @see <a href="https://help.aliyun.com/zh/kms/key-management-service/developer-reference/secrets-manager-client">凭据客户端</a>
 * @see com.aliyuncs.kms.secretsmanager.client.utils.CacheClientConstant#CREDENTIALS_PROPERTIES_CONFIG_NAME
 * @see com.aliyuncs.kms.secretsmanager.client.utils.CredentialsPropertiesUtils#loadCredentialsProperties(String)
 */
public class EvidenceSdkClientIT {

    SecretCacheClient client = client();

    @SneakyThrows
    SecretCacheClient client() {
        System.out.println("user.home=" + System.getProperty("user.home"));
        System.out.println("user.dir=" + System.getProperty("user.dir"));
        return new SecretCacheClientBuilder()
                .build();
    }

    @Test
    @SneakyThrows
    public void getSecretInfo() {
        String secretName = "dangqian.key001";
        SecretInfo secretInfo = client.getSecretInfo(secretName);
        String secretDataType = secretInfo.getSecretDataType();
        String secretValue = secretInfo.getSecretValue();
        System.out.println("secretDataType=" + secretDataType);
        System.out.println("secretValue=" + secretValue);
    }
}
