package me.test

import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.context.embedded.EmbeddedWebApplicationContext
import org.springframework.boot.context.embedded.LocalServerPort
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner
import org.springframework.util.Base64Utils
import org.springframework.util.LinkedMultiValueMap
import org.springframework.util.MultiValueMap
import org.springframework.web.util.UriComponentsBuilder

import java.nio.charset.Charset

import static org.assertj.core.api.Assertions.assertThat

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = [MySecApp.class], webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MySecTest {

    @Autowired
    EmbeddedWebApplicationContext applicationContext;

    @Autowired
    TestRestTemplate restTemplate;

    /** 服务的端口号 */
    @LocalServerPort
    int port;

    // request
    //      -> SpringBootWebSecurityConfiguration#ignoredPathsWebSecurityConfigurerAdapter()
    //      -> SecConf#webSecurityConfigurerAdapter
    //      -> default basic settings
    //      -> method annotation

    /** Ignore 测试 : `/js/a.js` 测试 */
    @Test
    public void ignore01() {
        String body = restTemplate.getForObject("/js/a.js", String.class);
        assertThat(body).contains("/* a.js */");
    }

    /** SecConf 测试 : `/SecConf/pub.html` 测试 */
    @Test
    public void secConfPub01() {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept([MediaType.TEXT_HTML])

        String path = UriComponentsBuilder.fromPath("/SecConf/pub.html")
                .build()
                .toUri()
                .toString()

        HttpEntity<Void> reqEntity = new HttpEntity<Void>(null, headers);

        ResponseEntity<String> respEntity = restTemplate.exchange(path,
                HttpMethod.GET, reqEntity, String.class);

        String body = respEntity.body
        assertThat(body).contains("<title>SecConf-pub</title>");
    }

    /** SecConf 测试 : `/SecConf/sec.html` 测试 */
    @Test
    public void secConfSec01() {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept([MediaType.TEXT_HTML])
        String token = Base64Utils.encodeToString(("admin:admin").getBytes("UTF-8"));
        headers.add("Authorization", "Basic " + token);

        String path = UriComponentsBuilder.fromPath("/SecConf/sec.html")
                .build()
                .toUri()
                .toString()

        HttpEntity<Void> reqEntity = new HttpEntity<Void>(null, headers);

        ResponseEntity<String> respEntity = restTemplate.exchange(path,
                HttpMethod.GET, reqEntity, String.class);

        assertThat(respEntity.body).contains("<title>SecConf-sec</title>");
    }

    /** SecConf 测试 : `/SecConf/adm.html` 测试  - admin 用户 */
    @Test
    public void secCondAdm01() {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept([MediaType.TEXT_HTML])
        String token = Base64Utils.encodeToString(("admin:admin").getBytes("UTF-8"));
        headers.add("Authorization", "Basic " + token);

        String path = UriComponentsBuilder.fromPath("/SecConf/adm.html")
                .build()
                .toUri()
                .toString()

        HttpEntity<Void> reqEntity = new HttpEntity<Void>(null, headers);

        ResponseEntity<String> respEntity = restTemplate.exchange(path,
                HttpMethod.GET, reqEntity, String.class);

        assertThat(respEntity.body).contains("<title>SecConf-adm</title>");
    }

    /** SecConf 测试 : `/SecConf/adm.html` 测试 - user 用户*/
    @Test
    public void secCondAdm02() {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept([MediaType.TEXT_HTML])
        String token = Base64Utils.encodeToString(("user:user").getBytes("UTF-8"));
        headers.add("Authorization", "Basic " + token);

        String path = UriComponentsBuilder.fromPath("/SecConf/adm.html")
                .build()
                .toUri()
                .toString()

        HttpEntity<Void> reqEntity = new HttpEntity<Void>(null, headers);

        ResponseEntity<String> respEntity = restTemplate.exchange(path,
                HttpMethod.GET, reqEntity, String.class);


        assertThat(respEntity.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN)
        assertThat(respEntity.body).contains("<div>4xx.html: 403</div>");
    }

    /** SecConf 测试 : `/SecConf/adm.html` 测试 - 未设置用户名密码 */
    @Test
    public void secCondAdm03() {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept([MediaType.TEXT_HTML])

        String path = UriComponentsBuilder.fromPath("/SecConf/adm.html")
                .build()
                .toUri()
                .toString()

        HttpEntity<Void> reqEntity = new HttpEntity<Void>(null, headers);

        ResponseEntity<String> respEntity = restTemplate.exchange(path,
                HttpMethod.GET, reqEntity, String.class);

        // 需要表单登录
        assertThat(respEntity.getStatusCode()).isEqualTo(HttpStatus.FOUND)
        assertThat(respEntity.headers.getLocation().toString()).isEqualTo("http://localhost:${port}/login".toString())
    }

    /** SecConf 测试 : `/SecConf/adm.html` 测试 - 用户名密码错误 */
    @Test
    public void secCondAdm04() {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept([MediaType.TEXT_HTML])
        String token = Base64Utils.encodeToString(("admin:admin1").getBytes("UTF-8"));
        headers.add("Authorization", "Basic " + token);

        String path = UriComponentsBuilder.fromPath("/SecConf/adm.html")
                .build()
                .toUri()
                .toString()

        HttpEntity<Void> reqEntity = new HttpEntity<Void>(null, headers);

        ResponseEntity<String> respEntity = restTemplate.exchange(path,
                HttpMethod.GET, reqEntity, String.class);

        // 默认走了 HttpBasicConfigurer 中配置的 BasicAuthenticationEntryPoint
        assertThat(respEntity.headers.get("WWW-Authenticate").get(0)).isEqualTo('Basic realm="MySecApp"')
        assertThat(respEntity.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED)
        assertThat(respEntity.body).contains("<div>4xx.html: 401</div>");
    }
    /** SecConf 测试 : `/SecConf/adm.html` 测试 - 模拟 XHR  */
    @Test
    public void secCondAdm05() {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept([MediaType.TEXT_HTML])
        String token = Base64Utils.encodeToString(("admin:admin1").getBytes("UTF-8"));
        headers.add("Authorization", "Basic " + token);
        headers.set("X-Requested-With", "XMLHttpRequest")

        String path = UriComponentsBuilder.fromPath("/SecConf/adm.html")
                .build()
                .toUri()
                .toString()

        HttpEntity<Void> reqEntity = new HttpEntity<Void>(null, headers);

        ResponseEntity<String> respEntity = restTemplate.exchange(path,
                HttpMethod.GET, reqEntity, String.class);

        // 默认走了 HttpBasicConfigurer 中配置的 HttpStatusEntryPoint
        assertThat(respEntity.headers.get("WWW-Authenticate")).isNull()
        assertThat(respEntity.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED)
        assertThat(respEntity.body).isNull()

    }

    /** 登录测试: 获取登录画面 */
    @Test
    public void login01() {
        String body = restTemplate.getForObject("/login", String.class);
        assertThat(body).contains("<title>my login form</title>");
    }

    /** 登录测试: 提交表单登录信息 - CSRF token 无效 */
    @Test
    public void login02() {

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept([MediaType.TEXT_HTML])

        MultiValueMap reqMsg = new LinkedMultiValueMap()
        reqMsg.username = "admin"
        reqMsg.password = "admin"
        reqMsg._csrf = "not_existed_csrf_token"

        HttpEntity<Void> reqEntity = new HttpEntity<Void>(reqMsg, headers);

        String path = UriComponentsBuilder.fromPath("/login")
                .build()
                .toUri()
                .toString()

        ResponseEntity<String> respEntity = restTemplate.exchange(path,
                HttpMethod.POST, reqEntity, String.class);

        // 因为 CSRF token 不存在， 故 CsrfFilter 会做相应的处理
        assertThat(respEntity.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN)
        assertThat(respEntity.body).contains("<div>4xx.html: 403</div>");

    }

    /** 默认 basic 认证测试 : `/controller/basic` 测试 - 没有 basic 认证 */
    @Test
    public void defaultBasic01() {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept([MediaType.TEXT_HTML])
        headers.set("X-Requested-With", "XMLHttpRequest")

        String path = UriComponentsBuilder.fromPath("/controller/basic")
                .build()
                .toUri()
                .toString()

        HttpEntity<Void> reqEntity = new HttpEntity<Void>(null, headers);

        ResponseEntity<String> respEntity = restTemplate.exchange(path,
                HttpMethod.GET, reqEntity, String.class);

        assertThat(respEntity.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED)
        assertThat(respEntity.body).contains("<div>4xx.html: 401</div>");
    }

    /** 默认 basic 认证测试 : `/controller/basic` 测试 - 有 basic 认证，有 USER 权限 */
    @Test
    public void defaultBasic02() {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept([MediaType.TEXT_HTML])
        String token = Base64Utils.encodeToString(("user:user").getBytes("UTF-8"));
        headers.add("Authorization", "Basic " + token);

        String path = UriComponentsBuilder.fromPath("/controller/basic")
                .build()
                .toUri()
                .toString()

        HttpEntity<Void> reqEntity = new HttpEntity<Void>(null, headers);

        ResponseEntity<String> respEntity = restTemplate.exchange(path,
                HttpMethod.GET, reqEntity, String.class);

        assertThat(respEntity.getStatusCode()).isEqualTo(HttpStatus.OK)
        assertThat(respEntity.body).contains("/controller/basic");
    }
    /** 默认 basic 认证测试 : `/controller/basic` 测试 - 有 basic 认证，无 USER 权限 */
    @Test
    public void defaultBasic03() {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept([MediaType.TEXT_HTML])
        String token = Base64Utils.encodeToString(("basic:basic").getBytes("UTF-8"));
        headers.add("Authorization", "Basic " + token);

        String path = UriComponentsBuilder.fromPath("/controller/basic")
                .build()
                .toUri()
                .toString()

        HttpEntity<Void> reqEntity = new HttpEntity<Void>(null, headers);

        ResponseEntity<String> respEntity = restTemplate.exchange(path,
                HttpMethod.GET, reqEntity, String.class);

        assertThat(respEntity.getStatusCode()).isEqualTo(HttpStatus.OK)
        assertThat(respEntity.body).contains("/controller/basic");
    }

    // TODO : restTemplate 使用cookie跟随302跳转，获取新的 csrf token 并登陆成功。

}
