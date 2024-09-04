package me.test.first.spring.boot.test;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * 验证 @TestPropertySource 手动指定配置文件
 *
 * @author dangqian.zll
 * @date 2019-12-04
 */
//@ExtendWith(MockitoExtension.class)
@SpringBootTest
//@ExtendWith(SpringExtension.class)
@TestPropertySource(locations = "classpath:PlaceHolder003Test.properties")
public class PlaceHolder003Test {

    @Value("${a}")
    String a;

    @Test
    public void test01() {
        System.out.println("a = " + a);
    }
}
