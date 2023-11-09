package me.test.first.spring.boot.test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Xml 中 import 的路径依赖 spring placeholder的值
 *
 * @author dangqian.zll
 * @date 2023/6/25
 * @see <a href="https://spring.io/blog/2011/02/15/spring-3-1-m1-unified-property-management">Spring 3.1 M1: Unified Property Management</a>
 * @see <a href="https://stackoverflow.com/questions/5410017/property-placeholder-for-imports-bean-refs">Property Placeholder for Imports/Bean Refs</a>
 */
public class PlaceHolder005Test {

    /**
     * 不设置 JVM 系统属性，或者 环境变量 时，在import 时无法从 Environment 拿到 importKey 的值，
     * import时: importKey="default"
     * import后: 则由 placeHolder 优先处理，故 myVarTest = "app"
     */
    @Test
    public void testImport01() {
        ClassPathXmlApplicationContext appCtx = new ClassPathXmlApplicationContext("PlaceHolder005Test.xml");
        appCtx.start();
        {
            String myVar = (String) appCtx.getBean("myVar");
            Assertions.assertNotEquals("myVar_app", myVar);
        }

        {
            String myVarTest = (String) appCtx.getBean("myVarTest");
            Assertions.assertEquals("___app___", myVarTest);
        }
    }

    /**
     * 通过设置 JVM 系统属性，在import 时无法从 Environment 可以拿到 importKey 的值，
     * import时: importKey="app"
     * import后: 则由 placeHolder 优先处理，故 myVarTest = "app"
     */
    @Test
    public void testImport02() {

        try {
            // 注意：也可以使用 环境变量 进行该测试，
            System.setProperty("importKey", "app");

            ClassPathXmlApplicationContext appCtx = new ClassPathXmlApplicationContext("PlaceHolder005Test.xml");
            appCtx.start();

            {
                String myVar = (String) appCtx.getBean("myVar");
                Assertions.assertEquals("myVar_app", myVar);
            }

            {
                String myVarTest = (String) appCtx.getBean("myVarTest");
                Assertions.assertEquals("___app___", myVarTest);
            }
        } finally {
            System.clearProperty("importKey");
        }
    }
}
