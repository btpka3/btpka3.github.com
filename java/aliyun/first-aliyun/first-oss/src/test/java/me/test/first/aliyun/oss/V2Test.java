package me.test.first.aliyun.oss;

import com.aliyun.auth.credentials.Credential;
import com.aliyun.auth.credentials.provider.DefaultCredentialProvider;
import com.aliyun.auth.credentials.provider.ICredentialProvider;
import com.aliyun.auth.credentials.provider.StaticCredentialProvider;
import com.aliyun.sdk.service.oss20190517.AsyncClient;
import com.aliyun.sdk.service.oss20190517.models.GetObjectMetaRequest;
import com.aliyun.sdk.service.oss20190517.models.GetObjectMetaResponse;
import darabonba.core.client.ClientOverrideConfiguration;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

import java.util.concurrent.CompletableFuture;

/**
 * @author dangqian.zll
 * @date 2025/6/6
 * @see <a href="https://api.aliyun.com/api-tools/sdk">SDK 中心</a>
 * @see <a href="https://help.aliyun.com/zh/sdk/product-overview/differences-between-v1-and-v2-sdks">V2.0 SDK 和 V1.0 SDK</a>
 */
public class V2Test {
    @Bean
    ICredentialProvider credentialsProvider2() {
        /*
        1. SystemPropertiesCredentialProvider : 使用JVM属性 `alibabacloud.accessKeyId`, `alibabacloud.accessKeySecret`
        1. EnvironmentVariableCredentialProvider : 使用环境变量: ALIBABA_CLOUD_ACCESS_KEY_ID,ALIBABA_CLOUD_ACCESS_KEY_SECRET,ALIBABA_CLOUD_SECURITY_TOKEN 取值 ak/sk/token
        1. OIDCRoleArnCredentialProvider: 使用环境变量: ALIBABA_CLOUD_ROLE_ARN,ALIBABA_CLOUD_OIDC_PROVIDER_ARN,ALIBABA_CLOUD_OIDC_TOKEN_FILE
        1. CLIProfileCredentialsProvider: ${user.home}/.aliyun/config.json 解析成 com.aliyun.auth.credentials.provider.CLIProfileCredentialsProvider.Config
        1. ProfileCredentialProvider: ${user.home}/.alibabacloud/credentials.ini , 默认使用 "default", 每个 section 根据子配置 "type" 的不同，创建不同的 ICredentialProvider 实现：
            - "ram_role_arn"    : RamRoleArnCredentialProvider
            - "rsa_key_pair"    : RsaKeyPairCredentialProvider
            - "ecs_ram_role"    : EcsRamRoleCredentialProvider
            - "oidc_role_arn"   : OIDCRoleArnCredentialProvider
            - *                 : StaticCredentialProvider
        1. EcsRamRoleCredentialProvider: 使用环境变量 ALIBABA_CLOUD_ECS_METADATA_DISABLED,ALIBABA_CLOUD_ECS_METADATA,ALIBABA_CLOUD_IMDSV1_DISABLED
        1. URLCredentialProvider: 使用环境变量 ALIBABA_CLOUD_CREDENTIALS_URI, 从目标URL返回值的JSON中解析出 Expiration/AccessKeyId,AccessKeySecret,SecurityToken
        */
        return DefaultCredentialProvider.builder()
                .build();
    }

    @Bean
    ICredentialProvider credentialsProvider(
            @Value("${your.spring.placeholder.access-key-id}") String accessKeyId,
            @Value("${your.spring.placeholder.access-key-secret}") String accessKeySecret
    ) {
        return StaticCredentialProvider.create(Credential.builder()
                .accessKeyId(accessKeyId)
                .accessKeySecret(accessKeySecret)
                //.securityToken(null)
                .build());
    }

    @Bean(destroyMethod = "close")
    AsyncClient asyncClient(
            ICredentialProvider credentialsProvider,
            @Value("${your.spring.placeholder.endpoint}") String endpoint
    ) {
        return AsyncClient.builder()
                //.httpClient(httpClient) // Use the configured HttpClient, otherwise use the default HttpClient (Apache HttpClient)
                .credentialsProvider(credentialsProvider)
                //.serviceConfiguration(Configuration.create()) // Service-level configuration
                // Client-level configuration rewrite, can set Endpoint, Http request parameters, etc.
                .overrideConfiguration(
                        ClientOverrideConfiguration.create()
                                // Endpoint 请参考 https://api.aliyun.com/product/Oss
                                .setEndpointOverride(endpoint)
                        //.setConnectTimeout(Duration.ofSeconds(30))
                )
                .build();
    }

    @SneakyThrows
    void getObjectMeta(
            AsyncClient client,
            @Value("${your.spring.placeholder.bucket}") String bucket,
            @Value("${your.spring.placeholder.key}") String key
    ) {
        GetObjectMetaRequest getObjectMetaRequest = GetObjectMetaRequest.builder()
                .bucket(bucket)
                .key(key)
                // Request-level configuration rewrite, can set Http request parameters, etc.
                // .requestConfiguration(RequestConfiguration.create().setHttpHeaders(new HttpHeaders()))
                .build();
        CompletableFuture<GetObjectMetaResponse> response = client.getObjectMeta(getObjectMetaRequest);
        // Synchronously get the return value of the API request
        GetObjectMetaResponse resp = response.get();
    }
}
