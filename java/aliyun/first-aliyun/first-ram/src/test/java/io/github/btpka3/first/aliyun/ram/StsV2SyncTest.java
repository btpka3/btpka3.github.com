package io.github.btpka3.first.aliyun.ram;

import com.aliyun.sts20150401.Client;
import com.aliyun.sts20150401.models.*;
import com.aliyun.tea.TeaException;
import com.aliyun.teaopenapi.models.Config;
import com.aliyun.teautil.Common;
import com.aliyun.teautil.models.RuntimeOptions;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

/**
 * @author dangqian.zll
 * @date 2024/12/13
 * @see <a href="https://next.api.alibabacloud.com/product/Sts">安全令牌</a>
 */
public class StsV2SyncTest {


    /**
     * <b>description</b> :
     * <p>使用AK&amp;SK初始化账号Client</p>
     *
     * @return Client
     * @throws Exception
     */
    @SneakyThrows
    public static Client createClient() {
        // 工程代码泄露可能会导致 AccessKey 泄露，并威胁账号下所有资源的安全性。以下代码示例仅供参考。
        // 建议使用更安全的 STS 方式，更多鉴权访问方式请参见：https://help.aliyun.com/document_detail/378657.html。
        Config config = new Config()
                // 必填，请确保代码运行环境设置了环境变量 ALIBABA_CLOUD_ACCESS_KEY_ID。
                .setAccessKeyId(System.getenv("ALIBABA_CLOUD_ACCESS_KEY_ID"))
                // 必填，请确保代码运行环境设置了环境变量 ALIBABA_CLOUD_ACCESS_KEY_SECRET。
                .setAccessKeySecret(System.getenv("ALIBABA_CLOUD_ACCESS_KEY_SECRET"));
        // Endpoint 请参考 https://api.aliyun.com/product/Sts
        config.endpoint = "sts.cn-hangzhou.aliyuncs.com";
        return new Client(config);
    }

    public void assumeRole01() {
        Client client = createClient();
        AssumeRoleRequest assumeRoleRequest = new AssumeRoleRequest()
                .setDurationSeconds(1L)
                .setPolicy("your_value")
                .setRoleArn("your_value")
                .setRoleSessionName("your_value");
        try {
            // 复制代码运行请自行打印 API 的返回值
            AssumeRoleResponse resp = client.assumeRoleWithOptions(assumeRoleRequest, new RuntimeOptions());
            AssumeRoleResponseBody body = resp.getBody();
            AssumeRoleResponseBody.AssumeRoleResponseBodyAssumedRoleUser user = body.getAssumedRoleUser();
            // 临时身份的 ID。
            // 示例: "34458433936495****:alice"
            String assumeRoleId = user.getAssumedRoleId();
            // 临时身份的 ARN。
            // 示例: "acs:ram::123456789012****:role/adminrole/alice"
            String arn = user.getArn();
            AssumeRoleResponseBody.AssumeRoleResponseBodyCredentials credentials = body.getCredentials();
            // 安全令牌。
            String securityToken = credentials.getSecurityToken();
            // 访问密钥。
            String accessKeySecret = credentials.getAccessKeySecret();
            // 访问密钥 ID。
            String accessKeyId = credentials.getAccessKeyId();
            // Token 到期失效时间（UTC 时间）。
            String expiration = credentials.getExpiration();

            // TODO with accessKeySecret

        } catch (TeaException error) {
            // 此处仅做打印展示，请谨慎对待异常处理，在工程项目中切勿直接忽略异常。
            // 错误 message
            System.out.println(error.getMessage());
            // 诊断地址
            System.out.println(error.getData().get("Recommend"));
            Common.assertAsString(error.message);
        } catch (Exception _error) {
            TeaException error = new TeaException(_error.getMessage(), _error);
            // 此处仅做打印展示，请谨慎对待异常处理，在工程项目中切勿直接忽略异常。
            // 错误 message
            System.out.println(error.getMessage());
            // 诊断地址
            System.out.println(error.getData().get("Recommend"));
            Common.assertAsString(error.message);
        }
    }

    @Test
    public void getCallerIdentity01() {
        Client client = createClient();
        RuntimeOptions runtime = new RuntimeOptions();

        try {
            // Copy the code to run, please print the return value of the API by yourself.
            GetCallerIdentityResponse resp = client.getCallerIdentityWithOptions(runtime);
            GetCallerIdentityResponseBody body =resp.getBody();
            // 身份类型
            String identifyType  =body.getIdentityType();

        } catch (TeaException error) {
            // Only a printing example. Please be careful about exception handling and do not ignore exceptions directly in engineering projects.
            // print error message
            System.out.println(error.getMessage());
            // Please click on the link below for diagnosis.
            System.out.println(error.getData().get("Recommend"));
            Common.assertAsString(error.message);
        } catch (Exception _error) {
            TeaException error = new TeaException(_error.getMessage(), _error);
            // Only a printing example. Please be careful about exception handling and do not ignore exceptions directly in engineering projects.
            // print error message
            System.out.println(error.getMessage());
            // Please click on the link below for diagnosis.
            System.out.println(error.getData().get("Recommend"));
            Common.assertAsString(error.message);
        }
    }
}
