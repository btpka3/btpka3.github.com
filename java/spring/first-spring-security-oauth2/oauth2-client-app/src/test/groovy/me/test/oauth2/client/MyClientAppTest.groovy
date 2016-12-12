package me.test.oauth2.client

import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.context.embedded.EmbeddedWebApplicationContext
import org.springframework.boot.context.embedded.LocalServerPort
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.LocalHostUriTemplateHandler
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.*
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner
import org.springframework.util.LinkedMultiValueMap
import org.springframework.util.MultiValueMap
import org.springframework.web.util.UriComponentsBuilder

import static org.assertj.core.api.Assertions.assertThat

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = [MyClientApp.class], webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MyClientAppTest {

    @Autowired
    EmbeddedWebApplicationContext applicationContext;

    @Autowired
    TestRestTemplate restTemplate;

    /** 服务的端口号 */
    @LocalServerPort
    int clientPort;

    @Value('${my.oauth2.auth.port}')
    int authPort = 10001;

    /** 主页 */
    @Test
    public void home01() {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept([MediaType.TEXT_HTML])

        String path = UriComponentsBuilder.fromPath("/")
                .build()
                .toUri()
                .toString()

        HttpEntity<Void> reqEntity = new HttpEntity<Void>(null, headers);

        ResponseEntity<String> respEntity = restTemplate.exchange(path,
                HttpMethod.GET, reqEntity, String.class);

        assertThat(respEntity.getStatusCode()).isEqualTo(HttpStatus.OK)
        assertThat(respEntity.body).contains("my oauth2 client app");
    }

    /** 登录后才能访问的页面 */
    @Test
    public void loginAndSec01() {

        TestRestTemplate tmpRestTemplate = new TestRestTemplate(TestRestTemplate.HttpClientOption.ENABLE_COOKIES);
        tmpRestTemplate.setUriTemplateHandler(new LocalHostUriTemplateHandler(applicationContext.getEnvironment()));

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept([MediaType.TEXT_HTML])

        MultiValueMap reqMsg = new LinkedMultiValueMap()
        reqMsg.username = "c_admin"
        reqMsg.password = "c_admin"

        HttpEntity<Void> reqEntity = new HttpEntity<Void>(reqMsg, headers);

        String path = UriComponentsBuilder.fromPath("/login")
                .build()
                .toUri()
                .toString()

        ResponseEntity<String> respEntity = tmpRestTemplate.exchange(path,
                HttpMethod.POST, reqEntity, String.class);

        assertThat(respEntity.getStatusCode()).isEqualTo(HttpStatus.FOUND)
        assertThat(respEntity.headers.getLocation().toString()).isEqualTo("http://localhost:${clientPort}/".toString())

        // 检查 restTemplate 是否配置OK，能否保留 Cookie
        HttpHeaders headers1 = new HttpHeaders();
        headers1.setAccept([MediaType.TEXT_HTML])

        String path1 = UriComponentsBuilder.fromPath("/sec")
                .build()
                .toUri()
                .toString()

        HttpEntity<Void> reqEntity1 = new HttpEntity<Void>(null, headers);
        ResponseEntity<String> respEntity1 = tmpRestTemplate.exchange(path1,
                HttpMethod.GET, reqEntity1, String.class);

        assertThat(respEntity1.getStatusCode()).isEqualTo(HttpStatus.OK)
        assertThat(respEntity1.body).contains("client sec ");
    }

    /** 访问授权资源的正常流程 */
    @Test
    public void oauth01() {
        TestRestTemplate tmpRestTemplate = new TestRestTemplate(TestRestTemplate.HttpClientOption.ENABLE_COOKIES);
        tmpRestTemplate.setUriTemplateHandler(new LocalHostUriTemplateHandler(applicationContext.getEnvironment()));

        // 登录 client app
        client_login(restTemplate)

        // 在 client app 中访问需资源授权的功能
        URI authUri = client_accessResourceWithoutAuth(restTemplate)

        // 跳转到 authorize server 进行授权 (未登录，应当先让用户登录)
        URI authLoginUri = auth_authWithoutLogin(restTemplate, authUri)

        // 登录 authorization server
        URI authWithLoginUri = auth_login(restTemplate, authLoginUri)

        // 登录 authorize server
        URI rscWithAtUri = auth_authWithLogin(restTemplate, authWithLoginUri)

        // 跳转回 client app
        client_accessResourceWithAuth(restTemplate, rscWithAtUri);

    }

    /** Client App : 登录 */
    private void client_login(TestRestTemplate restTemplate) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept([MediaType.TEXT_HTML])

        MultiValueMap reqMsg = new LinkedMultiValueMap()
        reqMsg.username = "c_admin"
        reqMsg.password = "c_admin"

        HttpEntity<Void> reqEntity = new HttpEntity<Void>(reqMsg, headers);

        String path = UriComponentsBuilder.fromPath("/login")
                .build()
                .toUri()
                .toString()

        ResponseEntity<String> respEntity = restTemplate.exchange(path,
                HttpMethod.POST, reqEntity, String.class);

        assertThat(respEntity.getStatusCode()).isEqualTo(HttpStatus.FOUND)
        assertThat(respEntity.headers.getLocation().toString()).isEqualTo("http://localhost:${clientPort}/".toString())

    }

    /** Client App : 第一次访问资源（此时尚未授权） */
    private URI client_accessResourceWithoutAuth(TestRestTemplate restTemplate) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept([MediaType.TEXT_HTML])

        MultiValueMap reqMsg = new LinkedMultiValueMap()

        HttpEntity<Void> reqEntity = new HttpEntity<Void>(reqMsg, headers);

        String path = UriComponentsBuilder.fromPath("/photo")
                .build()
                .toUri()
                .toString()

        ResponseEntity<String> respEntity = restTemplate.exchange(path,
                HttpMethod.POST, reqEntity, String.class);

        assertThat(respEntity.getStatusCode()).isEqualTo(HttpStatus.FOUND)
        URI uri = respEntity.headers.getLocation()
        assertThat(uri.toString()).isEqualTo("http://localhost:${authPort}/oauth/authorize".toString())
        return uri
    }

    /** Authorization Server : 进行授权（但是此时尚未登录） */
    private URI auth_authWithoutLogin(TestRestTemplate restTemplate, URI authUri) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept([MediaType.TEXT_HTML])

        MultiValueMap reqMsg = new LinkedMultiValueMap()
        reqMsg.username = "c_admin"
        reqMsg.password = "c_admin"

        HttpEntity<Void> reqEntity = new HttpEntity<Void>(reqMsg, headers);

        String path = UriComponentsBuilder.fromUri(authUri)
                .build()
                .toUri()
                .toString()

        ResponseEntity<String> respEntity = restTemplate.exchange(path,
                HttpMethod.POST, reqEntity, String.class);

        assertThat(respEntity.getStatusCode()).isEqualTo(HttpStatus.FOUND)
        URI uri = respEntity.headers.getLocation()
        assertThat(uri.toString()).contains("http://localhost:${authPort}/login".toString())
        return uri
    }

    /** Authorization Server : 登录 */
    private URI auth_login(TestRestTemplate restTemplate, URI authLoginUri) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept([MediaType.TEXT_HTML])

        MultiValueMap reqMsg = new LinkedMultiValueMap()
        reqMsg.username = "a_admin"
        reqMsg.password = "a_admin"

        HttpEntity<Void> reqEntity = new HttpEntity<Void>(reqMsg, headers);

        String path = UriComponentsBuilder.fromUri(authLoginUri)
                .build()
                .toUri()
                .toString()

        ResponseEntity<String> respEntity = restTemplate.exchange(path,
                HttpMethod.POST, reqEntity, String.class);

        assertThat(respEntity.getStatusCode()).isEqualTo(HttpStatus.FOUND)
        URI uri = respEntity.headers.getLocation()
        assertThat(uri.toString()).contains("http://localhost:${authPort}/oauth/authorize".toString())
        return uri
    }

    /** Authorization Server : 进行授权（已登录） */
    private URI auth_authWithLogin(TestRestTemplate restTemplate, URI authUri) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept([MediaType.TEXT_HTML])

        String path = UriComponentsBuilder.fromUri(authUri)
                .build()
                .toUri()
                .toString()

        ResponseEntity<String> respEntity = restTemplate.exchange(path,
                HttpMethod.GET, null, String.class);

        assertThat(respEntity.getStatusCode()).isEqualTo(HttpStatus.FOUND)
        URI uri = respEntity.headers.getLocation()
        assertThat(uri.toString()).contains("http://localhost:${clientPort}/photos".toString())
        return uri
    }

    /** Client App : 第二次访问资源（此时已授权） */
    private URI client_accessResourceWithAuth(TestRestTemplate restTemplate, URI rscWithAtUri) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept([MediaType.TEXT_HTML])

        String path = UriComponentsBuilder.fromUri(rscWithAtUri)
                .build()
                .toUri()
                .toString()

        ResponseEntity<String> respEntity = restTemplate.exchange(path,
                HttpMethod.GET, null, String.class);

        assertThat(respEntity.getStatusCode()).isEqualTo(HttpStatus.FOUND)
        URI uri = respEntity.headers.getLocation()
        assertThat(uri.toString()).contains("http://localhost:${clientPort}/photos".toString())
        return uri
    }
}
