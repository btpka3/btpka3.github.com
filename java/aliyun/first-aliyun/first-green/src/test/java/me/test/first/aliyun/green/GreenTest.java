package me.test.first.aliyun.green;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.green.model.v20180509.ImageSyncScanRequest;
import com.aliyuncs.http.FormatType;
import com.aliyuncs.http.HttpResponse;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.http.ProtocolType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.Date;
import java.util.UUID;

/**
 * @author 当千
 * @date 2019-04-23
 */
@SpringBootTest(
        classes = GreenTest.Conf.class,
        webEnvironment = SpringBootTest.WebEnvironment.NONE
)
@TestPropertySource
public class GreenTest {


    @SpringBootApplication
    public static class Conf {

    }

    String ALIYUN_ACCESS_KEY_ID = "<yourAccessKeyId>";
    String ALIYUN_ACCESS_KEY_SECRET = "<yourAccessKeySecret>";
    String REGION_ID = "cn-shanghai";


    @Test
    public void scan01() {
        IClientProfile profile = DefaultProfile.getProfile(REGION_ID, ALIYUN_ACCESS_KEY_ID, ALIYUN_ACCESS_KEY_SECRET);
        IAcsClient client = new DefaultAcsClient(profile);

        ImageSyncScanRequest imageSyncScanRequest = new ImageSyncScanRequest();
        // 指定api返回格式
        imageSyncScanRequest.setSysAcceptFormat(FormatType.JSON);
        // 指定请求方法
        imageSyncScanRequest.setSysMethod(MethodType.POST);
        imageSyncScanRequest.setSysEncoding("utf-8");
        //支持http和https
        imageSyncScanRequest.setSysProtocol(ProtocolType.HTTP);


        JSONObject httpBody = new JSONObject();
        {
            /*
             * 设置要检测的场景, 计费是按照该处传递的场景进行
             * 一次请求中可以同时检测多张图片，每张图片可以同时检测多个风险场景，计费按照场景计算
             * 例如：检测2张图片，场景传递porn,terrorism，计费会按照2张图片鉴黄，2张图片暴恐检测计算
             * porn: porn表示色情场景检测
             */
            httpBody.put("scenes", Arrays.asList("porn"));

            /*
             * 设置待检测图片， 一张图片一个task，
             * 多张图片同时检测时，处理的时间由最后一个处理完的图片决定。
             * 通常情况下批量检测的平均rt比单张检测的要长, 一次批量提交的图片数越多，rt被拉长的概率越高
             * 这里以单张图片检测作为示例, 如果是批量图片检测，请自行构建多个task
             */
            JSONObject task = new JSONObject();
            task.put("dataId", UUID.randomUUID().toString());

            //设置图片链接
            task.put("url", "http://xxx.test.jpg");
            task.put("time", new Date());
            httpBody.put("tasks", Arrays.asList(task));
        }

        imageSyncScanRequest.setHttpContent(
                org.apache.commons.codec.binary.StringUtils.getBytesUtf8(httpBody.toJSONString()),
                "UTF-8",
                FormatType.JSON
        );

        /*
         * 请设置超时时间, 服务端全链路处理超时时间为10秒，请做相应设置
         * 如果您设置的ReadTimeout 小于服务端处理的时间，程序中会获得一个read timeout 异常
         */
        imageSyncScanRequest.setSysConnectTimeout(3000);
        imageSyncScanRequest.setSysReadTimeout(10000);
        HttpResponse httpResponse = null;
        try {
            httpResponse = client.doAction(imageSyncScanRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //服务端接收到请求，并完成处理返回的结果
        if (httpResponse != null && httpResponse.isSuccess()) {
            JSONObject scrResponse = JSON.parseObject(org.apache.commons.codec.binary.StringUtils.newStringUtf8(httpResponse.getHttpContent()));
            System.out.println(JSON.toJSONString(scrResponse, true));
            int requestCode = scrResponse.getIntValue("code");
            //每一张图片的检测结果
            JSONArray taskResults = scrResponse.getJSONArray("data");
            if (200 == requestCode) {
                for (Object taskResult : taskResults) {
                    //单张图片的处理结果
                    int taskCode = ((JSONObject) taskResult).getIntValue("code");
                    //图片要检测的场景的处理结果, 如果是多个场景，则会有每个场景的结果
                    JSONArray sceneResults = ((JSONObject) taskResult).getJSONArray("results");
                    if (200 == taskCode) {
                        for (Object sceneResult : sceneResults) {
                            String scene = ((JSONObject) sceneResult).getString("scene");
                            String suggestion = ((JSONObject) sceneResult).getString("suggestion");
                            //根据scene和suggetion做相关处理
                            //do something
                            System.out.println("scene = [" + scene + "]");
                            System.out.println("suggestion = [" + suggestion + "]");
                        }
                    } else {
                        //单张图片处理失败, 原因是具体的情况详细分析
                        System.out.println("task process fail. task response:" + JSON.toJSONString(taskResult));
                    }
                }
            } else {
                /*
                 * 表明请求整体处理失败，原因视具体的情况详细分析
                 */
                System.out.println("the whole image scan request failed. response:" + JSON.toJSONString(scrResponse));
            }
        }

    }
}
