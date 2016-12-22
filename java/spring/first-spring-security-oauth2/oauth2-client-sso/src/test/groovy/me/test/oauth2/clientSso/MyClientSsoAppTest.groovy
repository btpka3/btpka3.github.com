package me.test.oauth2.clientSso

import me.test.oauth2.common.MyOAuth2Properties
import org.junit.Test
import org.junit.runner.RunWith
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.*
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner
import org.springframework.util.LinkedMultiValueMap
import org.springframework.util.MultiValueMap
import org.springframework.web.util.UriComponentsBuilder

import static org.assertj.core.api.Assertions.assertThat

// 使用独立运行的服务进行测试


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = [MyApp.class], webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class MyClientSsoAppTest {

    @Configuration
    @EnableConfigurationProperties
    static class MyApp {

        @Bean
        MyOAuth2Properties myOAuth2Properties() {
            return new MyOAuth2Properties()
        }
    }

    final String logPrefix = "█" * 40 + " "
    final Logger log = LoggerFactory.getLogger(getClass());


    MyOAuth2Properties myOAuth2Props
    String clientUrl
    String authUrl


    @Autowired
    void initProps(MyOAuth2Properties myOAuth2Props) {
        println("----------------eeee : " + myOAuth2Props.client.url)
        this.myOAuth2Props = myOAuth2Props
        clientUrl = myOAuth2Props.client.url
        authUrl = myOAuth2Props.auth.url
    }


    @Test
    public void empty() {
        assertThat(myOAuth2Props.client.url).isNotNull()
    }

    /** 主页 */
    @Test
    public void home01() {
        TestRestTemplate restTemplate = new TestRestTemplate()

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept([MediaType.TEXT_HTML])

        String path = UriComponentsBuilder.fromHttpUrl("${clientUrl}/")
                .build()
                .toUri()
                .toString()

        HttpEntity<Void> reqEntity = new HttpEntity<Void>(null, headers);

        ResponseEntity<String> respEntity = restTemplate.exchange(path,
                HttpMethod.GET, reqEntity, String.class);

        assertThat(respEntity.getStatusCode()).isEqualTo(HttpStatus.OK)
        assertThat(respEntity.body).contains("my oauth2 client sso");
        log.debug(respEntity.body)
    }

    /** 需登录才能访问的页面 */
    @Test
    public void sec01() {
        TestRestTemplate restTemplate = new TestRestTemplate()
        client_accessSecWithoutLocalLogin(restTemplate)
    }

    @Test
    public void htmlSso01() {

        TestRestTemplate clientRestTemplate = new TestRestTemplate(TestRestTemplate.HttpClientOption.ENABLE_COOKIES);
        TestRestTemplate authRestTemplate = new TestRestTemplate(TestRestTemplate.HttpClientOption.ENABLE_COOKIES);

        // "GET http://s.localhost:10004/sec" —— 尚未登录
        URI clientLoginUri = client_accessSecWithoutLocalLogin(clientRestTemplate)

        // "GET http://s.localhost:10004/login" —— 尚未登录
        URI authUri = client_localLoginWithoutSso(clientRestTemplate, clientLoginUri)

        // "GET http://a.localhost:10004/oauth/authorize" —— 尚未登录
        URI authLoginUri = auth_authWithoutLogin(authRestTemplate, authUri)

        // 省略了一步 ： 让显示 登录表单画面的请求
        // "GET http://a.localhost:10004/login" —— 尚未登录

        // "POST http://a.localhost:10004/oauth/authorize" —— 已经登录
        URI authUriWithLogin = auth_login(authRestTemplate, authLoginUri)

        URI clientLoginUriWithSso = auth_authWithLoginAndAutoAuth(authRestTemplate, authUriWithLogin)

        // "GET http://a.localhost:10004/login" —— 已经登录
        URI secWithLocalLogin = client_localLoginWithSso(clientRestTemplate, clientLoginUriWithSso)

        // "GET http://s.localhost:10004/sec" —— 已经登录
        client_accessSecWithLocalLogin(clientRestTemplate, secWithLocalLogin)
    }

    /** Client App : 第一次访问需登录的路径（此时尚未登录） */
    private URI client_accessSecWithoutLocalLogin(TestRestTemplate restTemplate) {
        log.debug(logPrefix + "client_accessSecWithoutLocalLogin")
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept([MediaType.ALL])

        HttpEntity<Void> reqEntity = new HttpEntity<Void>(null, headers);

        String path = UriComponentsBuilder.fromHttpUrl("${clientUrl}/sec")
                .build()
                .toUri()
                .toString()

        ResponseEntity<String> respEntity = restTemplate.exchange(path,
                HttpMethod.GET, reqEntity, String.class);

        assertThat(respEntity.getStatusCode()).isEqualTo(HttpStatus.FOUND)
        URI uri = respEntity.headers.getLocation()

        assertThat(uri.toString()).startsWith("${clientUrl}/login".toString())
        MultiValueMap<String, String> decodedQueryParams = UriComponentsBuilder.newInstance()
                .query(uri.getQuery())
                .build()
                .getQueryParams()
        assertThat(decodedQueryParams)
                .isEmpty()
        return uri
    }
    /** Client App : 第一次访问需登录的路径（此时尚未SSO） */
    private URI client_localLoginWithoutSso(TestRestTemplate restTemplate, URI loginUri) {
        log.debug(logPrefix + "client_localLoginWithoutSso")

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept([MediaType.ALL])

        HttpEntity<Void> reqEntity = new HttpEntity<Void>(null, headers);


        ResponseEntity<String> respEntity = restTemplate.exchange(loginUri,
                HttpMethod.GET, reqEntity, String.class);

        assertThat(respEntity.getStatusCode()).isEqualTo(HttpStatus.FOUND)
        URI uri = respEntity.headers.getLocation()

        // "http://a.localhost:10001/oauth/authorize
        // ?client_id=MY_CLIENT
        // &redirect_uri=http://s.localhost:10004/login
        // &response_type=code
        // &scope=LOGIN&state=IugS4H"
        assertThat(uri.toString()).startsWith("${authUrl}/oauth/authorize".toString())
        MultiValueMap<String, String> decodedQueryParams = UriComponentsBuilder.newInstance()
                .query(uri.getQuery())
                .build()
                .getQueryParams()
        assertThat(decodedQueryParams)
                .containsEntry("client_id", [myOAuth2Props.client.id])
                .containsEntry("redirect_uri", ["${clientUrl}/login".toString()])
                .containsEntry("response_type", ["code"])
                .containsEntry("scope", ["LOGIN"])
                .containsKeys("state")
        return uri
    }

    /** Authorization Server : 进行授权（但是此时尚未登录） */
    private URI auth_authWithoutLogin(TestRestTemplate restTemplate, URI authUri) {
        log.debug(logPrefix + "auth_authWithoutLogin")

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept([MediaType.TEXT_HTML])

        HttpEntity<Void> reqEntity = new HttpEntity<Void>(null, headers);

        ResponseEntity<String> respEntity = restTemplate.exchange(authUri,
                HttpMethod.GET, reqEntity, String.class);

        assertThat(respEntity.getStatusCode()).isEqualTo(HttpStatus.FOUND)
        URI uri = respEntity.headers.getLocation()
        assertThat(uri.toString()).isEqualTo("${authUrl}/login".toString())
        return uri
    }

    /** Authorization Server : 登录 */
    private URI auth_login(TestRestTemplate restTemplate, URI authLoginUri) {
        log.debug(logPrefix + "auth_login")

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept([MediaType.TEXT_HTML])

        MultiValueMap reqMsg = new LinkedMultiValueMap()
        reqMsg.username = "a_admin"
        reqMsg.password = "a_admin"

        HttpEntity reqEntity = new HttpEntity(reqMsg, headers);

        ResponseEntity<String> respEntity = restTemplate.exchange(authLoginUri,
                HttpMethod.POST, reqEntity, String.class);

        assertThat(respEntity.getStatusCode()).isEqualTo(HttpStatus.FOUND)
        URI uri = respEntity.headers.getLocation()
        assertThat(uri.toString()).startsWith("${authUrl}/oauth/authorize".toString())

        MultiValueMap<String, String> decodedQueryParams = UriComponentsBuilder.newInstance()
                .query(uri.getQuery())
                .build()
                .getQueryParams()
        assertThat(decodedQueryParams)
                .containsEntry("client_id", [myOAuth2Props.client.id])
                .containsEntry("redirect_uri", ["${clientUrl}/login".toString()])
                .containsEntry("response_type", ["code"])
                .containsEntry("scope", ["LOGIN"])
                .containsKeys("state")

        return uri
    }

    /** Authorization Server : 再次访问授权网址（会自动授权） */
    private URI auth_authWithLoginAndAutoAuth(TestRestTemplate restTemplate, URI authUri) {
        log.debug(logPrefix + "auth_authWithLoginAndAutoAuth")

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept([MediaType.TEXT_HTML])

        HttpEntity<Void> reqEntity = new HttpEntity<Void>(null, headers);

        ResponseEntity<String> respEntity = restTemplate.exchange(authUri,
                HttpMethod.GET, reqEntity, String.class);

        assertThat(respEntity.getStatusCode()).isEqualTo(HttpStatus.FOUND)
        URI uri = respEntity.headers.getLocation()
        assertThat(uri.toString()).startsWith("${clientUrl}/login".toString())

        MultiValueMap<String, String> decodedQueryParams = UriComponentsBuilder.newInstance()
                .query(uri.getQuery())
                .build()
                .getQueryParams()
        assertThat(decodedQueryParams)
                .containsKeys("code")
                .containsKeys("state")

        return uri

    }

    /** Client App : 第二次访问需登录的路径（此时已经SSO） */
    private URI client_localLoginWithSso(TestRestTemplate restTemplate, URI loginUri) {
        log.debug(logPrefix + "client_localLoginWithoutSso")

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept([MediaType.ALL])

        HttpEntity<Void> reqEntity = new HttpEntity<Void>(null, headers);


        ResponseEntity<String> respEntity = restTemplate.exchange(loginUri,
                HttpMethod.GET, reqEntity, String.class);

        assertThat(respEntity.getStatusCode()).isEqualTo(HttpStatus.FOUND)
        URI uri = respEntity.headers.getLocation()

        assertThat(uri.toString()).startsWith("${clientUrl}/sec".toString())
        MultiValueMap<String, String> decodedQueryParams = UriComponentsBuilder.newInstance()
                .query(uri.getQuery())
                .build()
                .getQueryParams()
        assertThat(decodedQueryParams)
                .isEmpty()

        return uri
    }

    /** Client App : 第二次访问需登录的路径（此时已经登录） */
    private void client_accessSecWithLocalLogin(TestRestTemplate restTemplate, URI secUri) {
        log.debug(logPrefix + "client_accessSecWithLocalLogin")
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept([MediaType.ALL])

        HttpEntity<Void> reqEntity = new HttpEntity<Void>(null, headers);

        ResponseEntity<String> respEntity = restTemplate.exchange(secUri,
                HttpMethod.GET, reqEntity, String.class);

        assertThat(respEntity.getStatusCode()).isEqualTo(HttpStatus.OK)
        assertThat(respEntity.body).contains("client sso sec");
        log.debug(respEntity.body)
    }

}
