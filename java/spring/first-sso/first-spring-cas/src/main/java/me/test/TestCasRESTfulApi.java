
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

    /**
     * TODO 方法的作用是？
     *
     * @author zhangliangliang
     * @date 2013-8-8下午2:25:29
     *
     * @param args
     */
    @SuppressWarnings("unchecked")
    public static void main(String[] args) {

        String casUrlPrefix = "https://cas.localhost.me:8443/first-cas-server";
        String username = "zhang3";
        String password = "123456";

        // GET TGT
        RestTemplate rest = new RestTemplate();
        rest.setMessageConverters(Arrays.asList(new StringHttpMessageConverter(), new FormHttpMessageConverter()));
        MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
        params.set("username", username);
        params.set("password", password);
        URI tgtUrl = rest.postForLocation(casUrlPrefix + "/v1/tickets", params, Collections.emptyMap());

        // GET ST
        //String service = "http://app.localhost.me:8080/first-spring-cas/sec.jsp";
        String service = "http://stateless.localhost:8080/first-spring-stateless/appointment.jsp";
        params.clear();
        params.set("service", service);
        ResponseEntity<String> st = rest.postForEntity(tgtUrl, params, String.class);
        System.out.println(st.getBody());

        // GET SSO Web App content
        Map<String, Object> urlParams = new HashMap<String, Object>();
        urlParams.put("ticket", st.getBody());
        ResponseEntity<String> html = rest.getForEntity(service, String.class, urlParams);
        System.out.println(html);
        System.out.println(html.getBody());

        System.out.println("~~~~~~~~~~~~~~");
        html = rest.getForEntity(service+"?ticket="+st.getBody(), String.class, Collections.emptyMap());
        System.out.println(html);
        System.out.println(html.getBody());
    }
}
