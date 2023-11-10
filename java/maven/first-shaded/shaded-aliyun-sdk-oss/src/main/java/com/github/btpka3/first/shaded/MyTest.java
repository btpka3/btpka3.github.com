package com.github.btpka3.first.shaded;

import com.aliyun.oss.OSSClient;

/**
 * @author dangqian.zll
 * @date 2023/11/9
 */
public class MyTest {

    public static void main(String[] args) {
        OSSClient ossClient = null;
        ossClient.abortBucketWorm("aaa");
    }
}
