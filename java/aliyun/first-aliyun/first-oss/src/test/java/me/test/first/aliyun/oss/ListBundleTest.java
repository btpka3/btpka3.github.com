package me.test.first.aliyun.oss;

import com.alibaba.fastjson.JSON;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.common.auth.CredentialsProvider;
import com.aliyun.oss.common.auth.CredentialsProviderFactory;
import com.aliyun.oss.model.ListObjectsV2Request;
import com.aliyun.oss.model.ListObjectsV2Result;
import com.aliyun.oss.model.OSSObjectSummary;
import lombok.*;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;

import java.io.FileInputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Objects;

/**
 * @author dangqian.zll
 * @date 2024/6/3
 * @see <a href="https://help.aliyun.com/zh/oss/developer-reference/manage-directories">Java管理目录</a>
 */
public class ListBundleTest {

    String prefix = "oss://bundle-repository/bundleV2/bundle-repo/";
    String bucketName = "bundle-repository";
    String keyPrefix = "bundleV2/bundle-repo/";


    @SneakyThrows
    public OSS getOss() {

        AkSkConf akSkConf = getAkSkConf(" AkSkConf.more_engine.slsRead.json");
        String endpoint = "cn-hangzhou.oss-cdn.aliyun-inc.com";
        String ak = akSkConf.getAccessKeyId();
        String sk = akSkConf.getAccessKeySecret();

        CredentialsProvider credentialsProvider = CredentialsProviderFactory.newDefaultCredentialProvider(ak, sk);

        return new OSSClientBuilder().build(endpoint, credentialsProvider);
    }

    @Test
    public void scanOss() {

        OSS oss = getOss();

        ListObjectsV2Request req  = new ListObjectsV2Request();
        req.setBucketName(bucketName);
        req.setPrefix(keyPrefix);
        ListObjectsV2Result result = oss.listObjectsV2(req);
        List<OSSObjectSummary> ossObjectSummaries = result.getObjectSummaries();
        for (OSSObjectSummary s : ossObjectSummaries) {
            System.out.println("\t" + s.getKey());
        }
    }

    @SneakyThrows
    protected AkSkConf getAkSkConf(String fileName) {
        String akSkConfFile = System.getProperty("user.home") + "/Documents/data/" + fileName;
        String akSkConfJson = IOUtils.toString(new FileInputStream(akSkConfFile), StandardCharsets.UTF_8);
        return JSON.parseObject(akSkConfJson, AkSkConf.class);
    }

    @Data
    @Builder(toBuilder = true)
    @NoArgsConstructor
    @AllArgsConstructor
    public static class BundleInfo {
        private String name;
        private String version;
        private String mavenProfile;
    }

    String toOssPath(BundleInfo info) {

        String name = info.getName();
        String version = info.getVersion();
        String mavenProfile = info.getMavenProfile();
        if (StringUtils.isBlank(mavenProfile) || Objects.equals("default", mavenProfile)) {
            return prefix + name + "/" + version + "/" + name + "-" + version + ".bundle.jar";
        }
        return prefix + name + "/" + version + "/" + mavenProfile + "/" + name + "-" + version + ".bundle.jar";
    }
}
