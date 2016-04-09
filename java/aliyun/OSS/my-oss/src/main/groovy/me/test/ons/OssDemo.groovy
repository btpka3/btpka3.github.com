package me.test.ons

import com.aliyun.oss.OSSClient
import com.aliyun.oss.model.Bucket
import com.aliyun.oss.model.BucketInfo
import com.aliyun.oss.model.OSSObject
import com.aliyun.oss.model.OSSObjectSummary
import com.aliyun.oss.model.ObjectListing
import com.aliyun.oss.model.PutObjectResult
import groovy.json.JsonOutput
import org.apache.commons.io.IOUtils
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class OssDemo {
    static Logger log = LoggerFactory.getLogger(OssDemo)

    static void main(String[] args) {

        Properties props = new Properties()
        props.load(new FileInputStream(new File(System.getProperty("user.home"), ".aliyun")))

        def endpoint = "oss-cn-hangzhou.aliyuncs.com"
        def accessKeyId = props.accessKeyId
        def accessKeySecret = props.accessKeySecret
        def bucketName = "btpka3"
        def key = "dir/my-first-key"

        OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);

        try {

            if (!ossClient.doesBucketExist(bucketName)) {
                log.error("OSS bucket ${bucketName} not exist.")
                System.exit(1)
            }

            // 查看Bucket信息。详细请参看“SDK手册 > Java-SDK > 管理Bucket”。
            // 链接地址是：https://help.aliyun.com/document_detail/oss/sdk/java-sdk/manage_bucket.html?spm=5176.docoss/sdk/java-sdk/init
            BucketInfo info = ossClient.getBucketInfo(bucketName);

            Bucket bucket = info.getBucket()

            log.info("""Bucket '${bucketName}' :
    location        : ${bucket.location}
    creationDate    : ${bucket.creationDate}
    owner           : ${bucket.owner}
""")

            // upload
            InputStream is = new ByteArrayInputStream("Hello OSS".getBytes());
            PutObjectResult putObjectResult = ossClient.putObject(bucketName, key, is);
            log.info "upload OK : ${JsonOutput.toJson(putObjectResult)}"

            // download
            OSSObject ossObject = ossClient.getObject(bucketName, key);
            InputStream inputStream = ossObject.getObjectContent();
            String strContent = IOUtils.toString(inputStream);
            log.info "download OK : ${strContent}"

            // list
            ObjectListing objectListing = ossClient.listObjects(bucketName);
            List<OSSObjectSummary> objectSummaries = objectListing.getObjectSummaries();
            log.info "list OK : ${JsonOutput.toJson(objectSummaries.findResult { it.key })}"

            // delete
            // ossClient.deleteObject(bucketName, key);
            // log.info "delete OK"

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ossClient.shutdown();
        }

    }
}
