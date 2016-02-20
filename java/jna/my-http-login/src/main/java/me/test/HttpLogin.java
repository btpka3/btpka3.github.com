package me.test;

import java.io.IOException;
import java.util.Map;

import org.apache.http.client.HttpClient;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.HttpClients;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.util.Assert;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 需要先分析 http请求,在模拟http请求的数据,使用编程的方式进行登录.
 * 适合: Http请求中没有加密数据,没有复杂JS,ActiveX控件计算数据的情形.
 */
@Configuration
public class HttpLogin {

    private static AnnotationConfigApplicationContext ctx = null;
    private static String userName = "xxx";
    private static String pwd = "xxx";

    public static void main(String[] args) throws JsonParseException, JsonMappingException, IOException {
        ctx = new AnnotationConfigApplicationContext();
        ctx.register(HttpLogin.class);
        ctx.refresh();

        login();
        getUserBuyList();
    }

    // 登录
    @SuppressWarnings("unchecked")
    public static void login() throws JsonParseException, JsonMappingException, IOException {
        RestTemplate restTemplate = ctx.getBean(RestTemplate.class);

        String url = "http://www.qmduobao.com/login/login.html";

        // param
        MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
        params.add("userName", userName);
        params.add("pwd", pwd);

        // login
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Requested-With", "XMLHttpRequest");
        ResponseEntity<String> resp = restTemplate.postForEntity(url, params, String.class);
        String respStr = resp.getBody();
        Assert.isTrue(HttpStatus.OK == resp.getStatusCode(), "登录失败,http 状态码是 " + resp.getStatusCode());
        Assert.isTrue(!"userError".equals(respStr), "用户名不存在");
        Assert.isTrue(!"pwdError".equals(respStr), "密码错误");

        // String -> JSON
        ObjectMapper objectMapper = ctx.getBean(ObjectMapper.class);
        Map<String, ?> jsonObj = objectMapper.readValue(respStr, Map.class);
        System.out.println("登录成功,用户ID = " + jsonObj.get("userId"));
    }

    // 获取夺宝列表
    @SuppressWarnings("unchecked")
    public static void getUserBuyList() throws JsonParseException, JsonMappingException, IOException {
        RestTemplate restTemplate = ctx.getBean(RestTemplate.class);

        String url = "http://www.qmduobao.com/user/UserBuyList.html";

        // param
        MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
        params.add("userName", userName);
        params.add("pwd", pwd);

        // get
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Requested-With", "XMLHttpRequest");
        ResponseEntity<String> resp = restTemplate.getForEntity(url, String.class);
        String respStr = resp.getBody();
        Assert.isTrue(HttpStatus.OK == resp.getStatusCode(), "ERROR, http 状态码是 " + resp.getStatusCode());

        // String -> HTML
        System.out.println(respStr);

        // 说明: 如果解析html的话,请使用 JSOUP
        //      该页面,浏览器上查看的 "夺宝记录" 是通过AJax请求获取的,URL如下(注意:不需要登录的额)
        //  

        System.out.println("===================================================");
        url = "http://www.qmduobao.com/user/userBuyListAjaxPage.action?pageNo=0&userId=1001679872&selectTime=0&startDate=&endDate=";
        resp = restTemplate.getForEntity(url, String.class);
        respStr = resp.getBody();
        System.out.println(respStr);
    }

    // ------------------------------------------------------- Spring Bean 配置区域

    @Bean
    @Scope("prototype")
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

    @Bean
    @Scope("prototype")
    public HttpClient httpClient() {

        RequestConfig globalConfig = RequestConfig.custom()
                .setCookieSpec(CookieSpecs.DEFAULT)
                .build();

        return HttpClients.custom()
                .setDefaultRequestConfig(globalConfig)
                .build();
    }

    @Bean
    @Scope("prototype")
    public HttpComponentsClientHttpRequestFactory requestFactory() {
        return new HttpComponentsClientHttpRequestFactory(httpClient());
    }

    @Bean
    @Scope("prototype")
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
