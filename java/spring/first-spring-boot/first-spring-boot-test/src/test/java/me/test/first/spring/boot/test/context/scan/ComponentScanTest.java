package me.test.first.spring.boot.test.context.scan;

import me.test.first.spring.boot.test.context.scan.pkg1.MyService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author dangqian.zll
 * @date 2024/5/17
 */
public class ComponentScanTest {

    @Test
    public void componentScan01() {
        String xmlLocation = "classpath:me/test/first/spring/boot/test/context/scan/ComponentScanTest.xml";
        ClassPathXmlApplicationContext appCtx = new ClassPathXmlApplicationContext(xmlLocation);
        appCtx.start();
        MyService service = (MyService) appCtx.getBean("myService");
        Assertions.assertNotNull(service);
    }
}
