package me.test.jdk.java.lang;

import org.junit.Assert;
import org.junit.Test;

import java.util.HashSet;

/**
 * @author dangqian.zll
 * @date 2020/9/3
 */
public class AnonymousClassTest {

    @Test
    public void test() {

        MyPojo pojo1 = new MyPojo();
        MyPojo pojo2 = new MyPojo();

        System.out.println("MyPojo.staticProp1.getClass() = " + MyPojo.staticProp1.getClass());
        System.out.println("pojo1.prop1.getClass()        = " + pojo1.prop1.getClass());
        System.out.println("pojo2.prop1.getClass()        = " + pojo2.prop1.getClass());

        // ------------ Anonymous 类级别比较

        // 类级别 Anonymous 类  与 类实例级别的 Anonymous 类 不是同一个类
        Assert.assertNotSame(MyPojo.staticProp1.getClass(), pojo1.prop1.getClass());
        Assert.assertNotSame(MyPojo.staticProp1.getClass(), pojo2.prop1.getClass());
        // FIXME
        Assert.assertSame(pojo1.prop1.getClass(), pojo2.prop1.getClass());

        // 两个
        Assert.assertEquals(MyPojo.staticProp1, pojo1.prop1);

        // ------------ Anonymous 类实例级别比较
        // 类级别 Anonymous 类实例  与 类实例级别的 Anonymous 类实例 是 equals 的
        Assert.assertEquals(MyPojo.staticProp1, pojo1.prop1);
        Assert.assertEquals(MyPojo.staticProp1, pojo2.prop1);

        // 不同 Anonymous 类实例 是 equals 的
        Assert.assertEquals(pojo1.prop1, pojo2.prop1);

    }


    public static class MyPojo {
        static HashSet<String> staticProp1 = new HashSet<String>() {
            private static final long serialVersionUID = 1L;

            {
                add("ctx");
                add("parent");
            }
        };

        HashSet<String> prop1 = new HashSet<String>() {
            private static final long serialVersionUID = 2L;

            {
                add("ctx");
                add("parent");
            }
        };


    }
}
