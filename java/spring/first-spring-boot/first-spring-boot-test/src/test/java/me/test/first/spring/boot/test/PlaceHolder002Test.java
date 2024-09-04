package me.test.first.spring.boot.test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;

/**
 * 验证使用自定义 PropertyPlaceholderConfigurer
 */
@SpringBootTest
@Import({
        PlaceHolder002Test.Conf.class
})
public class PlaceHolder002Test {


    @Configuration
    public static class Conf {


        /**
         * 请在你的Spring placeHolder 中进行相应的配置
         */
        @Bean
        PropertyPlaceholderConfigurer propertyPlaceholderConfigurer() {
            PropertyPlaceholderConfigurer p = new PropertyPlaceholderConfigurer();

            Properties props = new Properties();
            // 报空指针异常
            //props.setProperty("conf.bbb", null);
            props.setProperty("conf.ccc", "c11");
            props.setProperty("conf.ddd", "");
            props.setProperty("conf.eee", "e10");
            props.setProperty("conf.fff", "@null");
            p.setProperties(props);

            p.setNullValue("@null");
            return p;
        }

//            <util:
//        map id = "map1"
//        key-type="java.lang.String"value-type="java.lang.String">
//        <entry key="aaa"value="a11"/>
//        <entry key="bbb"value="${conf.bbb:#{null}}"/>
//        <entry key="ccc"value="${conf.ccc:#{'c11'}}"/>
//        <entry key="ddd"value="${conf.ddd:d11}"/>
//        <entry key="eee"value="${conf.eee:e11}"/>
//        <entry key="fff"value="${conf.fff}"/>
//<!--        <entry key="ggg"value="${conf.ggg}"/>-->
//    </util:map>

        @Bean
        Map<String, String> map1(


                @Value("${conf.bbb:#{null}}")
                        String bbb,
                @Value("${conf.ccc:#{'c11'}}")
                        String ccc,
                @Value("${conf.ddd:d11}")
                        String ddd,
                @Value("${conf.eee:e11}")
                        String eee,
                @Value("${conf.fff}")
                        String fff
        ) {
            Map<String, String> map = new LinkedHashMap<>();
            map.put("aaa", "a11");
            map.put("bbb", bbb);
            map.put("ccc", ccc);
            map.put("ddd", ddd);
            map.put("eee", eee);
            map.put("fff", fff);
            return map;
        }


    }

    @Autowired
    Map<String, String> map1;


    @Test
    public void test01() {
        Assertions.assertEquals(6, map1.size());
        Assertions.assertEquals("a11", map1.get("aaa"));
        Assertions.assertNull(map1.get("bbb"));
        Assertions.assertEquals("c11", map1.get("ccc"));
        Assertions.assertEquals("", map1.get("ddd"));
        Assertions.assertEquals("e10", map1.get("eee"));
        Assertions.assertNull(map1.get("fff"));
    }


}