package me.test.first.spring.boot.test;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Map;

/**
 * @date 2019-03-11
 */
@RunWith(SpringRunner.class)
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

        Assert.assertEquals(6, map1.size());
        Assert.assertEquals("a11", map1.get("aaa"));
        Assert.assertNull(map1.get("bbb"));
        Assert.assertEquals("c11", map1.get("ccc"));
        Assert.assertEquals("", map1.get("ddd"));
        Assert.assertEquals("e10", map1.get("eee"));
        Assert.assertNull(map1.get("fff"));
    }


}