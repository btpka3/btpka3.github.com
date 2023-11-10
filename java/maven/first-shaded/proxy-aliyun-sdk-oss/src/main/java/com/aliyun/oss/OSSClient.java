package com.aliyun.oss;

import com.aliyun.oss.common.auth.Credentials;
import com.aliyun.oss.common.auth.CredentialsProvider;
import com.aliyun.oss.common.comm.ResponseMessage;
import com.aliyun.oss.common.comm.SignVersion;
import com.aliyun.oss.model.*;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author dangqian.zll
 * @date 2023/11/9
 */
public class OSSClient implements OSS {

    private CredentialsProvider c;
    private shaded.com.aliyun.oss.OSSClient o1;
    private io.opentracing.ScopeManager m1;
    private com.google.gson.Gson g;
    private OSS oss = null;

    @Override
    public void switchCredentials(Credentials credentials) {
        oss.switchCredentials(credentials);
    }

    @Override
    public void switchSignatureVersion(SignVersion signVersion) {
        oss.switchSignatureVersion(signVersion);
    }

    @Override
    public void shutdown() {
        oss.shutdown();
    }

    @Override
    public String getConnectionPoolStats() {
        return oss.getConnectionPoolStats();
    }

    @Override
    public Bucket createBucket(String s) throws OSSException, ClientException {
        return oss.createBucket(s);
    }

    @Override
    public Bucket createBucket(CreateBucketRequest createBucketRequest) throws OSSException, ClientException {
        return oss.createBucket(createBucketRequest);
    }

    @Override
    public VoidResult deleteBucket(String s) throws OSSException, ClientException {
        return oss.deleteBucket(s);
    }

    @Override
    public VoidResult deleteBucket(GenericRequest genericRequest) throws OSSException, ClientException {
        return oss.deleteBucket(genericRequest);
    }

    @Override
    public List<Bucket> listBuckets() throws OSSException, ClientException {
        return oss.listBuckets();
    }

    @Override
    public BucketList listBuckets(String s, String s1, Integer integer) throws OSSException, ClientException {
        return oss.listBuckets(s, s1, integer);
    }

    @Override
    public BucketList listBuckets(ListBucketsRequest listBucketsRequest) throws OSSException, ClientException {
        return oss.listBuckets(listBucketsRequest);
    }

    @Override
    public VoidResult setBucketAcl(String s, CannedAccessControlList cannedAccessControlList) throws OSSException, ClientException {
        return oss.setBucketAcl(s, cannedAccessControlList);
    }

    @Override
    public VoidResult setBucketAcl(SetBucketAclRequest setBucketAclRequest) throws OSSException, ClientException {
        return oss.setBucketAcl(setBucketAclRequest);
    }

    @Override
    public AccessControlList getBucketAcl(String s) throws OSSException, ClientException {
        return oss.getBucketAcl(s);
    }

    @Override
    public AccessControlList getBucketAcl(GenericRequest genericRequest) throws OSSException, ClientException {
        return oss.getBucketAcl(genericRequest);
    }

    @Override
    public BucketMetadata getBucketMetadata(String s) throws OSSException, ClientException {
        return oss.getBucketMetadata(s);
    }

    @Override
    public BucketMetadata getBucketMetadata(GenericRequest genericRequest) throws OSSException, ClientException {
        return oss.getBucketMetadata(genericRequest);
    }

    @Override
    public VoidResult setBucketReferer(String s, BucketReferer bucketReferer) throws OSSException, ClientException {
        return oss.setBucketReferer(s, bucketReferer);
    }

    @Override
    public VoidResult setBucketReferer(SetBucketRefererRequest setBucketRefererRequest) throws OSSException, ClientException {
        return oss.setBucketReferer(setBucketRefererRequest);
    }

    @Override
    public BucketReferer getBucketReferer(String s) throws OSSException, ClientException {
        return oss.getBucketReferer(s);
    }

    @Override
    public BucketReferer getBucketReferer(GenericRequest genericRequest) throws OSSException, ClientException {
        return oss.getBucketReferer(genericRequest);
    }

    @Override
    public String getBucketLocation(String s) throws OSSException, ClientException {
        return oss.getBucketLocation(s);
    }

    @Override
    public String getBucketLocation(GenericRequest genericRequest) throws OSSException, ClientException {
        return oss.getBucketLocation(genericRequest);
    }

    @Override
    public VoidResult setBucketTagging(String s, Map<String, String> map) throws OSSException, ClientException {
        return oss.setBucketTagging(s, map);
    }

    @Override
    public VoidResult setBucketTagging(String s, TagSet tagSet) throws OSSException, ClientException {
        return oss.setBucketTagging(s, tagSet);
    }

    @Override
    public VoidResult setBucketTagging(SetBucketTaggingRequest setBucketTaggingRequest) throws OSSException, ClientException {
        return oss.setBucketTagging(setBucketTaggingRequest);
    }

    @Override
    public TagSet getBucketTagging(String s) throws OSSException, ClientException {
        return oss.getBucketTagging(s);
    }

    @Override
    public TagSet getBucketTagging(GenericRequest genericRequest) throws OSSException, ClientException {
        return oss.getBucketTagging(genericRequest);
    }

    @Override
    public VoidResult deleteBucketTagging(String s) throws OSSException, ClientException {
        return oss.deleteBucketTagging(s);
    }

    @Override
    public VoidResult deleteBucketTagging(GenericRequest genericRequest) throws OSSException, ClientException {
        return oss.deleteBucketTagging(genericRequest);
    }

    @Override
    public BucketVersioningConfiguration getBucketVersioning(String s) throws OSSException, ClientException {
        return oss.getBucketVersioning(s);
    }

    @Override
    public BucketVersioningConfiguration getBucketVersioning(GenericRequest genericRequest) throws OSSException, ClientException {
        return oss.getBucketVersioning(genericRequest);
    }

    @Override
    public VoidResult setBucketVersioning(SetBucketVersioningRequest setBucketVersioningRequest) throws OSSException, ClientException {
        return oss.setBucketVersioning(setBucketVersioningRequest);
    }

    @Override
    public boolean doesBucketExist(String s) throws OSSException, ClientException {
        return oss.doesBucketExist(s);
    }

    @Override
    public boolean doesBucketExist(GenericRequest genericRequest) throws OSSException, ClientException {
        return oss.doesBucketExist(genericRequest);
    }

    @Override
    public ObjectListing listObjects(String s) throws OSSException, ClientException {
        return oss.listObjects(s);
    }

    @Override
    public ObjectListing listObjects(String s, String s1) throws OSSException, ClientException {
        return oss.listObjects(s, s1);
    }

    @Override
    public ObjectListing listObjects(ListObjectsRequest listObjectsRequest) throws OSSException, ClientException {
        return oss.listObjects(listObjectsRequest);
    }

    @Override
    public ListObjectsV2Result listObjectsV2(ListObjectsV2Request listObjectsV2Request) throws OSSException, ClientException {
        return oss.listObjectsV2(listObjectsV2Request);
    }

    @Override
    public ListObjectsV2Result listObjectsV2(String s) throws OSSException, ClientException {
        return oss.listObjectsV2(s);
    }

    @Override
    public ListObjectsV2Result listObjectsV2(String s, String s1) throws OSSException, ClientException {
        return oss.listObjectsV2(s, s1);
    }

    @Override
    public ListObjectsV2Result listObjectsV2(String s, String s1, String s2, String s3, String s4, Integer integer, String s5, boolean b) throws OSSException, ClientException {
        return oss.listObjectsV2(s, s1, s2, s3, s4, integer, s5, b);
    }

    @Override
    public VersionListing listVersions(String s, String s1) throws OSSException, ClientException {
        return oss.listVersions(s, s1);
    }

    @Override
    public VersionListing listVersions(String s, String s1, String s2, String s3, String s4, Integer integer) throws OSSException, ClientException {
        return oss.listVersions(s, s1, s2, s3, s4, integer);
    }

    @Override
    public VersionListing listVersions(ListVersionsRequest listVersionsRequest) throws OSSException, ClientException {
        return oss.listVersions(listVersionsRequest);
    }

    @Override
    public PutObjectResult putObject(String s, String s1, InputStream inputStream) throws OSSException, ClientException {
        return oss.putObject(s, s1, inputStream);
    }

    @Override
    public PutObjectResult putObject(String s, String s1, InputStream inputStream, ObjectMetadata objectMetadata) throws OSSException, ClientException {
        return oss.putObject(s, s1, inputStream, objectMetadata);
    }

    @Override
    public PutObjectResult putObject(String s, String s1, File file, ObjectMetadata objectMetadata) throws OSSException, ClientException {
        return oss.putObject(s, s1, file, objectMetadata);
    }

    @Override
    public PutObjectResult putObject(String s, String s1, File file) throws OSSException, ClientException {
        return oss.putObject(s, s1, file);
    }

    @Override
    public PutObjectResult putObject(PutObjectRequest putObjectRequest) throws OSSException, ClientException {
        return oss.putObject(putObjectRequest);
    }

    @Override
    public PutObjectResult putObject(URL url, String s, Map<String, String> map) throws OSSException, ClientException {
        return oss.putObject(url, s, map);
    }

    @Override
    public PutObjectResult putObject(URL url, String s, Map<String, String> map, boolean b) throws OSSException, ClientException {
        return oss.putObject(url, s, map, b);
    }

    @Override
    public PutObjectResult putObject(URL url, InputStream inputStream, long l, Map<String, String> map) throws OSSException, ClientException {
        return oss.putObject(url, inputStream, l, map);
    }

    @Override
    public PutObjectResult putObject(URL url, InputStream inputStream, long l, Map<String, String> map, boolean b) throws OSSException, ClientException {
        return oss.putObject(url, inputStream, l, map, b);
    }

    @Override
    public CopyObjectResult copyObject(String s, String s1, String s2, String s3) throws OSSException, ClientException {
        return oss.copyObject(s, s1, s2, s3);
    }

    @Override
    public CopyObjectResult copyObject(CopyObjectRequest copyObjectRequest) throws OSSException, ClientException {
        return oss.copyObject(copyObjectRequest);
    }

    @Override
    public OSSObject getObject(String s, String s1) throws OSSException, ClientException {
        return oss.getObject(s, s1);
    }

    @Override
    public ObjectMetadata getObject(GetObjectRequest getObjectRequest, File file) throws OSSException, ClientException {
        return oss.getObject(getObjectRequest, file);
    }

    @Override
    public OSSObject getObject(GetObjectRequest getObjectRequest) throws OSSException, ClientException {
        return oss.getObject(getObjectRequest);
    }

    @Override
    public OSSObject selectObject(SelectObjectRequest selectObjectRequest) throws OSSException, ClientException {
        return oss.selectObject(selectObjectRequest);
    }

    @Override
    public OSSObject getObject(URL url, Map<String, String> map) throws OSSException, ClientException {
        return oss.getObject(url, map);
    }

    @Override
    public SimplifiedObjectMeta getSimplifiedObjectMeta(String s, String s1) throws OSSException, ClientException {
        return oss.getSimplifiedObjectMeta(s, s1);
    }

    @Override
    public SimplifiedObjectMeta getSimplifiedObjectMeta(GenericRequest genericRequest) throws OSSException, ClientException {
        return oss.getSimplifiedObjectMeta(genericRequest);
    }

    @Override
    public ObjectMetadata getObjectMetadata(String s, String s1) throws OSSException, ClientException {
        return oss.getObjectMetadata(s, s1);
    }

    @Override
    public ObjectMetadata getObjectMetadata(GenericRequest genericRequest) throws OSSException, ClientException {
        return oss.getObjectMetadata(genericRequest);
    }

    @Override
    public SelectObjectMetadata createSelectObjectMetadata(CreateSelectObjectMetadataRequest createSelectObjectMetadataRequest) throws OSSException, ClientException {
        return oss.createSelectObjectMetadata(createSelectObjectMetadataRequest);
    }

    @Override
    public ObjectMetadata headObject(String s, String s1) throws OSSException, ClientException {
        return oss.headObject(s, s1);
    }

    @Override
    public ObjectMetadata headObject(HeadObjectRequest headObjectRequest) throws OSSException, ClientException {
        return oss.headObject(headObjectRequest);
    }

    @Override
    public AppendObjectResult appendObject(AppendObjectRequest appendObjectRequest) throws OSSException, ClientException {
        return oss.appendObject(appendObjectRequest);
    }

    @Override
    public VoidResult deleteObject(String s, String s1) throws OSSException, ClientException {
        return oss.deleteObject(s, s1);
    }

    @Override
    public VoidResult deleteObject(GenericRequest genericRequest) throws OSSException, ClientException {
        return oss.deleteObject(genericRequest);
    }

    @Override
    public DeleteObjectsResult deleteObjects(DeleteObjectsRequest deleteObjectsRequest) throws OSSException, ClientException {
        return oss.deleteObjects(deleteObjectsRequest);
    }

    @Override
    public VoidResult deleteVersion(String s, String s1, String s2) throws OSSException, ClientException {
        return oss.deleteVersion(s, s1, s2);
    }

    @Override
    public VoidResult deleteVersion(DeleteVersionRequest deleteVersionRequest) throws OSSException, ClientException {
        return oss.deleteVersion(deleteVersionRequest);
    }

    @Override
    public DeleteVersionsResult deleteVersions(DeleteVersionsRequest deleteVersionsRequest) throws OSSException, ClientException {
        return oss.deleteVersions(deleteVersionsRequest);
    }

    @Override
    public boolean doesObjectExist(String s, String s1) throws OSSException, ClientException {
        return oss.doesObjectExist(s, s1);
    }

    @Override
    public boolean doesObjectExist(GenericRequest genericRequest) throws OSSException, ClientException {
        return oss.doesObjectExist(genericRequest);
    }

    @Override
    public boolean doesObjectExist(String s, String s1, boolean b) {
        return oss.doesObjectExist(s, s1, b);
    }

    @Override
    public boolean doesObjectExist(GenericRequest genericRequest, boolean b) throws OSSException, ClientException {
        return oss.doesObjectExist(genericRequest, b);
    }

    @Override
    @Deprecated
    public boolean doesObjectExist(HeadObjectRequest headObjectRequest) throws OSSException, ClientException {
        return oss.doesObjectExist(headObjectRequest);
    }

    @Override
    public VoidResult setObjectAcl(String s, String s1, CannedAccessControlList cannedAccessControlList) throws OSSException, ClientException {
        return oss.setObjectAcl(s, s1, cannedAccessControlList);
    }

    @Override
    public VoidResult setObjectAcl(SetObjectAclRequest setObjectAclRequest) throws OSSException, ClientException {
        return oss.setObjectAcl(setObjectAclRequest);
    }

    @Override
    public ObjectAcl getObjectAcl(String s, String s1) throws OSSException, ClientException {
        return oss.getObjectAcl(s, s1);
    }

    @Override
    public ObjectAcl getObjectAcl(GenericRequest genericRequest) throws OSSException, ClientException {
        return oss.getObjectAcl(genericRequest);
    }

    @Override
    public RestoreObjectResult restoreObject(String s, String s1) throws OSSException, ClientException {
        return oss.restoreObject(s, s1);
    }

    @Override
    public RestoreObjectResult restoreObject(GenericRequest genericRequest) throws OSSException, ClientException {
        return oss.restoreObject(genericRequest);
    }

    @Override
    public RestoreObjectResult restoreObject(String s, String s1, RestoreConfiguration restoreConfiguration) throws OSSException, ClientException {
        return oss.restoreObject(s, s1, restoreConfiguration);
    }

    @Override
    public RestoreObjectResult restoreObject(RestoreObjectRequest restoreObjectRequest) throws OSSException, ClientException {
        return oss.restoreObject(restoreObjectRequest);
    }

    @Override
    public VoidResult setObjectTagging(String s, String s1, Map<String, String> map) throws OSSException, ClientException {
        return oss.setObjectTagging(s, s1, map);
    }

    @Override
    public VoidResult setObjectTagging(String s, String s1, TagSet tagSet) throws OSSException, ClientException {
        return oss.setObjectTagging(s, s1, tagSet);
    }

    @Override
    public VoidResult setObjectTagging(SetObjectTaggingRequest setObjectTaggingRequest) throws OSSException, ClientException {
        return oss.setObjectTagging(setObjectTaggingRequest);
    }

    @Override
    public TagSet getObjectTagging(String s, String s1) throws OSSException, ClientException {
        return oss.getObjectTagging(s, s1);
    }

    @Override
    public TagSet getObjectTagging(GenericRequest genericRequest) throws OSSException, ClientException {
        return oss.getObjectTagging(genericRequest);
    }

    @Override
    public VoidResult deleteObjectTagging(String s, String s1) throws OSSException, ClientException {
        return oss.deleteObjectTagging(s, s1);
    }

    @Override
    public VoidResult deleteObjectTagging(GenericRequest genericRequest) throws OSSException, ClientException {
        return oss.deleteObjectTagging(genericRequest);
    }

    @Override
    public URL generatePresignedUrl(String s, String s1, Date date) throws ClientException {
        return oss.generatePresignedUrl(s, s1, date);
    }

    @Override
    public URL generatePresignedUrl(String s, String s1, Date date, HttpMethod httpMethod) throws ClientException {
        return oss.generatePresignedUrl(s, s1, date, httpMethod);
    }

    @Override
    public URL generatePresignedUrl(GeneratePresignedUrlRequest generatePresignedUrlRequest) throws ClientException {
        return oss.generatePresignedUrl(generatePresignedUrlRequest);
    }

    @Override
    public VoidResult putBucketImage(PutBucketImageRequest putBucketImageRequest) throws OSSException, ClientException {
        return oss.putBucketImage(putBucketImageRequest);
    }

    @Override
    public GetBucketImageResult getBucketImage(String s) throws OSSException, ClientException {
        return oss.getBucketImage(s);
    }

    @Override
    public GetBucketImageResult getBucketImage(String s, GenericRequest genericRequest) throws OSSException, ClientException {
        return oss.getBucketImage(s, genericRequest);
    }

    @Override
    public VoidResult deleteBucketImage(String s) throws OSSException, ClientException {
        return oss.deleteBucketImage(s);
    }

    @Override
    public VoidResult deleteBucketImage(String s, GenericRequest genericRequest) throws OSSException, ClientException {
        return oss.deleteBucketImage(s, genericRequest);
    }

    @Override
    public VoidResult deleteImageStyle(String s, String s1) throws OSSException, ClientException {
        return oss.deleteImageStyle(s, s1);
    }

    @Override
    public VoidResult deleteImageStyle(String s, String s1, GenericRequest genericRequest) throws OSSException, ClientException {
        return oss.deleteImageStyle(s, s1, genericRequest);
    }

    @Override
    public VoidResult putImageStyle(PutImageStyleRequest putImageStyleRequest) throws OSSException, ClientException {
        return oss.putImageStyle(putImageStyleRequest);
    }

    @Override
    public GetImageStyleResult getImageStyle(String s, String s1) throws OSSException, ClientException {
        return oss.getImageStyle(s, s1);
    }

    @Override
    public GetImageStyleResult getImageStyle(String s, String s1, GenericRequest genericRequest) throws OSSException, ClientException {
        return oss.getImageStyle(s, s1, genericRequest);
    }

    @Override
    public List<Style> listImageStyle(String s) throws OSSException, ClientException {
        return oss.listImageStyle(s);
    }

    @Override
    public List<Style> listImageStyle(String s, GenericRequest genericRequest) throws OSSException, ClientException {
        return oss.listImageStyle(s, genericRequest);
    }

    @Override
    public VoidResult setBucketProcess(SetBucketProcessRequest setBucketProcessRequest) throws OSSException, ClientException {
        return oss.setBucketProcess(setBucketProcessRequest);
    }

    @Override
    public BucketProcess getBucketProcess(String s) throws OSSException, ClientException {
        return oss.getBucketProcess(s);
    }

    @Override
    public BucketProcess getBucketProcess(GenericRequest genericRequest) throws OSSException, ClientException {
        return oss.getBucketProcess(genericRequest);
    }

    @Override
    public InitiateMultipartUploadResult initiateMultipartUpload(InitiateMultipartUploadRequest initiateMultipartUploadRequest) throws OSSException, ClientException {
        return oss.initiateMultipartUpload(initiateMultipartUploadRequest);
    }

    @Override
    public MultipartUploadListing listMultipartUploads(ListMultipartUploadsRequest listMultipartUploadsRequest) throws OSSException, ClientException {
        return oss.listMultipartUploads(listMultipartUploadsRequest);
    }

    @Override
    public PartListing listParts(ListPartsRequest listPartsRequest) throws OSSException, ClientException {
        return oss.listParts(listPartsRequest);
    }

    @Override
    public UploadPartResult uploadPart(UploadPartRequest uploadPartRequest) throws OSSException, ClientException {
        return oss.uploadPart(uploadPartRequest);
    }

    @Override
    public UploadPartCopyResult uploadPartCopy(UploadPartCopyRequest uploadPartCopyRequest) throws OSSException, ClientException {
        return oss.uploadPartCopy(uploadPartCopyRequest);
    }

    @Override
    public VoidResult abortMultipartUpload(AbortMultipartUploadRequest abortMultipartUploadRequest) throws OSSException, ClientException {
        return oss.abortMultipartUpload(abortMultipartUploadRequest);
    }

    @Override
    public CompleteMultipartUploadResult completeMultipartUpload(CompleteMultipartUploadRequest completeMultipartUploadRequest) throws OSSException, ClientException {
        return oss.completeMultipartUpload(completeMultipartUploadRequest);
    }

    @Override
    public VoidResult setBucketCORS(SetBucketCORSRequest setBucketCORSRequest) throws OSSException, ClientException {
        return oss.setBucketCORS(setBucketCORSRequest);
    }

    @Override
    public List<SetBucketCORSRequest.CORSRule> getBucketCORSRules(String s) throws OSSException, ClientException {
        return oss.getBucketCORSRules(s);
    }

    @Override
    public List<SetBucketCORSRequest.CORSRule> getBucketCORSRules(GenericRequest genericRequest) throws OSSException, ClientException {
        return oss.getBucketCORSRules(genericRequest);
    }

    @Override
    public CORSConfiguration getBucketCORS(GenericRequest genericRequest) throws OSSException, ClientException {
        return oss.getBucketCORS(genericRequest);
    }

    @Override
    public VoidResult deleteBucketCORSRules(String s) throws OSSException, ClientException {
        return oss.deleteBucketCORSRules(s);
    }

    @Override
    public VoidResult deleteBucketCORSRules(GenericRequest genericRequest) throws OSSException, ClientException {
        return oss.deleteBucketCORSRules(genericRequest);
    }

    @Override
    @Deprecated
    public ResponseMessage optionsObject(OptionsRequest optionsRequest) throws OSSException, ClientException {
        return oss.optionsObject(optionsRequest);
    }

    @Override
    public VoidResult setBucketLogging(SetBucketLoggingRequest setBucketLoggingRequest) throws OSSException, ClientException {
        return oss.setBucketLogging(setBucketLoggingRequest);
    }

    @Override
    public BucketLoggingResult getBucketLogging(String s) throws OSSException, ClientException {
        return oss.getBucketLogging(s);
    }

    @Override
    public BucketLoggingResult getBucketLogging(GenericRequest genericRequest) throws OSSException, ClientException {
        return oss.getBucketLogging(genericRequest);
    }

    @Override
    public VoidResult deleteBucketLogging(String s) throws OSSException, ClientException {
        return oss.deleteBucketLogging(s);
    }

    @Override
    public VoidResult deleteBucketLogging(GenericRequest genericRequest) throws OSSException, ClientException {
        return oss.deleteBucketLogging(genericRequest);
    }

    @Override
    public VoidResult setBucketWebsite(SetBucketWebsiteRequest setBucketWebsiteRequest) throws OSSException, ClientException {
        return oss.setBucketWebsite(setBucketWebsiteRequest);
    }

    @Override
    public BucketWebsiteResult getBucketWebsite(String s) throws OSSException, ClientException {
        return oss.getBucketWebsite(s);
    }

    @Override
    public BucketWebsiteResult getBucketWebsite(GenericRequest genericRequest) throws OSSException, ClientException {
        return oss.getBucketWebsite(genericRequest);
    }

    @Override
    public VoidResult deleteBucketWebsite(String s) throws OSSException, ClientException {
        return oss.deleteBucketWebsite(s);
    }

    @Override
    public VoidResult deleteBucketWebsite(GenericRequest genericRequest) throws OSSException, ClientException {
        return oss.deleteBucketWebsite(genericRequest);
    }

    @Override
    public String generatePostPolicy(Date date, PolicyConditions policyConditions) throws ClientException {
        return oss.generatePostPolicy(date, policyConditions);
    }

    @Override
    public String calculatePostSignature(String s) {
        return oss.calculatePostSignature(s);
    }

    @Override
    public VoidResult setBucketLifecycle(SetBucketLifecycleRequest setBucketLifecycleRequest) throws OSSException, ClientException {
        return oss.setBucketLifecycle(setBucketLifecycleRequest);
    }

    @Override
    public List<LifecycleRule> getBucketLifecycle(String s) throws OSSException, ClientException {
        return oss.getBucketLifecycle(s);
    }

    @Override
    public List<LifecycleRule> getBucketLifecycle(GenericRequest genericRequest) throws OSSException, ClientException {
        return oss.getBucketLifecycle(genericRequest);
    }

    @Override
    public VoidResult deleteBucketLifecycle(String s) throws OSSException, ClientException {
        return oss.deleteBucketLifecycle(s);
    }

    @Override
    public VoidResult deleteBucketLifecycle(GenericRequest genericRequest) throws OSSException, ClientException {
        return oss.deleteBucketLifecycle(genericRequest);
    }

    @Override
    public VoidResult addBucketReplication(AddBucketReplicationRequest addBucketReplicationRequest) throws OSSException, ClientException {
        return oss.addBucketReplication(addBucketReplicationRequest);
    }

    @Override
    public List<ReplicationRule> getBucketReplication(String s) throws OSSException, ClientException {
        return oss.getBucketReplication(s);
    }

    @Override
    public List<ReplicationRule> getBucketReplication(GenericRequest genericRequest) throws OSSException, ClientException {
        return oss.getBucketReplication(genericRequest);
    }

    @Override
    public VoidResult deleteBucketReplication(String s, String s1) throws OSSException, ClientException {
        return oss.deleteBucketReplication(s, s1);
    }

    @Override
    public VoidResult deleteBucketReplication(DeleteBucketReplicationRequest deleteBucketReplicationRequest) throws OSSException, ClientException {
        return oss.deleteBucketReplication(deleteBucketReplicationRequest);
    }

    @Override
    public BucketReplicationProgress getBucketReplicationProgress(String s, String s1) throws OSSException, ClientException {
        return oss.getBucketReplicationProgress(s, s1);
    }

    @Override
    public BucketReplicationProgress getBucketReplicationProgress(GetBucketReplicationProgressRequest getBucketReplicationProgressRequest) throws OSSException, ClientException {
        return oss.getBucketReplicationProgress(getBucketReplicationProgressRequest);
    }

    @Override
    public List<String> getBucketReplicationLocation(String s) throws OSSException, ClientException {
        return oss.getBucketReplicationLocation(s);
    }

    @Override
    public List<String> getBucketReplicationLocation(GenericRequest genericRequest) throws OSSException, ClientException {
        return oss.getBucketReplicationLocation(genericRequest);
    }

    @Override
    public AddBucketCnameResult addBucketCname(AddBucketCnameRequest addBucketCnameRequest) throws OSSException, ClientException {
        return oss.addBucketCname(addBucketCnameRequest);
    }

    @Override
    public List<CnameConfiguration> getBucketCname(String s) throws OSSException, ClientException {
        return oss.getBucketCname(s);
    }

    @Override
    public List<CnameConfiguration> getBucketCname(GenericRequest genericRequest) throws OSSException, ClientException {
        return oss.getBucketCname(genericRequest);
    }

    @Override
    public VoidResult deleteBucketCname(String s, String s1) throws OSSException, ClientException {
        return oss.deleteBucketCname(s, s1);
    }

    @Override
    public VoidResult deleteBucketCname(DeleteBucketCnameRequest deleteBucketCnameRequest) throws OSSException, ClientException {
        return oss.deleteBucketCname(deleteBucketCnameRequest);
    }

    @Override
    public CreateBucketCnameTokenResult createBucketCnameToken(CreateBucketCnameTokenRequest createBucketCnameTokenRequest) throws OSSException, ClientException {
        return oss.createBucketCnameToken(createBucketCnameTokenRequest);
    }

    @Override
    public GetBucketCnameTokenResult getBucketCnameToken(GetBucketCnameTokenRequest getBucketCnameTokenRequest) throws OSSException, ClientException {
        return oss.getBucketCnameToken(getBucketCnameTokenRequest);
    }

    @Override
    public BucketInfo getBucketInfo(String s) throws OSSException, ClientException {
        return oss.getBucketInfo(s);
    }

    @Override
    public BucketInfo getBucketInfo(GenericRequest genericRequest) throws OSSException, ClientException {
        return oss.getBucketInfo(genericRequest);
    }

    @Override
    public BucketStat getBucketStat(String s) throws OSSException, ClientException {
        return oss.getBucketStat(s);
    }

    @Override
    public BucketStat getBucketStat(GenericRequest genericRequest) throws OSSException, ClientException {
        return oss.getBucketStat(genericRequest);
    }

    @Override
    public VoidResult setBucketStorageCapacity(String s, UserQos userQos) throws OSSException, ClientException {
        return oss.setBucketStorageCapacity(s, userQos);
    }

    @Override
    public VoidResult setBucketStorageCapacity(SetBucketStorageCapacityRequest setBucketStorageCapacityRequest) throws OSSException, ClientException {
        return oss.setBucketStorageCapacity(setBucketStorageCapacityRequest);
    }

    @Override
    public UserQos getBucketStorageCapacity(String s) throws OSSException, ClientException {
        return oss.getBucketStorageCapacity(s);
    }

    @Override
    public UserQos getBucketStorageCapacity(GenericRequest genericRequest) throws OSSException, ClientException {
        return oss.getBucketStorageCapacity(genericRequest);
    }

    @Override
    public VoidResult setBucketEncryption(SetBucketEncryptionRequest setBucketEncryptionRequest) throws OSSException, ClientException {
        return oss.setBucketEncryption(setBucketEncryptionRequest);
    }

    @Override
    public ServerSideEncryptionConfiguration getBucketEncryption(String s) throws OSSException, ClientException {
        return oss.getBucketEncryption(s);
    }

    @Override
    public ServerSideEncryptionConfiguration getBucketEncryption(GenericRequest genericRequest) throws OSSException, ClientException {
        return oss.getBucketEncryption(genericRequest);
    }

    @Override
    public VoidResult deleteBucketEncryption(String s) throws OSSException, ClientException {
        return oss.deleteBucketEncryption(s);
    }

    @Override
    public VoidResult deleteBucketEncryption(GenericRequest genericRequest) throws OSSException, ClientException {
        return oss.deleteBucketEncryption(genericRequest);
    }

    @Override
    public VoidResult setBucketPolicy(String s, String s1) throws OSSException, ClientException {
        return oss.setBucketPolicy(s, s1);
    }

    @Override
    public VoidResult setBucketPolicy(SetBucketPolicyRequest setBucketPolicyRequest) throws OSSException, ClientException {
        return oss.setBucketPolicy(setBucketPolicyRequest);
    }

    @Override
    public GetBucketPolicyResult getBucketPolicy(GenericRequest genericRequest) throws OSSException, ClientException {
        return oss.getBucketPolicy(genericRequest);
    }

    @Override
    public GetBucketPolicyResult getBucketPolicy(String s) throws OSSException, ClientException {
        return oss.getBucketPolicy(s);
    }

    @Override
    public VoidResult deleteBucketPolicy(GenericRequest genericRequest) throws OSSException, ClientException {
        return oss.deleteBucketPolicy(genericRequest);
    }

    @Override
    public VoidResult deleteBucketPolicy(String s) throws OSSException, ClientException {
        return oss.deleteBucketPolicy(s);
    }

    @Override
    public UploadFileResult uploadFile(UploadFileRequest uploadFileRequest) throws Throwable {
        return oss.uploadFile(uploadFileRequest);
    }

    @Override
    public DownloadFileResult downloadFile(DownloadFileRequest downloadFileRequest) throws Throwable {
        return oss.downloadFile(downloadFileRequest);
    }

    @Override
    public CreateLiveChannelResult createLiveChannel(CreateLiveChannelRequest createLiveChannelRequest) throws OSSException, ClientException {
        return oss.createLiveChannel(createLiveChannelRequest);
    }

    @Override
    public VoidResult setLiveChannelStatus(String s, String s1, LiveChannelStatus liveChannelStatus) throws OSSException, ClientException {
        return oss.setLiveChannelStatus(s, s1, liveChannelStatus);
    }

    @Override
    public VoidResult setLiveChannelStatus(SetLiveChannelRequest setLiveChannelRequest) throws OSSException, ClientException {
        return oss.setLiveChannelStatus(setLiveChannelRequest);
    }

    @Override
    public LiveChannelInfo getLiveChannelInfo(String s, String s1) throws OSSException, ClientException {
        return oss.getLiveChannelInfo(s, s1);
    }

    @Override
    public LiveChannelInfo getLiveChannelInfo(LiveChannelGenericRequest liveChannelGenericRequest) throws OSSException, ClientException {
        return oss.getLiveChannelInfo(liveChannelGenericRequest);
    }

    @Override
    public LiveChannelStat getLiveChannelStat(String s, String s1) throws OSSException, ClientException {
        return oss.getLiveChannelStat(s, s1);
    }

    @Override
    public LiveChannelStat getLiveChannelStat(LiveChannelGenericRequest liveChannelGenericRequest) throws OSSException, ClientException {
        return oss.getLiveChannelStat(liveChannelGenericRequest);
    }

    @Override
    public VoidResult deleteLiveChannel(String s, String s1) throws OSSException, ClientException {
        return oss.deleteLiveChannel(s, s1);
    }

    @Override
    public VoidResult deleteLiveChannel(LiveChannelGenericRequest liveChannelGenericRequest) throws OSSException, ClientException {
        return oss.deleteLiveChannel(liveChannelGenericRequest);
    }

    @Override
    public List<LiveChannel> listLiveChannels(String s) throws OSSException, ClientException {
        return oss.listLiveChannels(s);
    }

    @Override
    public LiveChannelListing listLiveChannels(ListLiveChannelsRequest listLiveChannelsRequest) throws OSSException, ClientException {
        return oss.listLiveChannels(listLiveChannelsRequest);
    }

    @Override
    public List<LiveRecord> getLiveChannelHistory(String s, String s1) throws OSSException, ClientException {
        return oss.getLiveChannelHistory(s, s1);
    }

    @Override
    public List<LiveRecord> getLiveChannelHistory(LiveChannelGenericRequest liveChannelGenericRequest) throws OSSException, ClientException {
        return oss.getLiveChannelHistory(liveChannelGenericRequest);
    }

    @Override
    public VoidResult generateVodPlaylist(String s, String s1, String s2, long l, long l1) throws OSSException, ClientException {
        return oss.generateVodPlaylist(s, s1, s2, l, l1);
    }

    @Override
    public VoidResult generateVodPlaylist(GenerateVodPlaylistRequest generateVodPlaylistRequest) throws OSSException, ClientException {
        return oss.generateVodPlaylist(generateVodPlaylistRequest);
    }

    @Override
    public OSSObject getVodPlaylist(String s, String s1, long l, long l1) throws OSSException, ClientException {
        return oss.getVodPlaylist(s, s1, l, l1);
    }

    @Override
    public OSSObject getVodPlaylist(GetVodPlaylistRequest getVodPlaylistRequest) throws OSSException, ClientException {
        return oss.getVodPlaylist(getVodPlaylistRequest);
    }

    @Override
    public String generateRtmpUri(String s, String s1, String s2, long l) throws OSSException, ClientException {
        return oss.generateRtmpUri(s, s1, s2, l);
    }

    @Override
    public String generateRtmpUri(GenerateRtmpUriRequest generateRtmpUriRequest) throws OSSException, ClientException {
        return oss.generateRtmpUri(generateRtmpUriRequest);
    }

    @Override
    public VoidResult createSymlink(String s, String s1, String s2) throws OSSException, ClientException {
        return oss.createSymlink(s, s1, s2);
    }

    @Override
    public VoidResult createSymlink(CreateSymlinkRequest createSymlinkRequest) throws OSSException, ClientException {
        return oss.createSymlink(createSymlinkRequest);
    }

    @Override
    public OSSSymlink getSymlink(String s, String s1) throws OSSException, ClientException {
        return oss.getSymlink(s, s1);
    }

    @Override
    public OSSSymlink getSymlink(GenericRequest genericRequest) throws OSSException, ClientException {
        return oss.getSymlink(genericRequest);
    }

    @Override
    public GenericResult processObject(ProcessObjectRequest processObjectRequest) throws OSSException, ClientException {
        return oss.processObject(processObjectRequest);
    }

    @Override
    public VoidResult setBucketRequestPayment(String s, Payer payer) throws OSSException, ClientException {
        return oss.setBucketRequestPayment(s, payer);
    }

    @Override
    public VoidResult setBucketRequestPayment(SetBucketRequestPaymentRequest setBucketRequestPaymentRequest) throws OSSException, ClientException {
        return oss.setBucketRequestPayment(setBucketRequestPaymentRequest);
    }

    @Override
    public GetBucketRequestPaymentResult getBucketRequestPayment(String s) throws OSSException, ClientException {
        return oss.getBucketRequestPayment(s);
    }

    @Override
    public GetBucketRequestPaymentResult getBucketRequestPayment(GenericRequest genericRequest) throws OSSException, ClientException {
        return oss.getBucketRequestPayment(genericRequest);
    }

    @Override
    public VoidResult setBucketQosInfo(String s, BucketQosInfo bucketQosInfo) throws OSSException, ClientException {
        return oss.setBucketQosInfo(s, bucketQosInfo);
    }

    @Override
    public VoidResult setBucketQosInfo(SetBucketQosInfoRequest setBucketQosInfoRequest) throws OSSException, ClientException {
        return oss.setBucketQosInfo(setBucketQosInfoRequest);
    }

    @Override
    public BucketQosInfo getBucketQosInfo(String s) throws OSSException, ClientException {
        return oss.getBucketQosInfo(s);
    }

    @Override
    public BucketQosInfo getBucketQosInfo(GenericRequest genericRequest) throws OSSException, ClientException {
        return oss.getBucketQosInfo(genericRequest);
    }

    @Override
    public VoidResult deleteBucketQosInfo(String s) throws OSSException, ClientException {
        return oss.deleteBucketQosInfo(s);
    }

    @Override
    public VoidResult deleteBucketQosInfo(GenericRequest genericRequest) throws OSSException, ClientException {
        return oss.deleteBucketQosInfo(genericRequest);
    }

    @Override
    public UserQosInfo getUserQosInfo() throws OSSException, ClientException {
        return oss.getUserQosInfo();
    }

    @Override
    public SetAsyncFetchTaskResult setAsyncFetchTask(String s, AsyncFetchTaskConfiguration asyncFetchTaskConfiguration) throws OSSException, ClientException {
        return oss.setAsyncFetchTask(s, asyncFetchTaskConfiguration);
    }

    @Override
    public SetAsyncFetchTaskResult setAsyncFetchTask(SetAsyncFetchTaskRequest setAsyncFetchTaskRequest) throws OSSException, ClientException {
        return oss.setAsyncFetchTask(setAsyncFetchTaskRequest);
    }

    @Override
    public GetAsyncFetchTaskResult getAsyncFetchTask(String s, String s1) throws OSSException, ClientException {
        return oss.getAsyncFetchTask(s, s1);
    }

    @Override
    public GetAsyncFetchTaskResult getAsyncFetchTask(GetAsyncFetchTaskRequest getAsyncFetchTaskRequest) throws OSSException, ClientException {
        return oss.getAsyncFetchTask(getAsyncFetchTaskRequest);
    }

    @Override
    public CreateVpcipResult createVpcip(CreateVpcipRequest createVpcipRequest) throws OSSException, ClientException {
        return oss.createVpcip(createVpcipRequest);
    }

    @Override
    public List<Vpcip> listVpcip() throws OSSException, ClientException {
        return oss.listVpcip();
    }

    @Override
    public VoidResult deleteVpcip(DeleteVpcipRequest deleteVpcipRequest) throws OSSException, ClientException {
        return oss.deleteVpcip(deleteVpcipRequest);
    }

    @Override
    public VoidResult createBucketVpcip(CreateBucketVpcipRequest createBucketVpcipRequest) throws OSSException, ClientException {
        return oss.createBucketVpcip(createBucketVpcipRequest);
    }

    @Override
    public List<VpcPolicy> getBucketVpcip(GenericRequest genericRequest) throws OSSException, ClientException {
        return oss.getBucketVpcip(genericRequest);
    }

    @Override
    public VoidResult deleteBucketVpcip(DeleteBucketVpcipRequest deleteBucketVpcipRequest) throws OSSException, ClientException {
        return oss.deleteBucketVpcip(deleteBucketVpcipRequest);
    }

    @Override
    public VoidResult setBucketInventoryConfiguration(String s, InventoryConfiguration inventoryConfiguration) throws OSSException, ClientException {
        return oss.setBucketInventoryConfiguration(s, inventoryConfiguration);
    }

    @Override
    public VoidResult setBucketInventoryConfiguration(SetBucketInventoryConfigurationRequest setBucketInventoryConfigurationRequest) throws OSSException, ClientException {
        return oss.setBucketInventoryConfiguration(setBucketInventoryConfigurationRequest);
    }

    @Override
    public GetBucketInventoryConfigurationResult getBucketInventoryConfiguration(String s, String s1) throws OSSException, ClientException {
        return oss.getBucketInventoryConfiguration(s, s1);
    }

    @Override
    public GetBucketInventoryConfigurationResult getBucketInventoryConfiguration(GetBucketInventoryConfigurationRequest getBucketInventoryConfigurationRequest) throws OSSException, ClientException {
        return oss.getBucketInventoryConfiguration(getBucketInventoryConfigurationRequest);
    }

    @Override
    public ListBucketInventoryConfigurationsResult listBucketInventoryConfigurations(String s) throws OSSException, ClientException {
        return oss.listBucketInventoryConfigurations(s);
    }

    @Override
    public ListBucketInventoryConfigurationsResult listBucketInventoryConfigurations(String s, String s1) throws OSSException, ClientException {
        return oss.listBucketInventoryConfigurations(s, s1);
    }

    @Override
    public ListBucketInventoryConfigurationsResult listBucketInventoryConfigurations(ListBucketInventoryConfigurationsRequest listBucketInventoryConfigurationsRequest) throws OSSException, ClientException {
        return oss.listBucketInventoryConfigurations(listBucketInventoryConfigurationsRequest);
    }

    @Override
    public VoidResult deleteBucketInventoryConfiguration(String s, String s1) throws OSSException, ClientException {
        return oss.deleteBucketInventoryConfiguration(s, s1);
    }

    @Override
    public VoidResult deleteBucketInventoryConfiguration(DeleteBucketInventoryConfigurationRequest deleteBucketInventoryConfigurationRequest) throws OSSException, ClientException {
        return oss.deleteBucketInventoryConfiguration(deleteBucketInventoryConfigurationRequest);
    }

    @Override
    public InitiateBucketWormResult initiateBucketWorm(InitiateBucketWormRequest initiateBucketWormRequest) throws OSSException, ClientException {
        return oss.initiateBucketWorm(initiateBucketWormRequest);
    }

    @Override
    public InitiateBucketWormResult initiateBucketWorm(String s, int i) throws OSSException, ClientException {
        return oss.initiateBucketWorm(s, i);
    }

    @Override
    public VoidResult abortBucketWorm(String s) throws OSSException, ClientException {
        return oss.abortBucketWorm(s);
    }

    @Override
    public VoidResult abortBucketWorm(GenericRequest genericRequest) throws OSSException, ClientException {
        return oss.abortBucketWorm(genericRequest);
    }

    @Override
    public VoidResult completeBucketWorm(String s, String s1) throws OSSException, ClientException {
        return oss.completeBucketWorm(s, s1);
    }

    @Override
    public VoidResult completeBucketWorm(CompleteBucketWormRequest completeBucketWormRequest) throws OSSException, ClientException {
        return oss.completeBucketWorm(completeBucketWormRequest);
    }

    @Override
    public VoidResult extendBucketWorm(String s, String s1, int i) throws OSSException, ClientException {
        return oss.extendBucketWorm(s, s1, i);
    }

    @Override
    public VoidResult extendBucketWorm(ExtendBucketWormRequest extendBucketWormRequest) throws OSSException, ClientException {
        return oss.extendBucketWorm(extendBucketWormRequest);
    }

    @Override
    public GetBucketWormResult getBucketWorm(String s) throws OSSException, ClientException {
        return oss.getBucketWorm(s);
    }

    @Override
    public GetBucketWormResult getBucketWorm(GenericRequest genericRequest) throws OSSException, ClientException {
        return oss.getBucketWorm(genericRequest);
    }

    @Override
    public VoidResult createDirectory(String s, String s1) throws OSSException, ClientException {
        return oss.createDirectory(s, s1);
    }

    @Override
    public VoidResult createDirectory(CreateDirectoryRequest createDirectoryRequest) throws OSSException, ClientException {
        return oss.createDirectory(createDirectoryRequest);
    }

    @Override
    public DeleteDirectoryResult deleteDirectory(String s, String s1) throws OSSException, ClientException {
        return oss.deleteDirectory(s, s1);
    }

    @Override
    public DeleteDirectoryResult deleteDirectory(String s, String s1, boolean b, String s2) throws OSSException, ClientException {
        return oss.deleteDirectory(s, s1, b, s2);
    }

    @Override
    public DeleteDirectoryResult deleteDirectory(DeleteDirectoryRequest deleteDirectoryRequest) throws OSSException, ClientException {
        return oss.deleteDirectory(deleteDirectoryRequest);
    }

    @Override
    public VoidResult renameObject(String s, String s1, String s2) throws OSSException, ClientException {
        return oss.renameObject(s, s1, s2);
    }

    @Override
    public VoidResult renameObject(RenameObjectRequest renameObjectRequest) throws OSSException, ClientException {
        return oss.renameObject(renameObjectRequest);
    }

    @Override
    public VoidResult setBucketResourceGroup(SetBucketResourceGroupRequest setBucketResourceGroupRequest) throws OSSException, ClientException {
        return oss.setBucketResourceGroup(setBucketResourceGroupRequest);
    }

    @Override
    public GetBucketResourceGroupResult getBucketResourceGroup(String s) throws OSSException, ClientException {
        return oss.getBucketResourceGroup(s);
    }

    @Override
    public VoidResult createUdf(CreateUdfRequest createUdfRequest) throws OSSException, ClientException {
        return oss.createUdf(createUdfRequest);
    }

    @Override
    public UdfInfo getUdfInfo(UdfGenericRequest udfGenericRequest) throws OSSException, ClientException {
        return oss.getUdfInfo(udfGenericRequest);
    }

    @Override
    public List<UdfInfo> listUdfs() throws OSSException, ClientException {
        return oss.listUdfs();
    }

    @Override
    public VoidResult deleteUdf(UdfGenericRequest udfGenericRequest) throws OSSException, ClientException {
        return oss.deleteUdf(udfGenericRequest);
    }

    @Override
    public VoidResult uploadUdfImage(UploadUdfImageRequest uploadUdfImageRequest) throws OSSException, ClientException {
        return oss.uploadUdfImage(uploadUdfImageRequest);
    }

    @Override
    public List<UdfImageInfo> getUdfImageInfo(UdfGenericRequest udfGenericRequest) throws OSSException, ClientException {
        return oss.getUdfImageInfo(udfGenericRequest);
    }

    @Override
    public VoidResult deleteUdfImage(UdfGenericRequest udfGenericRequest) throws OSSException, ClientException {
        return oss.deleteUdfImage(udfGenericRequest);
    }

    @Override
    public VoidResult createUdfApplication(CreateUdfApplicationRequest createUdfApplicationRequest) throws OSSException, ClientException {
        return oss.createUdfApplication(createUdfApplicationRequest);
    }

    @Override
    public UdfApplicationInfo getUdfApplicationInfo(UdfGenericRequest udfGenericRequest) throws OSSException, ClientException {
        return oss.getUdfApplicationInfo(udfGenericRequest);
    }

    @Override
    public List<UdfApplicationInfo> listUdfApplications() throws OSSException, ClientException {
        return oss.listUdfApplications();
    }

    @Override
    public VoidResult deleteUdfApplication(UdfGenericRequest udfGenericRequest) throws OSSException, ClientException {
        return oss.deleteUdfApplication(udfGenericRequest);
    }

    @Override
    public VoidResult upgradeUdfApplication(UpgradeUdfApplicationRequest upgradeUdfApplicationRequest) throws OSSException, ClientException {
        return oss.upgradeUdfApplication(upgradeUdfApplicationRequest);
    }

    @Override
    public VoidResult resizeUdfApplication(ResizeUdfApplicationRequest resizeUdfApplicationRequest) throws OSSException, ClientException {
        return oss.resizeUdfApplication(resizeUdfApplicationRequest);
    }

    @Override
    public UdfApplicationLog getUdfApplicationLog(GetUdfApplicationLogRequest getUdfApplicationLogRequest) throws OSSException, ClientException {
        return oss.getUdfApplicationLog(getUdfApplicationLogRequest);
    }

    @Override
    public VoidResult setBucketTransferAcceleration(String s, boolean b) throws OSSException, ClientException {
        return oss.setBucketTransferAcceleration(s, b);
    }

    @Override
    public TransferAcceleration getBucketTransferAcceleration(String s) throws OSSException, ClientException {
        return oss.getBucketTransferAcceleration(s);
    }

    @Override
    public VoidResult deleteBucketTransferAcceleration(String s) throws OSSException, ClientException {
        return oss.deleteBucketTransferAcceleration(s);
    }

    @Override
    public VoidResult putBucketAccessMonitor(String s, String s1) throws OSSException, ClientException {
        return oss.putBucketAccessMonitor(s, s1);
    }

    @Override
    public AccessMonitor getBucketAccessMonitor(String s) throws OSSException, ClientException {
        return oss.getBucketAccessMonitor(s);
    }

    @Override
    public VoidResult openMetaQuery(String s) throws OSSException, ClientException {
        return oss.openMetaQuery(s);
    }

    @Override
    public GetMetaQueryStatusResult getMetaQueryStatus(String s) throws OSSException, ClientException {
        return oss.getMetaQueryStatus(s);
    }

    @Override
    public DoMetaQueryResult doMetaQuery(DoMetaQueryRequest doMetaQueryRequest) throws OSSException, ClientException {
        return oss.doMetaQuery(doMetaQueryRequest);
    }

    @Override
    public VoidResult closeMetaQuery(String s) throws OSSException, ClientException {
        return oss.closeMetaQuery(s);
    }

    @Override
    public DescribeRegionsResult describeRegions(DescribeRegionsRequest describeRegionsRequest) throws OSSException, ClientException {
        return oss.describeRegions(describeRegionsRequest);
    }

    @Override
    public VoidResult setBucketCallbackPolicy(SetBucketCallbackPolicyRequest setBucketCallbackPolicyRequest) throws OSSException, ClientException {
        return oss.setBucketCallbackPolicy(setBucketCallbackPolicyRequest);
    }

    @Override
    public GetBucketCallbackPolicyResult getBucketCallbackPolicy(GenericRequest genericRequest) throws OSSException, ClientException {
        return oss.getBucketCallbackPolicy(genericRequest);
    }

    @Override
    public VoidResult deleteBucketCallbackPolicy(GenericRequest genericRequest) throws OSSException, ClientException {
        return oss.deleteBucketCallbackPolicy(genericRequest);
    }

    @Override
    public AsyncProcessObjectResult asyncProcessObject(AsyncProcessObjectRequest asyncProcessObjectRequest) throws OSSException, ClientException {
        return oss.asyncProcessObject(asyncProcessObjectRequest);
    }

    @Override
    public VoidResult writeGetObjectResponse(WriteGetObjectResponseRequest writeGetObjectResponseRequest) throws OSSException, ClientException {
        return oss.writeGetObjectResponse(writeGetObjectResponseRequest);
    }
}
