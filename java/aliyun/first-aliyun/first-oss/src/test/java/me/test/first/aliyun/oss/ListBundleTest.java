package me.test.first.aliyun.oss;

import com.aliyun.core.utils.DateUtil;
import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.common.auth.CredentialsProvider;
import com.aliyun.oss.common.auth.CredentialsProviderFactory;
import com.aliyun.oss.common.utils.BinaryUtil;
import com.aliyun.oss.model.*;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * @author dangqian.zll
 * @date 2024/6/3
 * @see <a href="https://help.aliyun.com/zh/oss/developer-reference/manage-directories">Java管理目录</a>
 */
public class ListBundleTest {

    String bucketName = "aigc-mtee-resource-center";
    String keyPrefix = "mtee3_data/resource/appitem/all/";
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

        req.setAdditionalHeaderNames(new HashSet<>(Arrays.asList(clientHostNameKey, clientIpKey)));

        ListObjectsV2Result result = oss.listObjectsV2(req);
        List<OSSObjectSummary> ossObjectSummaries = result.getObjectSummaries();
        for (OSSObjectSummary s : ossObjectSummaries) {
            System.out.println("\t" + s.getKey());
        }
        System.out.println("Done.");
    }

    public void downloadFile() {
        OSS oss = getOss();
        String objectName = null;
        File localFile = null;
        try {
            // 下载Object到本地文件，并保存到指定的本地路径中。如果指定的本地文件存在会覆盖，不存在则新建。
            // 如果未指定本地路径，则下载后的文件默认保存到示例程序所属项目对应本地路径中。
            oss.getObject(new GetObjectRequest(bucketName, objectName), localFile);
        } catch (OSSException oe) {
            System.out.println("Caught an OSSException, which means your request made it to OSS, "
                    + "but was rejected with an error response for some reason.");
            System.out.println("Error Message:" + oe.getErrorMessage());
            System.out.println("Error Code:" + oe.getErrorCode());
            System.out.println("Request ID:" + oe.getRequestId());
            System.out.println("Host ID:" + oe.getHostId());
        } catch (ClientException ce) {
            System.out.println("Caught an ClientException, which means the client encountered "
                    + "a serious internal problem while trying to communicate with OSS, "
                    + "such as not being able to access the network.");
            System.out.println("Error Message:" + ce.getMessage());
        }
    }

    /**
     * 设置生命周期规则【bucket级别】
     */
    public void autoExpireByBucket() {
        OSS oss = getOss();
        // 设置生命周期规则
        SetBucketLifecycleRequest request = new SetBucketLifecycleRequest(bucketName);

        // 创建规则：上传后30天自动删除
        LifecycleRule rule = new LifecycleRule();
        rule.setId("delete-after-30-days");
        rule.setStatus(LifecycleRule.RuleStatus.Enabled);

        // 设置过期时间（相对时间）
        rule.setExpirationDays(30);

        // 或者设置具体的过期日期
        // rule.setExpirationDate(new Date("2026-12-31"));

        request.AddLifecycleRule(rule);
        VoidResult result = oss.setBucketLifecycle(request);
    }

    /**
     * 设置生命周期规则【基于最后一次访问时间的生命周期规则】
     *
     * @see <a href="https://help.aliyun.com/zh/oss/user-guide/lifecycle-rules-based-on-the-last-access-time">基于最后一次访问时间的生命周期规则</a>
     */
    public void autoExpireByLastAccess() {
        OSS oss = getOss();

        VoidResult result0 = oss.putBucketAccessMonitor(bucketName, AccessMonitor.AccessMonitorStatus.Enabled.toString());


        // 指定生命周期规则1。规则中指定前缀为logs，且小于等于64 KB的所有文件在距离最后一次访问时间30天后转为低频访问类型。且再次访问前缀为logs的文件时，这些文件仍保留为低频访问类型。
        LifecycleRule rule = new LifecycleRule();
        rule.setId("rule1");
        rule.setPrefix("logs");
        rule.setStatus(LifecycleRule.RuleStatus.Enabled);

        LifecycleRule.StorageTransition storageTransition = new LifecycleRule.StorageTransition();
        storageTransition.setStorageClass(StorageClass.IA);
        storageTransition.setExpirationDays(30);
        storageTransition.setIsAccessTime(true);
        storageTransition.setReturnToStdWhenVisit(false);
        storageTransition.setAllowSmallFile(true);
        List<LifecycleRule.StorageTransition> storageTransitionList = new ArrayList<LifecycleRule.StorageTransition>();
        storageTransitionList.add(storageTransition);
        rule.setStorageTransition(storageTransitionList);

        // 设置生命周期规则
        SetBucketLifecycleRequest request = new SetBucketLifecycleRequest(bucketName);
        request.AddLifecycleRule(rule);
        VoidResult result = oss.setBucketLifecycle(request);
    }


    @SneakyThrows
    public void uploadWithMeta1() {
        OSS oss = getOss();
        String objectName = "a/b/c.txt";
        String content = "hello";

        ObjectMetadata meta = new ObjectMetadata();
        String md5 = BinaryUtil.toBase64String(BinaryUtil.calculateMd5(content.getBytes(StandardCharsets.UTF_8)));

        // 开启文件内容MD5校验。开启后OSS会把您提供的MD5与文件的MD5比较，不一致则抛出异常。
        meta.setContentMD5(md5);
        // 指定上传的内容类型。内容类型决定浏览器将以什么形式、什么编码读取文件。如果没有指定则根据文件的扩展名生成，如果没有扩展名则为默认值application/octet-stream。
        meta.setContentType("text/plain; charset=utf-8");
        // 设置header，例如设置上传文件的存储类型。
        meta.setHeader("x-oss-storage-class", StorageClass.Standard);

        // ⭕️ 上传时设置单文件的过期时间
        Date expirationDate = new Date(System.currentTimeMillis() + 30L * 24 * 60 * 60 * 1000);
        meta.setExpirationTime(expirationDate);

        // 设置内容被下载时的名称。
        meta.setContentDisposition("attachment; filename=\"DownloadFilename\"");
        // 设置上传文件的长度。如超过此长度，则上传文件会被截断，上传的文件长度为设置的长度。如小于此长度，则为上传文件的实际长度。
        meta.setContentLength(content.length());
        // 设置内容被下载时网页的缓存行为。
        meta.setCacheControl("Download Action");
        // 设置缓存过期时间，格式是格林威治时间（GMT）。
        meta.setExpirationTime(DateUtil.parseIso8601Date("2022-10-12T00:00:00.000Z"));
        // 设置内容被下载时的编码格式。
        meta.setContentEncoding("gzip");


        // 设置自定义元数据。建议使用Base64编码。
        meta.addUserMetadata("key1", Base64.getEncoder().encodeToString("value1".getBytes(StandardCharsets.UTF_8)));
        meta.addUserMetadata("key2", Base64.getEncoder().encodeToString("value2".getBytes(StandardCharsets.UTF_8)));

        PutObjectRequest putObjectRequest = new PutObjectRequest(
                bucketName,
                objectName,
                new ByteArrayInputStream(content.getBytes(StandardCharsets.UTF_8)),
                meta
        );

        // 上传文件。
        PutObjectResult result = oss.putObject(putObjectRequest);
    }

    /**
     * 修改元数据
     */
    public void modifyMeta() {
        OSS oss = getOss();
        // Endpoint以华东1（杭州）为例，其它Region请按实际情况填写。
        String endpoint = "https://oss-cn-hangzhou.aliyuncs.com";
        // 从环境变量中获取访问凭证。运行本代码示例之前，请确保已设置环境变量OSS_ACCESS_KEY_ID和OSS_ACCESS_KEY_SECRET。
        String bucketName = "yourSourceBucketName";
        String ossObjKey = "yourSourceObjectName";

        // 设置源文件与目标文件相同，调用 ossClient.copyObject 方法修改文件元数据。
        CopyObjectRequest request = new CopyObjectRequest(
                bucketName,
                ossObjKey,
                bucketName,
                ossObjKey
        );
        ObjectMetadata meta = new ObjectMetadata();
        // ... 设置元数据
        request.setNewObjectMetadata(meta);
        CopyObjectResult result = oss.copyObject(request);
    }

    /**
     * 获取元数据
     */
    public void getMeta() {
        OSS oss = getOss();
        String bucketName = "yourSourceBucketName";
        String ossObjKey = "yourSourceObjectName";
        // 获取文件的部分元数据。
        {
            SimplifiedObjectMeta objectMeta = oss.getSimplifiedObjectMeta(bucketName, ossObjKey);
            String eTag = objectMeta.getETag();
            long size = objectMeta.getSize();
            Date lastModified = objectMeta.getLastModified();
            String versionId = objectMeta.getVersionId();
        }
        // 获取文件的全部元数据
        {
            ObjectMetadata metadata = oss.getObjectMetadata(bucketName, ossObjKey);
            String eTag = metadata.getETag();
            long size = metadata.getContentLength();
            Date lastModified = metadata.getLastModified();
            String versionId = metadata.getVersionId();
            String contentMD5 = metadata.getContentMD5();
            long contentLength = metadata.getContentLength();
            String objectType = metadata.getObjectType();
            // 自定义元数据
            Map<String, String> userMetadata = metadata.getUserMetadata();

        }
    }
}
