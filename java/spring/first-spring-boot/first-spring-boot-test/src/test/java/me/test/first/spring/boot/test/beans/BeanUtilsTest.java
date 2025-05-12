package me.test.first.spring.boot.test.beans;

import lombok.Data;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.CachedIntrospectionResults;

import java.util.UUID;
import java.util.function.BiConsumer;

/**
 * 注意：这些单侧用例必须 单独启动，因为CachedIntrospectionResults#shouldIntrospectorIgnoreBeaninfoClasses 仅初始化一次。
 *
 * @author dangqian.zll
 * @date 2025/4/23
 * @see java.beans.BeanInfo
 * @see java.beans.GenericBeanInfo
 * @see org.springframework.beans.ExtendedBeanInfo
 * @see CachedIntrospectionResults
 */
public class BeanUtilsTest {
    int loop = 10000;
    MyPojo src = new MyPojo();
    MyPojo dest = new MyPojo();

    @Test
    public void test1() {
        System.setProperty(CachedIntrospectionResults.IGNORE_BEANINFO_PROPERTY_NAME, "false");
        CachedIntrospectionResults.clearClassLoader(this.getClass().getClassLoader());
        run("copyByBeanUtils", loop, BeanUtils::copyProperties, src, dest);
        run("copyByProgramming", loop, this::copyByProgramming, src, dest);
        /*
        spring.beaninfo.ignore=false, copyByBeanUtils      : loop=  10000, cost=    46
        spring.beaninfo.ignore=false, copyByProgramming    : loop=  10000, cost=     1
        */
    }

    @Test
    public void test2() {
        System.setProperty(CachedIntrospectionResults.IGNORE_BEANINFO_PROPERTY_NAME, "true");
        CachedIntrospectionResults.clearClassLoader(this.getClass().getClassLoader());
        run("copyByBeanUtils", loop, BeanUtils::copyProperties, src, dest);
        run("copyByProgramming", loop, this::copyByProgramming, src, dest);
        /*
        spring.beaninfo.ignore=true, copyByBeanUtils      : loop=  10000, cost=    42
        spring.beaninfo.ignore=true, copyByProgramming    : loop=  10000, cost=     1
         */
    }

    protected void run(String name, int loop, BiConsumer<MyPojo, MyPojo> copyFn, MyPojo src, MyPojo dest) {
        long start = System.currentTimeMillis();
        for (int i = 0; i < loop; i++) {
            copyFn.accept(src, dest);
        }
        long end = System.currentTimeMillis();
        long cost = end - start;
        System.out.printf("%s=%s, %-20s : loop=%7d, cost=%6d\n",
                CachedIntrospectionResults.IGNORE_BEANINFO_PROPERTY_NAME,
                System.getProperty(CachedIntrospectionResults.IGNORE_BEANINFO_PROPERTY_NAME, "false"),
                name, loop, cost);
    }


    protected void copyByProgramming(MyPojo src, MyPojo dest) {
        dest.setField1(src.getField1());
        dest.setField2(src.getField2());
        dest.setField3(src.getField3());
        dest.setField4(src.getField4());
        dest.setField5(src.getField5());
    }

    @Data
    public static class MyPojo {
        private String field1 = UUID.randomUUID().toString();
        private String field2 = UUID.randomUUID().toString();
        private String field3 = UUID.randomUUID().toString();
        private String field4 = UUID.randomUUID().toString();
        private String field5 = UUID.randomUUID().toString();
    }
}
