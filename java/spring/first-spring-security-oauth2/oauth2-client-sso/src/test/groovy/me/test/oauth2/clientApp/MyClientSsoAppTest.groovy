package me.test.oauth2.clientApp

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
`
}
