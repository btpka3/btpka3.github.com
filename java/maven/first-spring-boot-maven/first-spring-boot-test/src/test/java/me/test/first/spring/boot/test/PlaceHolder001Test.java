package me.test.first.spring.boot.test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import java.util.Map;

/**
 * @date 2019-03-11
 */
@SpringBootTest
@ContextConfiguration//("PlaceHolder001Test-context.xml")
//@ImportResource("classpath*:spring/application_120.xml")
//@TestPropertySource(locations = "classpath:PlaceHolder001Test.properties")
public class PlaceHolder001Test {

    // bash: ./gradlew -i :first-spring-boot-test:test --tests me.test.first.spring.boot.test.PlaceHolder001Test

    @Autowired
    @Qualifier("map1")
    Map<String, String> map1;


    @Test
    public void testStaticMethod01() {
        System.out.println(PlaceHolder001Test.class.getResource(".").getFile());

        Assertions.assertEquals(6, map1.size());
        Assertions.assertEquals("a11", map1.get("aaa"));
        Assertions.assertNull(map1.get("bbb"));
        Assertions.assertEquals("c11", map1.get("ccc"));
        Assertions.assertEquals("", map1.get("ddd"));
        Assertions.assertEquals("e10", map1.get("eee"));
        Assertions.assertNull(map1.get("fff"));
    }


}