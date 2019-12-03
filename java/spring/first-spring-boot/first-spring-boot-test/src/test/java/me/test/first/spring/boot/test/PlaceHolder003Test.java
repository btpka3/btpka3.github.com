package me.test.first.spring.boot.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author dangqian.zll
 * @date 2019-12-04
 */
@RunWith(SpringRunner.class)
@TestPropertySource(locations = "classpath:PlaceHolder003Test.properties")
public class PlaceHolder003Test {

    @Value("${a}")
    String a;

    @Test
    public void test01() {
        System.out.println("a = " + a);
    }
}
