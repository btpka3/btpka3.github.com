package me.test.first.aliyun.oss;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.common.auth.CredentialsProvider;
import com.aliyun.oss.common.auth.CredentialsProviderFactory;
import com.aliyun.oss.model.ListObjectsV2Request;
import com.aliyun.oss.model.ListObjectsV2Result;
import com.aliyun.oss.model.OSSObjectSummary;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

/**
 * @author dangqian.zll
 * @date 2024/6/3
 * @see <a href="https://help.aliyun.com/zh/oss/developer-reference/manage-directories">Java管理目录</a>
 */
public class ListBundleTest {

    String bucketName = "aigc-mtee-resource-center";
    String keyPrefix = "test";
    String endpoint = "oss-accelerate.aliyuncs.com";

    @SneakyThrows
    public OSS getOss() {

        AkSkConf akSkConf = AkSkConf.getAkSkConf("AkSkConf.aigc_yun.aone-mtee3-online.json");

        String ak = akSkConf.getAccessKeyId();
        String sk = akSkConf.getAccessKeySecret();

        CredentialsProvider credentialsProvider = CredentialsProviderFactory.newDefaultCredentialProvider(ak, sk);

        return new OSSClientBuilder().build(endpoint, credentialsProvider);
    }

    @Test
    public void scanOss() {

        OSS oss = getOss();

        ListObjectsV2Request req = new ListObjectsV2Request();
        req.setBucketName(bucketName);
        req.setPrefix(keyPrefix);

        // 基于OSS用户自定义日志字段分析中间链路代理
        // https://help.aliyun.com/zh/oss/use-cases/analysis-of-intermediate-link-agents-based-on-oss-logging-request-headers
        String clientHostNameKey = "x-client-hostname";
        String clientHostNameValue = "aigc-yun-online-fhodc-2ch5b-wtxjzwtnmpqn";
        req.addHeader(clientHostNameKey, clientHostNameValue);

        String clientIpKey = "x-client-ip";
        String clientIpValue = "172.16.8.93";
        req.addHeader(clientIpKey, clientIpValue);

        req.setAdditionalHeaderNames(new HashSet<>(Arrays.asList(clientHostNameKey,clientIpKey)));

        ListObjectsV2Result result = oss.listObjectsV2(req);
        List<OSSObjectSummary> ossObjectSummaries = result.getObjectSummaries();
        for (OSSObjectSummary s : ossObjectSummaries) {
            System.out.println("\t" + s.getKey());
        }
        System.out.println("Done.");
    }
}
