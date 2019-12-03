package me.test.jdk.javax.management;

import org.junit.Assert;
import org.junit.Test;

import javax.management.*;
import java.lang.management.ManagementFactory;
import java.util.Objects;
import java.util.stream.Stream;

/**
 * @author dangqian.zll
 * @date 2019-09-10
 * @see <a href="https://docs.oracle.com/javase/7/docs/technotes/guides/management/mxbeans.html">Using the Platform MBean Server and Platform MXBeans</a>
 */
public class JmxTest {

    @Test
    public void test() throws MalformedObjectNameException, IntrospectionException, InstanceNotFoundException, ReflectionException, NotCompliantMBeanException, InstanceAlreadyExistsException, MBeanException, AttributeNotFoundException, InvalidAttributeValueException, InterruptedException {
        MBeanServer server = ManagementFactory.getPlatformMBeanServer();
        ObjectName objectName = new ObjectName("aaa:type=" + PersonMBean.class.getSimpleName() + ",tag=bbb,name=p1");

        PersonMBean p1 = new Person();
        {
            p1.setAge(11);
            p1.setName("zhang3");
            server.registerMBean(p1, objectName);
        }

        {
            MBeanInfo mBeanInfo = server.getMBeanInfo(objectName);

            System.out.println(mBeanInfo);

            MBeanAttributeInfo ageInfo = Stream.of(mBeanInfo.getAttributes())
                    .filter(info -> Objects.equals("Age", info.getName()))
                    .findFirst()
                    .orElse(null);

            Assert.assertNotNull(ageInfo);


            Assert.assertEquals(11, server.getAttribute(objectName, "Age"));

            MBeanOperationInfo sayHiInfo = Stream.of(mBeanInfo.getOperations())
                    .filter(info -> Objects.equals("sayHi", info.getName()))
                    .findFirst()
                    .orElse(null);
            Assert.assertNotNull(sayHiInfo);


            Object result = server.invoke(
                    objectName,
                    "sayHi",
                    new Object[]{},
                    new String[]{});

            Assert.assertEquals("hi, zhang3", result);


            server.setAttribute(objectName, new Attribute("Age", 12));


            Assert.assertEquals(12, (Object) p1.getAge());
        }

//        Thread.sleep(60 * 60 * 1000);


    }

    public static interface PersonMBean {
        Integer getAge();

        void setAge(Integer age);

        String getName();

        void setName(String name);

        String sayHi();

    }

    public static class Person implements PersonMBean {
        private Integer age;
        private String name;

        public Integer getAge() {
            return age;
        }

        public void setAge(Integer age) {
            this.age = age;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @Override
        public String sayHi() {
            return "hi, " + name;
        }


    }
}
