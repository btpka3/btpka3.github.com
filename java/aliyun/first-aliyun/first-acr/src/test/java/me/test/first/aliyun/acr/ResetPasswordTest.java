package me.test.first.aliyun.acr;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.cr.model.v20181201.ResetLoginPasswordRequest;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.HttpResponse;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author dangqian.zll
 * @date 2023/5/31
 * @see <a href="https://help.aliyun.com/document_detail/451391.html">ResetLoginPassword - 重置登录密码</a>
 */
public class ResetPasswordTest {


    @Test
    public void test() throws ClientException {
        String ak = System.getProperty("acr.ak");
        Assert.assertTrue(StringUtils.isNotBlank(ak));

        String sk = System.getProperty("acr.sk");
        Assert.assertTrue(StringUtils.isNotBlank(sk));

        String password = System.getProperty("acr.password");
        Assert.assertTrue(StringUtils.isNotBlank(password));

        String instanceId = System.getProperty("acr.instanceId");
        Assert.assertTrue(StringUtils.isNotBlank(instanceId));

        resetPassword(ak, sk, instanceId, password);
        System.out.println("reset password - done.");
    }

    public void resetPassword(
            String ak,
            String sk,
            String instanceId,
            String password
    ) throws ClientException {
        // 设置Client
        DefaultProfile.addEndpoint("cn-hanghzou", "cn-hangzhou", "cr", "cr.cn-hangzhou.aliyuncs.com");

        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", ak, sk);

        DefaultAcsClient client = new DefaultAcsClient(profile);

        // 构造请求
        ResetLoginPasswordRequest request = new ResetLoginPasswordRequest();

        // 设置参数
        request.setPassword(password);
        request.setInstanceId(instanceId);

        // 发起请求
        HttpResponse response = client.doAction(request);

        // 处理结果
        System.out.println(new String(response.getHttpContent()));
    }
}
