# Aliyun MNS Sample

This is a sample that demonstrates how to interactive with Aliyun MNS using the Java SDK.

## Prerequisites

* You must have a valid Aliyun developer account, see [http://www.aliyun.com](http://www.aliyun.com).
* Requires the Java SDK for MNS, see [http://docs.aliyun.com/?#/mns/sdk/java_sdk](http://docs.aliyun.com/?#/mns/sdk/java_sdk).
* You must be signed up to use Aliyun MNS, see [http://www.aliyun.com/product/mns](http://www.aliyun.com/product/mns).

## Running the Sample

The basic steps are:

1. Create a file with name ".aliyun-mns.properties" in user home directory which is "~/" in Linux or "C:\Users\YOURNAME\" in Windows.
2. Fill the file with your Access Key ID, Secret Access Key, Account Id and Account Endpoint (Example: http://AccountId.mns.cn-hangzhou.aliyuncs.com) :
> mns.accountendpoint=
> mns.accesskeyid=
> mns.accesskeysecret=
> mns.securitytoken=
3. Save the file.
4. Run the `QueueSample.java` and `TopicSample.java` file, located in the same directory.
