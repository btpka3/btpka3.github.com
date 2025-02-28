package me.test.first.aliyun.acr;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.auth.AlibabaCloudCredentialsProvider;
import com.aliyuncs.profile.DefaultProfile;
import org.junit.jupiter.api.Test;

/**
 * @author dangqian.zll
 * @date 2024/12/13
 */
public class AkLessTest {

    @Test
    public void withAk01() {
        String regionId = null;
        String accessKey = null;
        String accessSecret = null;
        // 创建POP网关Client
        DefaultProfile profile = DefaultProfile.getProfile(regionId, accessKey, accessSecret);
        IAcsClient client = new DefaultAcsClient(profile);
        // TODO with client
    }

    @Test
    public void akLess01() {
        //AlibabaCloudCredentialsProvider credentialsProvider = AklessCredProviderFactory.getCredentialsProvider(ramRoleArn);
        AlibabaCloudCredentialsProvider credentialsProvider = null;
        // 创建无AK CredentialsProvider，传入roleArn
        String regionId = null;
        // 创建POP网关Client
        DefaultProfile profile = DefaultProfile.getProfile(regionId);
        IAcsClient client = new DefaultAcsClient(profile, credentialsProvider);
        // TODO with client
    }
}
