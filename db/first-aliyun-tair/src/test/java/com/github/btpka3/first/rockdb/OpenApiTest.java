package com.github.btpka3.first.rockdb;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.r_kvstore.model.v20150101.DescribeAccountsRequest;
import com.aliyuncs.r_kvstore.model.v20150101.DescribeAccountsResponse;
import org.junit.jupiter.api.Test;

/**
 * OpenAPI 主要是for运维，对tair实例进行各种管理。
 *
 * @author dangqian.zll
 * @date 2023/3/2
 * @see <a href="https://help.aliyun.com/document_detail/443879.html">OpenAPI-SDK</a>
 * @see <a href="https://help.aliyun.com/document_detail/141646.htm">通过SDK调用API（Java）</a>
 * @see <a href="https://help.aliyun.com/document_detail/473816.html">DescribeAccounts - 查找账号信息</a>
 * @see <a href="https://github.com/aliyun/aliyun-openapi-java-sdk/tree/master/aliyun-java-sdk-r-kvstore">aliyun/aliyun-openapi-java-sdk : aliyun-java-sdk-r-kvstore</a>
 */
public class OpenApiTest {

    @Test
    public void test01() {

        // 创建DefaultAcsClient实例并初始化
        DefaultProfile profile = DefaultProfile.getProfile(
                "cn-hangzhou",        // 地域ID
                "<accessKeyId>",      // RAM账号的AccessKey ID
                "<accessKeySecret>"); // RAM账号的AccessKey Secret
        IAcsClient client = new DefaultAcsClient(profile);
        // 设置地域和域名信息（可选）
        String regionId = "cn-hangzhou";
        String product = "r-kvstore";
        String endpoint = "r-kvstore.aliyuncs.com";
        DefaultProfile.addEndpoint(regionId, product, endpoint);

        // 创建API请求并设置参数
        DescribeAccountsRequest request = new DescribeAccountsRequest();
        request.setInstanceId("r-bp1xxxxxxxxxxxx");
        // 发起请求并处理应答或异常
        try {
            DescribeAccountsResponse response = client.getAcsResponse(request);
            System.out.println(response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
