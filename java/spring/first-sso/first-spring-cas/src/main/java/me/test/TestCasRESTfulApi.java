
package me.test;

import java.net.URI;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

public class TestCasRESTfulApi {

    @SuppressWarnings("unchecked")
    public static void test() {

        String casUrlPrefix = "http://login-test.his.com/tcgroup-sso-web";
        String username = "15372712873";
        String password = "123456";

        // GET TGT
        RestTemplate rest = new RestTemplate();
        rest.setMessageConverters(Arrays.asList(new StringHttpMessageConverter(), new FormHttpMessageConverter()));
        MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
        params.set("username", username);
        params.set("password", password);
        URI tgtUrl = rest.postForLocation(casUrlPrefix + "/v1/tickets", params, Collections.emptyMap());
        System.out.println("[" + tgtUrl + "]");

        // GET ST
        String service = "http://login-test.his.com/tcgroup-his-web/client/nop.do";
        params.clear();
        params.set("service", service);
        ResponseEntity<String> st = rest.postForEntity(tgtUrl, params, String.class);
        System.out.println("[" + st.getBody() + "]");

        // Using ST get data from SSO app(tcgroup-his-web).
        Map<String, Object> urlParams = new HashMap<String, Object>();
        urlParams.put("ticket", st.getBody());
        ResponseEntity<String> html = rest.getForEntity(service + "?ticket=" + st.getBody(), String.class, urlParams);
        System.out.println("[" + html.getBody() + "]");
    }

    public static void main(String[] args) {

        // 测试总次数
        int count = 100;

        // 开始测试
        long start = System.currentTimeMillis();
        for (int i = 0; i < count; i++) {
            test();
        }
        long end = System.currentTimeMillis();

        long cost = end - start;
        System.out.println("total time cost : " + cost);
        System.out.println("Done.");
    }
}
