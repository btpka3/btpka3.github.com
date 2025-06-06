package me.test.first.aliyun.oss;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.common.auth.CredentialsProvider;
import com.aliyuncs.auth.*;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

/**
 * @author dangqian.zll
 * @date 2025/6/6
 * @see <a href="https://api.aliyun.com/api-tools/sdk">SDK 中心</a>
 * @see <a href="https://help.aliyun.com/zh/sdk/product-overview/differences-between-v1-and-v2-sdks">V2.0 SDK 和 V1.0 SDK</a>
 * @see SystemPropertiesCredentialsProvider
 * @see EnvironmentVariableCredentialsProvider
 * @see OIDCCredentialsProvider
 * @see CLIProfileCredentialsProvider
 * @see ProfileCredentialsProvider
 * @see InstanceProfileCredentialsProvider
 * @see URLCredentialsProvider
 * @see STSAssumeRoleSessionCredentialsProvider
 * @see STSGetSessionAccessKeyCredentialsProvider
 * @see BasicCredentials
 * @see StaticCredentialsProvider
 */
public class V1Test {
    @SneakyThrows
    @Bean
    AlibabaCloudCredentialsProvider alibabaCloudCredentialsProvider2() {
        /*
        1. SystemPropertiesCredentialsProvider : 使用JVM属性 `alibabacloud.accessKeyId`, `alibabacloud.accessKeySecret`
        1. EnvironmentVariableCredentialsProvider : 使用环境变量: ALIBABA_CLOUD_ACCESS_KEY_ID,ALIBABA_CLOUD_ACCESS_KEY_SECRET,ALIBABA_CLOUD_SECURITY_TOKEN 取值 ak/sk/token
        1. OIDCCredentialsProvider: 使用环境变量: ALIBABA_CLOUD_ROLE_ARN,ALIBABA_CLOUD_OIDC_PROVIDER_ARN,ALIBABA_CLOUD_OIDC_TOKEN_FILE
        1. CLIProfileCredentialsProvider: ${user.home}/.aliyun/config.json 解析成 com.aliyun.auth.credentials.provider.CLIProfileCredentialsProvider.Config
        1. ProfileCredentialsProvider: ${user.home}/.alibabacloud/credentials.ini , 默认使用 "default", 每个 section 根据子配置 "type" 的不同，创建不同的 ICredentialProvider 实现：
            - "ram_role_arn"    : STSAssumeRoleSessionCredentialsProvider
            - "rsa_key_pair"    : STSGetSessionAccessKeyCredentialsProvider
            - "ecs_ram_role"    : InstanceProfileCredentialsProvider
            - "oidc_role_arn"   : OIDCCredentialsProvider
            - *                 : BasicCredentials
        1. InstanceProfileCredentialsProvider: 使用环境变量 ALIBABA_CLOUD_ECS_METADATA_DISABLED,ALIBABA_CLOUD_ECS_METADATA,ALIBABA_CLOUD_IMDSV1_DISABLED
        1. URLCredentialsProvider: 使用环境变量 ALIBABA_CLOUD_CREDENTIALS_URI, 从目标URL返回值的JSON中解析出 Expiration/AccessKeyId,AccessKeySecret,SecurityToken
        */
        return new DefaultCredentialsProvider();
    }

    @SneakyThrows
    @Bean
    AlibabaCloudCredentialsProvider alibabaCloudCredentialsProvider(
            @Value("${your.spring.placeholder.accessKeyId}") String accessKeyId,
            @Value("${your.spring.placeholder.accessKeySecret}") String accessKeySecret
    ) {
        return new StaticCredentialsProvider(new BasicCredentials(accessKeyId, accessKeySecret));
    }

    @Bean
    CredentialsProvider credentialsProvider(
            AlibabaCloudCredentialsProvider alibabaCloudCredentialsProvider,
            @Value("${your.spring.placeholder.ramRoleArn}") String ramRoleArn,
            @Value("${your.spring.placeholder.regionId}") String regionId
    ) {
        IClientProfile profile = DefaultProfile.getProfile(regionId);
        return new com.aliyun.oss.common.auth.STSAssumeRoleSessionCredentialsProvider(alibabaCloudCredentialsProvider, ramRoleArn, profile);
    }

    @Bean
    OSS oss(
            @Value("${your.spring.placeholder.endpoint}") String endpoint,
            CredentialsProvider credentialsProvider
    ) {
        return new OSSClientBuilder().build(endpoint, credentialsProvider);
    }
}
