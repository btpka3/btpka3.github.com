package io.github.btpka3.first.aliyun.kms;

import com.aliyuncs.kms.secretsmanager.client.SecretCacheClient;
import com.aliyuncs.kms.secretsmanager.client.SecretCacheClientBuilder;
import com.aliyuncs.kms.secretsmanager.client.model.SecretInfo;
import com.aliyuncs.kms.secretsmanager.client.service.DefaultSecretManagerClientBuilder;
import lombok.SneakyThrows;
import org.springframework.context.annotation.Bean;

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
public class EvidenceSdkClientTest {

    /* 需要通过环境变量，或者 secretsmanager.properties 配置文件

示例1: ECS实例RAM角色
----
credentials_type=ecs_ram_role
credentials_role_name=xxx
cache_client_region_id=[{"regionId":"<your region id>"}]
----

示例2: RAMRoleArn
----
# 访问凭据类型
credentials_type=ram_role
# 角色名称
credentials_role_session_name=#role name#
# 资源短名称
credentials_role_arn=#role arn#
# AK
credentials_access_key_id=#access key id#
# SK
credentials_access_secret=#access key secret#
# 关联的KMS服务地域
cache_client_region_id=[{"regionId":"#regionId#"}]
----

示例3: STS Token
----
# 访问凭据类型
credentials_type=sts
# 角色名称
credentials_role_session_name=#role name#
# 资源短名称
credentials_role_arn=#role arn#
# AK
credentials_access_key_id=#access key id#
# SK
credentials_access_secret=#access key secret#
# 关联的KMS服务地域
cache_client_region_id=[{"regionId":"#regionId#"}]
----

示例4: AccessKey
----
# 访问凭据类型
credentials_type=ak
# AK
credentials_access_key_id=#access key id#
# SK
credentials_access_secret=#access key secret#
# 关联的KMS服务地域
cache_client_region_id=[{"regionId":"#regionId#"}]
----

示例5: ClientKey
----
# 访问凭据类型
credentials_type=client_key
# 读取client key的解密密码：支持从环境变量或者文件读取
client_key_password_from_env_variable=#your client key private key password environment variable name#
client_key_password_from_file_path=#your client key private key password file path#
# Client Key私钥文件路径
client_key_private_key_path=#your client key private key file path#
# 关联的KMS服务地域
cache_client_region_id=[{"regionId":"#regionId#"}]
----
     */

    @SneakyThrows
    @Bean
    SecretCacheClient client() {
        return new SecretCacheClientBuilder()
                .build();
    }

    /**
     * 使用 自定义配置文件 初始化
     */
    @SneakyThrows
    @Bean
    SecretCacheClient client2() {
        return SecretCacheClientBuilder.newCacheClientBuilder(
                        DefaultSecretManagerClientBuilder.standard()
                                .withCustomConfigFile("/your/path/to/secretsmanager.properties")
                                .build()
                )
                .build();
    }

    @SneakyThrows
    void getSecretInfo(
            SecretCacheClient client,
            String secretName
    ) {
        SecretInfo secretInfo = client.getSecretInfo(secretName);
        String secretDataType = secretInfo.getSecretDataType();
        String secretName1 = secretInfo.getSecretName();
        String secretValue = secretInfo.getSecretValue();
    }
}
