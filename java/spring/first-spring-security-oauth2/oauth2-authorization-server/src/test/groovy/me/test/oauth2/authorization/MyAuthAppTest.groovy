package me.test.oauth2.authorization

import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.context.embedded.EmbeddedWebApplicationContext
import org.springframework.boot.context.embedded.LocalServerPort
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.*
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner
import org.springframework.web.util.UriComponentsBuilder

import static org.assertj.core.api.Assertions.assertThat

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = [MyAuthApp.class], webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MyAuthAppTest {

    @Autowired
    EmbeddedWebApplicationContext applicationContext;

    @Autowired
    TestRestTemplate restTemplate;

    /** 服务的端口号 */
    @LocalServerPort
    int port;

    /** 没有任何认证信息 */
    @Test
    public void o2Me01() {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept([MediaType.TEXT_HTML])

        String path = UriComponentsBuilder.fromPath("/o2/me")
                .build()
                .toUri()
                .toString()

        HttpEntity<Void> reqEntity = new HttpEntity<Void>(null, headers);

        ResponseEntity<String> respEntity = restTemplate.exchange(path,
                HttpMethod.GET, reqEntity, String.class);

        assertThat(respEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_ACCEPTABLE)
        assertThat(respEntity.body).contains("<div>4xx.html: 406</div>");
    }

    /** 登录并 */
    @Test
    public void loginAndOauth01() {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept([MediaType.TEXT_HTML])

        String path = UriComponentsBuilder.fromPath("/o2/me")
                .build()
                .toUri()
                .toString()

        HttpEntity<Void> reqEntity = new HttpEntity<Void>(null, headers);

        ResponseEntity<String> respEntity = restTemplate.exchange(path,
                HttpMethod.GET, reqEntity, String.class);

        assertThat(respEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_ACCEPTABLE)
        assertThat(respEntity.body).contains("<div>4xx.html: 406</div>");
    }

}
