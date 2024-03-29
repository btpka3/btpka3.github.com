package me.test.first.spring.boot.test;

import lombok.Data;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.converter.ConditionalGenericConverter;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterRegistry;
import org.springframework.core.convert.converter.GenericConverter;
import org.springframework.core.convert.support.DefaultConversionService;
import org.springframework.format.Formatter;

import java.util.Date;
import java.util.List;
import java.util.Properties;

/**
 * @author dangqian.zll
 * @date 2023/6/27
 * @see ConditionalGenericConverter
 * @see GenericConverter
 * @see ConversionService
 * @see Converter
 * @see Formatter
 * @see ConverterRegistry#addConverter(Converter)
 * @see DefaultConversionService#addDefaultConverters(ConverterRegistry)
 * @see org.springframework.core.convert.support.StringToPropertiesConverter
 * @see org.springframework.core.convert.support.StringToArrayConverter
 */
public class ConverterTest {

    @Test
    public void test01() {
        String xmlLocation = "classpath*:me/test/first/spring/boot/test/ConverterTest-01.xml";
        ClassPathXmlApplicationContext appCtx = new ClassPathXmlApplicationContext(xmlLocation);
        appCtx.start();
        MyPojo myPojo = (MyPojo) appCtx.getBean("myPojo");
        System.out.println("myPojo=" + myPojo);

        // 注意 properties 中 的Value不要有行尾空格，但等号前后可以有空格
        Assertions.assertEquals("v1", myPojo.getMyProps().get("k1"));
        Assertions.assertEquals("v2", myPojo.getMyProps().get("k2"));
        Assertions.assertEquals("v3", myPojo.getMyProps().get("k3"));

    }

    @Data
    public static class MyPojo {
        private List<String> myList;
        private String myArr;
        private Date myDate;
        private Properties myProps;

    }
}
