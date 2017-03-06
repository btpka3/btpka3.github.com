package me.test.first.spring.boot.cxf

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
import org.springframework.http.MediaType
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner
import org.springframework.web.util.UriComponentsBuilder

import static org.assertj.core.api.Assertions.assertThat

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = [MyCxfApp.class], webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@TestConfiguration // 该配置仅仅影响当前测试使用的环境
public class MyMvcAppTest {

    @Autowired
    EmbeddedWebApplicationContext applicationContext;

    @Autowired
    TestRestTemplate restTemplate;

    /** 服务的端口号 */
    @LocalServerPort
    int port;

    /** 静态资源测试: `classpath:/static/` */
    @Test
    public void static01() {
        String body = restTemplate.getForObject("/a.css", String.class);
        assertThat(body).startsWith("/* a.css */");
    }

    /** 静态资源测试: `classpath:/public/` */
    @Test
    public void public01() {
        String body = restTemplate.getForObject("/a.js", String.class);
        assertThat(body).startsWith("/* a.js */");
    }

    /** 静态资源测试: `classpath:/META-INF/resources/` */
    @Test
    public void metaInfResources01() {
        String body = restTemplate.getForObject("/a.txt", String.class);
        assertThat(body).startsWith("/* a.txt */");
    }

    /** 静态资源测试: `classpath:/resources/` */
    @Test
    public void resources01() {
        String body = restTemplate.getForObject("/a.html", String.class);
        assertThat(body).startsWith("<!-- a.html -->");
    }


    @Test
    public void home01() {
        String body = restTemplate.getForObject("/", String.class);
        assertThat(body).startsWith("home ");
    }

    @Test
    public void service01() {
        String path = UriComponentsBuilder.fromPath("/service")
                .queryParam("a", "3")
                .queryParam("b", "4")
                .build()
                .toUri()
                .toString()

        String body = restTemplate.getForObject(path, String.class);
        assertThat(body).startsWith("service 7");
    }

    @Test
    public void thymeleaf01() {
        String body = restTemplate.getForObject("/thymeleaf", String.class);
        assertThat(body).contains("<div>thymeleaf: 1+2=3</div>");
    }

    /** 测试内容协商: HTML : 通过 HTTP 请求头 "Accept" */
    @Test
    public void respType01() {

        // 默认请求头：Accept: text/plain, text/plain, application/json, application/json, application/xml, application/xml, application/*+json, application/*+json, text/xml, application/*+xml, text/xml, application/*+xml, */*, */*

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept([MediaType.TEXT_HTML])

        HttpEntity<Void> reqEntity = new HttpEntity<Void>(null, headers);

        String path = UriComponentsBuilder.fromPath("/respType")
                .build()
                .toUri()
                .toString()

        String body = restTemplate.exchange(path, HttpMethod.GET, reqEntity, String.class);

        assertThat(body).contains("<div>respTypeHtml: respTypeHtml");
    }

    /** 测试内容协商: JSON : 通过 HTTP 请求头 "Accept" 为空 */
    @Test
    public void respType02() {

        HttpHeaders headers = new HttpHeaders();
        // 注意：以下三种方式均返回 json
        headers.set(HttpHeaders.ACCEPT, "")
        //headers.setAccept([MediaType.ALL])
        //headers.setAccept([MediaType.APPLICATION_JSON_UTF8])

        HttpEntity<Void> reqEntity = new HttpEntity<Void>(null, headers);

        String path = UriComponentsBuilder.fromPath("/respType")
//                .queryParam("format", "json")
                .build()
                .toUri()
                .toString()

        String body = restTemplate.exchange(path, HttpMethod.GET, reqEntity, String.class);
        assertThat(body).contains('{"a":"respType","b":');
    }

    /** 测试内容协商: JSON : URL上 "format" 参数*/
    @Test
    public void respType03() {

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept([MediaType.TEXT_HTML])

        HttpEntity<Void> reqEntity = new HttpEntity<Void>(null, headers);

        String path = UriComponentsBuilder.fromPath("/respType")
                .queryParam("format", "json")
                .build()
                .toUri()
                .toString()

        String body = restTemplate.exchange(path, HttpMethod.GET, reqEntity, String.class);
        assertThat(body).contains('{"a":"respType","b":');
    }

    /** 测试内容协商: JSON : 通过 URL 后缀 */
    @Test
    public void respType04() {

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept([MediaType.TEXT_HTML])

        HttpEntity<Void> reqEntity = new HttpEntity<Void>(null, headers);

        String path = UriComponentsBuilder.fromPath("/respType.xml")
        //.queryParam("format", "xml")
                .build()
                .toUri()
                .toString()

        String body = restTemplate.exchange(path, HttpMethod.GET, reqEntity, String.class);
        assertThat(body).contains('<a>respType</a><b>');
    }

    /** 测试内容协商: JSON : 优先级 :
     * 1. URL 后缀
     * 2. "format" URL 参数
     * 3. "Accept" http 请求头
     */
    @Test
    public void respType05() {

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept([MediaType.TEXT_HTML])

        HttpEntity<Void> reqEntity = new HttpEntity<Void>(null, headers);

        String path = UriComponentsBuilder.fromPath("/respType.xml")
                .queryParam("format", "json")
                .build()
                .toUri()
                .toString()

        String body = restTemplate.exchange(path, HttpMethod.GET, reqEntity, String.class);
        assertThat(body).contains('<a>respType</a><b>');
    }

    // -------------
    /** 测试错误处理：404 - 使用静态模板, HTML `classpath:/resources/error/404.html` */
    @Test
    public void err404_01() {

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept([MediaType.TEXT_HTML])

        HttpEntity<Void> reqEntity = new HttpEntity<Void>(null, headers);

        String path = UriComponentsBuilder.fromPath("/err404")
        //.queryParam("format", "json")
                .build()
                .toUri()
                .toString()

        String body = restTemplate.exchange(path, HttpMethod.GET, reqEntity, String.class);
        assertThat(body).contains("<span>404 error</span>")
    }

    /** 测试错误处理：400 - 使用静态模板, HTML `classpath:/resources/error/4xx.html` */
    @Test
    public void err400_01() {

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept([MediaType.TEXT_HTML])

        HttpEntity<Void> reqEntity = new HttpEntity<Void>(null, headers);

        String path = UriComponentsBuilder.fromPath("/err400")
        //.queryParam("format", "json")
                .build()
                .toUri()
                .toString()

        String body = restTemplate.exchange(path, HttpMethod.GET, reqEntity, String.class);
        assertThat(body).contains("<div>4xx</div>")
    }

    // -------------
    /** 测试错误处理：500 - 使用动态模板, HTML `classpath:/templates/error/500.html` */
    @Test
    public void err500_01() {

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept([MediaType.TEXT_HTML])

        HttpEntity<Void> reqEntity = new HttpEntity<Void>(null, headers);

        String path = UriComponentsBuilder.fromPath("/err500")
        //.queryParam("format", "json")
                .build()
                .toUri()
                .toString()

        String body = restTemplate.exchange(path, HttpMethod.GET, reqEntity, String.class);
        assertThat(body).contains("<div>500.html: 500</div>")
    }

    /** 测试错误处理：500 - 使用动态模板, JSON `classpath:/templates/error/500.html` */
    @Test
    public void err500_02() {

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept([MediaType.TEXT_HTML])

        HttpEntity<Void> reqEntity = new HttpEntity<Void>(null, headers);

        String path = UriComponentsBuilder.fromPath("/err500")
        //.queryParam("format", "json")
                .build()
                .toUri()
                .toString()

        String body = restTemplate.exchange(path, HttpMethod.GET, reqEntity, String.class);
        assertThat(body).contains("<div>500.html: 500</div>")
    }

    /** 测试错误处理：500 - 使用动态模板, 内容协商虽然 url 后缀最高，但抛出异常后会丢失，故会返回 xml */
    @Test
    public void err500_03() {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept([MediaType.TEXT_HTML])

        HttpEntity<Void> reqEntity = new HttpEntity<Void>(null, headers);

        String path = UriComponentsBuilder.fromPath("/err500.json")
                .queryParam("format", "xml")
                .build()
                .toUri()
                .toString()

        String body = restTemplate.exchange(path, HttpMethod.GET, reqEntity, String.class);
        println "=============="
        println body
        assertThat(body)
                .contains("<status>500</status>")
                .contains("<message>err500</message>")
                .contains("<path>/err500.json</path>")
    }
}
