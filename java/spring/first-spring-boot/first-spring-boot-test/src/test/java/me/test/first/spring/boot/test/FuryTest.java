package me.test.first.spring.boot.test;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.io.FileUtils;
import org.apache.fury.Fury;
import org.apache.fury.config.Language;
import org.apache.fury.io.FuryInputStream;
import org.apache.fury.memory.MemoryBuffer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.Date;

/**
 * @author dangqian.zll
 * @date 2025/5/12
 * @see FuryInputStream
 * @see MemoryBuffer
 */
public class FuryTest {
    @Test
    public void test01() throws IOException {
        MyPojo myPojo = MyPojo.builder()
                .name("zll")
                .male(true)
                .age(18)
                .birthday(new Date())
                .build();

        Fury fury = Fury.builder().withLanguage(Language.JAVA)
                // Allow to deserialize objects unknown types,
                // more flexible but less secure.
                // .requireClassRegistration(false)
                .build();
        fury.register(MyPojo.class);
        byte[] bytes = fury.serialize(myPojo);

        FileUtils.writeByteArrayToFile(new File("/tmp/FuryTest/test01.fury"), bytes);

        MyPojo myPojo1 = (MyPojo) fury.deserialize(bytes);
        Assertions.assertEquals("zll", myPojo1.getName());
        Assertions.assertTrue(myPojo1.getMale());
        Assertions.assertEquals(18, myPojo1.getAge());
    }

    @Data
    @Builder(toBuilder = true)
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MyPojo implements Serializable {
        private static final long serialVersionUID = 1L;
        private String name;
        private Boolean male;
        private Integer age;
        private Date birthday;
    }
}
