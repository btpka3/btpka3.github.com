package io.github.btpka3.first.aliyun.kms;

import com.aliyun.kms.secretsmanager.plugin.oss.ProxyOSSClientBuilder;
import com.aliyun.kms.secretsmanager.plugin.sdkcore.ProxyAcsClient;
import com.aliyun.oss.OSS;
import com.aliyuncs.IAcsClient;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

/**
 * 不推荐：
 * <p>
 * - 支持阿里云 SDK V1.0,
 * - 不支持 阿里云 SDK V2.0, 可以使用 凭据客户端。
 * - 提供了 {@link com.aliyuncs.auth.AlibabaCloudCredentialsProvider} 的实现类 :
 * {@link com.aliyun.kms.secretsmanager.plugin.sdkcore.SdkCorePluginCredentialProvider},
 * 并对相关对象进行了封装。
 *
 * @author dangqian.zll
 * @date 2025/6/9
 * @see <a href="https://help.aliyun.com/zh/kms/key-management-service/developer-reference/ram-secret-plug-in">RAM凭据插件</a>
 * @see com.aliyun.kms.secretsmanager.plugin.common.utils.CredentialsUtils#generateCredentialsBySecret(java.lang.String)
 * @see com.aliyun.kms.secretsmanager.plugin.common.utils.Constants#DEFAULT_CREDENTIAL_PROPERTIES_FILE_NAME
 * @see com.aliyun.kms.secretsmanager.plugin.common.service.DefaultSecretsManagerPluginCredentialsProviderLoader#load()
 */
public class EvidenceSdkRamTest {

    /* 需要创建文件： managed_credentials_providers.properties

示例1： ecs_ram_role
----
credentials_type=ecs_ram_role
## ECS RAM Role名称
credentials_role_name=#credentials_role_name#
## 关联的KMS服务地域
cache_client_region_id=[{"regionId":"#regionId#"}]
----

示例2： client_key
----
## 访问凭据类型。
credentials_type=client_key

## 读取client key的凭证口令：支持从环境变量或者文件读取。
## 凭证口令：在应用接入点AAP中创建Client Key时保存的凭证口令（ClientKeyPassword）。
client_key_password_from_env_variable=#your client key private key password environment variable name#
client_key_password_from_file_path=#your client key private key password file path#

# Client Key文件路径。
# Client Key文件：在应用接入点AAP中创建Client Key时下载的应用身份凭证内容（ClientKeyContent）。
# 下载后文件名默认为ClientKey_******.json。
client_key_private_key_path=#your client key private key file path#

## 关联的KMS服务地域。
cache_client_region_id=[{"regionId":"#regionId#"}]
----
     */


    @SneakyThrows
    @Bean
    IAcsClient iAcsClient(
            @Value("${your.spring.placeholder.region-id}") String regionId,
            @Value("${your.spring.placeholder.secret-name}") String secretName
    ) {
        return new ProxyAcsClient(regionId, secretName);
    }

    @SneakyThrows
    @Bean
    OSS oss(
            @Value("${your.spring.placeholder.endpoint}") String endpoint,
            @Value("${your.spring.placeholder.secret-name}") String secretName
    ) {
        return new ProxyOSSClientBuilder().build(endpoint, secretName);
    }
}
