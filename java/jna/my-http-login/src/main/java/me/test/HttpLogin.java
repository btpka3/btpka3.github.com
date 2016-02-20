package me.test;

import java.io.IOException;
import java.util.Map;

import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.util.PublicSuffixMatcher;
import org.apache.http.conn.util.PublicSuffixMatcherLoader;
import org.apache.http.cookie.ClientCookie;
import org.apache.http.cookie.CookieSpecProvider;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.impl.cookie.DefaultCookieSpecProvider;
import org.apache.http.impl.cookie.RFC6265CookieSpecProvider;
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
 * 
 * 参考:
 * http://hc.apache.org/httpcomponents-client-4.5.x/tutorial/html/statemgmt.html#d5e499
 */
@Configuration
public class HttpLogin {

    private static AnnotationConfigApplicationContext ctx = null;
    private static String userName = "xx";
    private static String pwd = "xx";

    // 服务器返回的 userId
    private static String userId = null;

    public static void main(String[] args) throws JsonParseException, JsonMappingException, IOException {
        ctx = new AnnotationConfigApplicationContext();
        ctx.register(HttpLogin.class);
        ctx.refresh();

        index();
        login();
        getUserBuyList();
    }

    // 登录
    @SuppressWarnings("unchecked")
    public static void index() {
        RestTemplate restTemplate = ctx.getBean(RestTemplate.class);

        String url = "http://www.qmduobao.com/";
        ResponseEntity<String> resp = restTemplate.getForEntity(url, String.class);
        Assert.isTrue(HttpStatus.OK == resp.getStatusCode(), "ERROR, http 状态码是 " + resp.getStatusCode());

        // 获取 cookie: "JSESSIONID"
        CookieStore cookieStore = ctx.getBean(CookieStore.class);
        //System.out.println("====================" + resp.getHeaders().get("Set-Cookie"));
        System.out.println(cookieStore.getCookies());

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

        CookieStore cookieStore = ctx.getBean(CookieStore.class);

        // 模拟JS设置相关cookie
        BasicClientCookie cookie = new BasicClientCookie("loginType", "0");
        cookie.setDomain(".qmduobao.com");
        cookie.setPath("/");
        cookie.setAttribute(ClientCookie.PATH_ATTR, "/");
        cookie.setAttribute(ClientCookie.DOMAIN_ATTR, ".qmduobao.com");
        cookieStore.addCookie(cookie);

        cookie = new BasicClientCookie("loginUser", (String) jsonObj.get("phone"));
        cookie.setDomain(".qmduobao.com");
        cookie.setPath("/");
        cookie.setAttribute(ClientCookie.PATH_ATTR, "/");
        cookie.setAttribute(ClientCookie.DOMAIN_ATTR, ".qmduobao.com");
        cookieStore.addCookie(cookie);

        cookie = new BasicClientCookie("userName", (String) jsonObj.get("phone"));
        cookie.setDomain(".qmduobao.com");
        cookie.setPath("/");
        cookie.setAttribute(ClientCookie.PATH_ATTR, "/");
        cookie.setAttribute(ClientCookie.DOMAIN_ATTR, ".qmduobao.com");
        cookieStore.addCookie(cookie);

        userId = jsonObj.get("userId").toString();
        cookie = new BasicClientCookie("userId", jsonObj.get("userId").toString());
        cookie.setDomain(".qmduobao.com");
        cookie.setPath("/");
        cookie.setAttribute(ClientCookie.PATH_ATTR, "/");
        cookie.setAttribute(ClientCookie.DOMAIN_ATTR, ".qmduobao.com");
        cookieStore.addCookie(cookie);

        System.out.println(cookieStore.getCookies());

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
        if (respStr.contains("夺宝记录")) {
            System.out.println("=====OK, 获取成功");
            System.out.println(respStr);
        } else {
            System.out.println("=====ERROR, 获取失败");
        }

        //System.out.println(respStr);

        // 说明: 如果解析html的话,请使用 JSOUP
        //      该页面,浏览器上查看的 "夺宝记录" 是通过AJax请求获取的,URL如下(注意:不需要登录的额)

        System.out.println("===================================================");
        url = "http://www.qmduobao.com/user/userBuyListAjaxPage.action?pageNo=0&userId="
                + userId
                + "&selectTime=0&startDate=&endDate=";
        resp = restTemplate.getForEntity(url, String.class);
        respStr = resp.getBody();
        System.out.println(respStr);

    }

    // ------------------------------------------------------- Spring Bean 配置区域

    @Bean
    @Scope("singleton")
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

    @Bean
    @Scope("singleton")
    public CookieStore cookieStore() {
        return new BasicCookieStore();
    }

    @Bean
    @Scope("singleton")
    public HttpClient httpClient() {
        PublicSuffixMatcher publicSuffixMatcher = PublicSuffixMatcherLoader.getDefault();

        Registry<CookieSpecProvider> r = RegistryBuilder.<CookieSpecProvider> create()
                .register(CookieSpecs.DEFAULT,
                        new DefaultCookieSpecProvider(publicSuffixMatcher))
                .register(CookieSpecs.STANDARD,
                        new RFC6265CookieSpecProvider(publicSuffixMatcher))
                .build();

        RequestConfig globalConfig = RequestConfig.custom()
                .setCookieSpec(CookieSpecs.DEFAULT)
                .build();

        return HttpClients.custom()
                .setUserAgent("Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:44.0) Gecko/20100101 Firefox/44.0")
                .setDefaultCookieSpecRegistry(r)
                .setDefaultCookieStore(cookieStore())
                .setDefaultRequestConfig(globalConfig)
                .build();
    }

    @Bean
    @Scope("singleton")
    public HttpComponentsClientHttpRequestFactory requestFactory() {
        return new HttpComponentsClientHttpRequestFactory(httpClient());
    }

    @Bean
    @Scope("prototype")
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setRequestFactory(requestFactory());
        return restTemplate;
    }
}
