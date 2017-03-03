package me.test.first.spring.boot.cxf

import me.test.first.spring.boot.swagger.MockFileResource
import me.test.first.spring.boot.swagger.MySwaggerApp
import me.test.first.spring.boot.swagger.model.Item
import me.test.first.spring.boot.swagger.model.MyEnum
import me.test.first.spring.boot.swagger.model.TestPostJsonReq
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.context.embedded.EmbeddedWebApplicationContext
import org.springframework.boot.context.embedded.LocalServerPort
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.core.io.ByteArrayResource
import org.springframework.core.io.Resource
import org.springframework.http.*
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner
import org.springframework.util.LinkedMultiValueMap
import org.springframework.util.MultiValueMap
import org.springframework.web.util.UriComponentsBuilder

import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

import static org.assertj.core.api.Assertions.assertThat

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = [MySwaggerApp], webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@TestConfiguration // 该配置仅仅影响当前测试使用的环境
//@CompileStatic
class MySwaggerAppTest {

    @Autowired
    EmbeddedWebApplicationContext applicationContext;

    @Autowired
    TestRestTemplate restTemplate;

    /** 服务的端口号 */
    @LocalServerPort
    int port;

    @Test
    void testGet01() {

        String userName = "zhang3"
        List<String> userNames = ["li4", "wang5"]

        String date = "2000-01-02T03:04:05.070+08:00"
        LocalDateTime localDateTime = LocalDateTime.parse(date, DateTimeFormatter.ISO_ZONED_DATE_TIME)
        long epochSecond = localDateTime.toEpochSecond(ZoneOffset.UTC)
        Instant instant = localDateTime.toInstant(ZoneOffset.of("+08:00"))
        Date dataObj = Date.from(instant)
        long epochMilli = instant.toEpochMilli()

        HttpHeaders headers = new HttpHeaders();
//        headers.setAccept([MediaType.TEXT_HTML])
        //headers.set("nonce", "nonce-1")
        headers.set("userName", userName)
        headers.put("userNames", userNames)

        HttpEntity<Void> reqEntity = new HttpEntity<Void>(null, headers);

        String path = UriComponentsBuilder.fromPath("/test/{oid}/testGet/{userId}")
                .queryParam("curPage", "99")
                .queryParam("addresses", "addr1", "addr2")
                .queryParam("startDate", date)
                .build()
                .expand(["oid": "111", "userId": "222"])
                .toUri()
                .toString()
        ResponseEntity<String> respEntity = restTemplate.exchange(path, HttpMethod.GET, reqEntity, String.class);

        println "======------: " + respEntity
        assertThat(respEntity.statusCode).isEqualTo(HttpStatus.OK)
        assertThat(respEntity.headers.getContentType()).isEqualTo(MediaType.APPLICATION_JSON_UTF8);
        assertThat(respEntity.headers.get("userName")).isEqualTo([userName]);
        assertThat(respEntity.headers.get("userNames")).isEqualTo(userNames);
        assertThat(respEntity.body).isEqualTo("""{"oid":"111","userId":"222","curPage":99,"addresses":["addr1","addr2"],"startDate":${
            epochMilli
        },"itemList":[{"title":"item-1","price":100},{"title":"item-2","price":200}]}""" as String);
    }


    @Test
    void testPostForm01() {

        String userName = "zhang3"
        List<String> userNames = ["li4", "wang5"]
        String date = "2000-01-02T03:04:05.070+08:00"

//        long epochSecond = LocalDateTime
//                .parse(date, DateTimeFormatter.ISO_ZONED_DATE_TIME)
//                .toEpochSecond(ZoneOffset.UTC)
        long epochMilli = LocalDateTime
                .parse(date, DateTimeFormatter.ISO_ZONED_DATE_TIME)
                .toInstant(ZoneOffset.of("+08:00"))
                .toEpochMilli()
        HttpHeaders headers = new HttpHeaders()
        MultiValueMap<String, String> reqData = [
                name    : ["zhang3"],
                height  : ["182"],
                hobbies : ["play", "sleep"],
                birthday: [date]
        ] as LinkedMultiValueMap

        HttpEntity<MultiValueMap<String, String>> reqEntity = new HttpEntity<>(reqData, headers)

        String path = UriComponentsBuilder.fromPath("/test/{oid}/testPostForm")
                .build()
                .expand(["oid": "111"])
                .toUri()
                .toString()
        ResponseEntity<String> respEntity = restTemplate.exchange(path, HttpMethod.POST, reqEntity, String);

        println "======------: " + respEntity
        assertThat(respEntity.statusCode).isEqualTo(HttpStatus.OK)

        // FIXME ??? UTF-8 何来？
        assertThat(respEntity.headers.getContentType().toString()).isEqualTo("text/plain;charset=UTF-8");
        assertThat(respEntity.body).isEqualTo("zhang3-182-[play, sleep]-${epochMilli}" as String);
    }


    @Test
    void testFileUpload01() {

        String userName = "zhang3"
        List<String> userNames = ["li4", "wang5"]
        String date = "2000-01-02T03:04:05.070+08:00"

//        long epochSecond = LocalDateTime
//                .parse(date, DateTimeFormatter.ISO_ZONED_DATE_TIME)
//                .toEpochSecond(ZoneOffset.UTC)
        long epochMilli = LocalDateTime
                .parse(date, DateTimeFormatter.ISO_ZONED_DATE_TIME)
                .toInstant(ZoneOffset.of("+08:00"))
                .toEpochMilli()
        HttpHeaders headers = new HttpHeaders()
        headers.setContentType(MediaType.MULTIPART_FORM_DATA)

        // 如果使用 ByteArrayResource, 将没有 filename 内容和 Content-Type，进而造成406
        // 跟踪 ResourceHttpMessageConverter#getMediaType 可以得知原因：ByteArrayResource#getFilename == null
        // "--u97X-MtYT3piSSnMaLb_jj_OTb25HXm6iUk[\r][\n]"
        // "Content-Disposition: form-data; name="file1"[\r][\n]"
        // "Content-Length: 3[\r][\n]"
        // "[\r][\n]"
        // "aaa[\r][\n]"
        // "--u97X-MtYT3piSSnMaLb_jj_OTb25HXm6iUk[\r][\n]"
//        Resource file1 = new ByteArrayResource("aaa".getBytes());
//        Resource file2 = new ByteArrayResource("bbb".getBytes());

        // "--tOq2RWLENNODk3gUxcu3DreXpQswY6UNSUV[\r][\n]"
        // "Content-Disposition: form-data; name="file2"; filename="b.txt"[\r][\n]" // FIXME filename
        // "Content-Type: text/plain[\r][\n]"  // FIXME
        // "Content-Length: 3[\r][\n]"
        // "[\r][\n]"
        // "bbb[\r][\n]"
//        Resource file1 = new FileSystemResource("src/test/resources/a.txt")
//        Resource file2 = new FileSystemResource("src/test/resources/b.txt")
//        println "file1 = "+file1.getFile().getAbsolutePath()

        Resource file1 = new MockFileResource("a.txt", "aaa")
        Resource file2 = new MockFileResource("b.txt", "bbb")

        MultiValueMap<String, Object> reqData = [
                file1: [file1],
                file2: [file2]
        ] as LinkedMultiValueMap

        HttpEntity<MultiValueMap<String, Object>> reqEntity = new HttpEntity<>(reqData, headers)

        String path = UriComponentsBuilder.fromPath("/test/{oid}/testFileUpload")
                .build()
                .expand(["oid": "111"])
                .toUri()
                .toString()
        ResponseEntity<String> respEntity = restTemplate.exchange(path, HttpMethod.POST, reqEntity, String);

        println "======------: " + respEntity
        assertThat(respEntity.statusCode).isEqualTo(HttpStatus.OK)
        assertThat(respEntity.headers.getContentType().toString()).isEqualTo("text/plain;charset=UTF-8");
        assertThat(respEntity.body).isEqualTo("aaa-bbb");
    }


    @Test
    void testPostBin01() {

        String userName = "zhang3"
        List<String> userNames = ["li4", "wang5"]
        String date = "2000-01-02T03:04:05.070+08:00"

//        long epochSecond = LocalDateTime
//                .parse(date, DateTimeFormatter.ISO_ZONED_DATE_TIME)
//                .toEpochSecond(ZoneOffset.UTC)
        long epochMilli = LocalDateTime
                .parse(date, DateTimeFormatter.ISO_ZONED_DATE_TIME)
                .toInstant(ZoneOffset.of("+08:00"))
                .toEpochMilli()
        HttpHeaders headers = new HttpHeaders()
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM)

        Resource reqData = new ByteArrayResource("aaa".getBytes())

        HttpEntity<MultiValueMap<String, String>> reqEntity = new HttpEntity<>(reqData, headers)

        String path = UriComponentsBuilder.fromPath("/test/{oid}/testPostBin")
                .build()
                .expand(["oid": "111"])
                .toUri()
                .toString()
        ResponseEntity<String> respEntity = restTemplate.exchange(path, HttpMethod.POST, reqEntity, String);

        println "======------: " + respEntity
        assertThat(respEntity.statusCode).isEqualTo(HttpStatus.OK)

        // FIXME ??? UTF-8 何来？
        assertThat(respEntity.headers.getContentType().toString()).isEqualTo("text/plain;charset=UTF-8");
        assertThat(respEntity.body).isEqualTo("aaa");
    }


    @Test
    void testPostJson01() {

        String userName = "zhang3"
        List<String> userNames = ["li4", "wang5"]

        String date = "2000-01-02T03:04:05.070+08:00"
        LocalDateTime localDateTime = LocalDateTime.parse(date, DateTimeFormatter.ISO_ZONED_DATE_TIME)
        long epochSecond = localDateTime.toEpochSecond(ZoneOffset.UTC)
        Instant instant = localDateTime.toInstant(ZoneOffset.of("+08:00"))
        Date dataObj = Date.from(instant)
        long epochMilli = instant.toEpochMilli()



        HttpHeaders headers = new HttpHeaders()
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8)

        TestPostJsonReq reqData = new TestPostJsonReq(
                name: "name-1",
                count: 9,
                addresses: ["addr-1", "addr-2"],
                startDate: dataObj,
                itemList: [
                        new Item(title: "item-1", price: 100),
                        new Item(title: "item-2", price: 200)
                ],
                type: MyEnum.THREE

        )

        HttpEntity<TestPostJsonReq> reqEntity = new HttpEntity<>(reqData, headers)

        String path = UriComponentsBuilder.fromPath("/test/{oid}/testPostJson")
                .build()
                .expand(["oid": "111"])
                .toUri()
                .toString()
        ResponseEntity<String> respEntity = restTemplate.exchange(path, HttpMethod.POST, reqEntity, String);

        println "======------: " + respEntity
        assertThat(respEntity.statusCode).isEqualTo(HttpStatus.OK)

        // FIXME ??? UTF-8 何来？
        assertThat(respEntity.headers.getContentType()).isEqualTo(MediaType.APPLICATION_JSON_UTF8);
        assertThat(respEntity.body).isEqualTo('{'
                + '"name":"name-1",'
                + '"count":9,'
                + '"addresses":["addr-1","addr-2"],'
                + '"startDate":' + epochMilli + ','
                + '"itemList":['
                + '{"title":"item-1","price":100},'
                + '{"title":"item-2","price":200}'
                + '],'
                + '"type":"THREE"'
                + '}'
        )
    }


    @Test
    void testReturnGeneric01() {

        String userName = "zhang3"
        List<String> userNames = ["li4", "wang5"]

        String date = "2000-01-02T03:04:05.070+08:00"
        LocalDateTime localDateTime = LocalDateTime.parse(date, DateTimeFormatter.ISO_ZONED_DATE_TIME)
        long epochSecond = localDateTime.toEpochSecond(ZoneOffset.UTC)
        Instant instant = localDateTime.toInstant(ZoneOffset.of("+08:00"))
        Date dataObj = Date.from(instant)
        long epochMilli = instant.toEpochMilli()

        HttpHeaders headers = new HttpHeaders()
        HttpEntity<TestPostJsonReq> reqEntity = new HttpEntity<>(null, headers)

        String path = UriComponentsBuilder.fromPath("/test/{oid}/testReturnGeneric")
                .build()
                .expand(["oid": "111"])
                .toUri()
                .toString()
        ResponseEntity<String> respEntity = restTemplate.exchange(path, HttpMethod.GET, reqEntity, String);

        println "======------: " + respEntity
        assertThat(respEntity.statusCode).isEqualTo(HttpStatus.OK)

        // FIXME ??? UTF-8 何来？
        assertThat(respEntity.headers.getContentType()).isEqualTo(MediaType.APPLICATION_JSON_UTF8);
        assertThat(respEntity.body).isEqualTo('{'
                + '"code":"SUCCESS",'
                + '"msg":"hi~",'
                + '"raw":false,'
                + '"data":['
                + '{"title":"i-1","price":111},'
                + '{"title":"i-2","price":222}'
                + ']}'
        )
    }
//
//    /** 静态资源测试: `classpath:/META-INF/resources/` */
//    @Test
//    void metaInfResources01() {
//        String body = restTemplate.getForObject("/a.txt", String.class);
//        assertThat(body).startsWith("/* a.txt */");
//    }
//
//    /** 静态资源测试: `classpath:/resources/` */
//    @Test
//    void resources01() {
//        String body = restTemplate.getForObject("/a.html", String.class);
//        assertThat(body).startsWith("<!-- a.html -->");
//    }
//
//
//
//    @Test
//    void service01() {
//        String path = UriComponentsBuilder.fromPath("/service")
//                .queryParam("a", "3")
//                .queryParam("b", "4")
//                .build()
//                .toUri()
//                .toString()
//
//        String body = restTemplate.getForObject(path, String.class);
//        assertThat(body).startsWith("service 7");
//    }
//
//    @Test
//    void thymeleaf01() {
//        String body = restTemplate.getForObject("/thymeleaf", String.class);
//        assertThat(body).contains("<div>thymeleaf: 1+2=3</div>");
//    }
//
//    /** 测试内容协商: HTML : 通过 HTTP 请求头 "Accept" */
//    @Test
//    void respType01() {
//
//        // 默认请求头：Accept: text/plain, text/plain, application/json, application/json, application/xml, application/xml, application/*+json, application/*+json, text/xml, application/*+xml, text/xml, application/*+xml, */*, */*
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.setAccept([MediaType.TEXT_HTML])
//
//        HttpEntity<Void> reqEntity = new HttpEntity<Void>(null, headers);
//
//        String path = UriComponentsBuilder.fromPath("/respType")
//                .build()
//                .toUri()
//                .toString()
//
//        String body = restTemplate.exchange(path, HttpMethod.GET, reqEntity, String.class);
//
//        assertThat(body).contains("<div>respTypeHtml: respTypeHtml");
//    }
//
//    /** 测试内容协商: JSON : 通过 HTTP 请求头 "Accept" 为空 */
//    @Test
//    void respType02() {
//
//        HttpHeaders headers = new HttpHeaders();
//        // 注意：以下三种方式均返回 json
//        headers.set(HttpHeaders.ACCEPT, "")
//        //headers.setAccept([MediaType.ALL])
//        //headers.setAccept([MediaType.APPLICATION_JSON_UTF8])
//
//        HttpEntity<Void> reqEntity = new HttpEntity<Void>(null, headers);
//
//        String path = UriComponentsBuilder.fromPath("/respType")
////                .queryParam("format", "json")
//                .build()
//                .toUri()
//                .toString()
//
//        String body = restTemplate.exchange(path, HttpMethod.GET, reqEntity, String.class);
//        assertThat(body).contains('{"a":"respType","b":');
//    }
//
//    /** 测试内容协商: JSON : URL上 "format" 参数*/
//    @Test
//    void respType03() {
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.setAccept([MediaType.TEXT_HTML])
//
//        HttpEntity<Void> reqEntity = new HttpEntity<Void>(null, headers);
//
//        String path = UriComponentsBuilder.fromPath("/respType")
//                .queryParam("format", "json")
//                .build()
//                .toUri()
//                .toString()
//
//        String body = restTemplate.exchange(path, HttpMethod.GET, reqEntity, String.class);
//        assertThat(body).contains('{"a":"respType","b":');
//    }
//
//    /** 测试内容协商: JSON : 通过 URL 后缀 */
//    @Test
//    void respType04() {
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.setAccept([MediaType.TEXT_HTML])
//
//        HttpEntity<Void> reqEntity = new HttpEntity<Void>(null, headers);
//
//        String path = UriComponentsBuilder.fromPath("/respType.xml")
//        //.queryParam("format", "xml")
//                .build()
//                .toUri()
//                .toString()
//
//        String body = restTemplate.exchange(path, HttpMethod.GET, reqEntity, String.class);
//        assertThat(body).contains('<a>respType</a><b>');
//    }
//
//    /** 测试内容协商: JSON : 优先级 :
//     * 1. URL 后缀
//     * 2. "format" URL 参数
//     * 3. "Accept" http 请求头
//     */
//    @Test
//    void respType05() {
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.setAccept([MediaType.TEXT_HTML])
//
//        HttpEntity<Void> reqEntity = new HttpEntity<Void>(null, headers);
//
//        String path = UriComponentsBuilder.fromPath("/respType.xml")
//                .queryParam("format", "json")
//                .build()
//                .toUri()
//                .toString()
//
//        String body = restTemplate.exchange(path, HttpMethod.GET, reqEntity, String.class);
//        assertThat(body).contains('<a>respType</a><b>');
//    }
//
//    // -------------
//    /** 测试错误处理：404 - 使用静态模板, HTML `classpath:/resources/error/404.html` */
//    @Test
//    void err404_01() {
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.setAccept([MediaType.TEXT_HTML])
//
//        HttpEntity<Void> reqEntity = new HttpEntity<Void>(null, headers);
//
//        String path = UriComponentsBuilder.fromPath("/err404")
//        //.queryParam("format", "json")
//                .build()
//                .toUri()
//                .toString()
//
//        String body = restTemplate.exchange(path, HttpMethod.GET, reqEntity, String.class);
//        assertThat(body).contains("<span>404 error</span>")
//    }
//
//    /** 测试错误处理：400 - 使用静态模板, HTML `classpath:/resources/error/4xx.html` */
//    @Test
//    void err400_01() {
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.setAccept([MediaType.TEXT_HTML])
//
//        HttpEntity<Void> reqEntity = new HttpEntity<Void>(null, headers);
//
//        String path = UriComponentsBuilder.fromPath("/err400")
//        //.queryParam("format", "json")
//                .build()
//                .toUri()
//                .toString()
//
//        String body = restTemplate.exchange(path, HttpMethod.GET, reqEntity, String.class);
//        assertThat(body).contains("<div>4xx</div>")
//    }
//
//    // -------------
//    /** 测试错误处理：500 - 使用动态模板, HTML `classpath:/templates/error/500.html` */
//    @Test
//    void err500_01() {
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.setAccept([MediaType.TEXT_HTML])
//
//        HttpEntity<Void> reqEntity = new HttpEntity<Void>(null, headers);
//
//        String path = UriComponentsBuilder.fromPath("/err500")
//        //.queryParam("format", "json")
//                .build()
//                .toUri()
//                .toString()
//
//        String body = restTemplate.exchange(path, HttpMethod.GET, reqEntity, String.class);
//        assertThat(body).contains("<div>500.html: 500</div>")
//    }
//
//    /** 测试错误处理：500 - 使用动态模板, JSON `classpath:/templates/error/500.html` */
//    @Test
//    void err500_02() {
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.setAccept([MediaType.TEXT_HTML])
//
//        HttpEntity<Void> reqEntity = new HttpEntity<Void>(null, headers);
//
//        String path = UriComponentsBuilder.fromPath("/err500")
//        //.queryParam("format", "json")
//                .build()
//                .toUri()
//                .toString()
//
//        String body = restTemplate.exchange(path, HttpMethod.GET, reqEntity, String.class);
//        assertThat(body).contains("<div>500.html: 500</div>")
//    }
//
//    /** 测试错误处理：500 - 使用动态模板, 内容协商虽然 url 后缀最高，但抛出异常后会丢失，故会返回 xml */
//    @Test
//    void err500_03() {
//        HttpHeaders headers = new HttpHeaders();
//        headers.setAccept([MediaType.TEXT_HTML])
//
//        HttpEntity<Void> reqEntity = new HttpEntity<Void>(null, headers);
//
//        String path = UriComponentsBuilder.fromPath("/err500.json")
//                .queryParam("format", "xml")
//                .build()
//                .toUri()
//                .toString()
//
//        String body = restTemplate.exchange(path, HttpMethod.GET, reqEntity, String.class);
//        println "=============="
//        println body
//        assertThat(body)
//                .contains("<status>500</status>")
//                .contains("<message>err500</message>")
//                .contains("<path>/err500.json</path>")
//    }
}
