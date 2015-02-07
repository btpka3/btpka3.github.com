package me.test;

import java.util.Set;

import javax.validation.Configuration;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import junit.framework.TestCase;

import org.hibernate.validator.constraints.ScriptAssert;
import org.hibernate.validator.messageinterpolation.ResourceBundleMessageInterpolator;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

/**
 * 什么是JSR303-Bean Validation?
 *  是一套类似于Apache Commons Validator，但是可以通过注解进行配置，
 *  也可以通过XML文件进行配置。
 *  http://jcp.org/en/jsr/detail?id=303
 *
 * 参考实现：
 *  Hibernate Validator
 *  http://www.hibernate.org/subprojects/validator.html
 *
 * 注意：
 *  Hibernate Validator 4.2.0.Beta2版本的压缩包中包含中文的文档，在线看时总#￥@的被连接重置。
 *  但这个工程里我使用的是 4.1.0.Final 版
 *
 * 小结：
 *  1. 缺少一些感觉比较常用的Validate，
 *     比如，对字符串类型的日期格式验证。
 *      假如一个字段是"yyyyMMdd"格式的年月日字符串，
 *      可以通过@Pattern用正则表达式限定为全部为字符串，
 *      可是用户仍能发送"20011299" 或 "20010231"这样不存在的日期。
 *      JSR303中只提供了@Past,@Future以对java.util.Date和java.util.Calendar
 *      验证其是否是过去/将来的时间。
 *
 *     比如，如果要保证某个字符串的长队必须为某个固定的值，这时可以使用@Size，
 *     但是@Size是用来保证长度在一个区域内的，有个min和max，
 *     若把min和max设为某个固定值，则消息不太合适。
 *
 *  2. 该规范以及RI实现中只能够对单个字段进行Validate，没有类似Struts中使用的 validateWhen.
 *     若是能够提供一个使用EL表达式的类似实现就好了。
 *     也许这种验证应该拿到业务里进行验证吧？使用Spring的Validator？
 *
 *  RI实现中又扩展了4个注解：@Email,@Length,@NotEmpty,@Range
 *  其中@Length与规范中定义的@Size类似，但仅仅适用于string类型，而@Size则不限于String，还可以用于Collection,Map,Array
 *
 * 工程目录树：
 * /
 * |- src
 * |   |  ValidationMessages.properties
 * |   |- me
 * |       |- test
 * |            Person.java
 * |            PersonTest.java
 * |- lib:
 *      hibernate-validator-4.0.1.GA.jar
 *      jpa-api-2.0.Beta-20090815.jar
 *      log4j-1.2.14.jar
 *      slf4j-api-1.5.6.jar
 *      slf4j-log4j12-1.5.6.jar
 *      validation-api-1.0.0.GA.jar
 *      junit-4.6.jar
 *
 * @author btpka3@163.com
 *
 */
public class PersonTest {

    private Validator validator = null;

    @Before
    public void setUp() {
        if (validator == null) {
            // Configuration<?> config = Validation.byDefaultProvider().configure();
            // config.messageInterpolator(new ResourceBundleMessageInterpolator(ResourceBundle.getBundle("me.test.ValidationMessages") ));
            // validator = config.buildValidatorFactory().getValidator();
            ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
            validator = validatorFactory.getValidator();
        }
    }

    /**
     * 正常 - 全部检验通过
     */
    @SuppressWarnings("unchecked")
    @Test
    public void test001() {

        // ■ 准备环境

        // ■ 准备参数
        Person p = new Person();
        p.setId("U001");
        p.setName("zhang3");
        p.setAge(25);
        p.setAddress("abcdefg");

        // ■ 执行
        Set<ConstraintViolation<Person>> violations = validator.validate(p);

        // ■ 验证结果
        ConstraintViolation<Person>[] arr = violations.toArray(new ConstraintViolation[0]);
        TestCase.assertEquals(0, arr.length);

        // ■ 验证环境
    }

    /**
     * 异常 - id格式不正确
     */
    @SuppressWarnings("unchecked")
    @Test
    public void test002() {

        // ■ 准备环境

        // ■ 准备参数
        Person p = new Person();
        p.setId("U00X");
        p.setName("zhang3");
        p.setAge(25);
        p.setAddress("abcdefg");

        // ■ 执行
        Set<ConstraintViolation<Person>> violations = validator.validate(p);

        // ■ 验证结果
        ConstraintViolation<Person>[] arr = violations.toArray(new ConstraintViolation[0]);
        TestCase.assertEquals(1, arr.length);

        ConstraintViolation<Person> violation = arr[0];
        String propertyPath = violation.getPropertyPath().toString();
        TestCase.assertEquals("id", propertyPath);
        String message = violation.getMessage();
        TestCase.assertEquals("必须匹配正则表达式 \"^U\\d{3}$\"", message);

        // ■ 验证环境
    }

    /**
     * 异常 - name == null
     */
    @SuppressWarnings("unchecked")
    @Test
    public void test003() {

        // ■ 准备环境

        // ■ 准备参数
        Person p = new Person();
        p.setId("U001");
        p.setName(null);
        p.setAge(25);
        p.setAddress("abcdefg");

        // ■ 执行
        Set<ConstraintViolation<Person>> violations = validator.validate(p);

        // ■ 验证结果
        ConstraintViolation<Person>[] arr = violations.toArray(new ConstraintViolation[0]);
        TestCase.assertEquals(1, arr.length);

        ConstraintViolation<Person> violation = arr[0];
        String propertyPath = violation.getPropertyPath().toString();
        TestCase.assertEquals("name", propertyPath);
        String message = violation.getMessage();
        TestCase.assertEquals("用户名不能为null", message);

        // ■ 验证环境
    }

    /**
     * 异常 - age值的范围不正确(0)
     */
    @SuppressWarnings("unchecked")
    @Test
    public void test004() {

        // ■ 准备环境

        // ■ 准备参数
        Person p = new Person();
        p.setId("U001");
        p.setName("zhang3");
        p.setAge(0);
        p.setAddress("abcdefg");

        // ■ 执行
        Set<ConstraintViolation<Person>> violations = validator.validate(p);

        // ■ 验证结果
        ConstraintViolation<Person>[] arr = violations.toArray(new ConstraintViolation[0]);
        TestCase.assertEquals(1, arr.length);

        ConstraintViolation<Person> violation = arr[0];
        String propertyPath = violation.getPropertyPath().toString();
        TestCase.assertEquals("age", propertyPath);
        String message = violation.getMessage();
        TestCase.assertEquals("必须大于等于1", message);

        // ■ 验证环境
    }

    /**
     * 异常 - age值的范围不正确(200)
     */
    @SuppressWarnings("unchecked")
    @Test
    public void test005() {

        // ■ 准备环境

        // ■ 准备参数
        Person p = new Person();
        p.setId("U001");
        p.setName("zhang3");
        p.setAge(200);
        p.setAddress("abcdefg");

        // ■ 执行
        Set<ConstraintViolation<Person>> violations = validator.validate(p);

        // ■ 验证结果
        ConstraintViolation<Person>[] arr = violations.toArray(new ConstraintViolation[0]);
        TestCase.assertEquals(1, arr.length);

        ConstraintViolation<Person> violation = arr[0];
        String propertyPath = violation.getPropertyPath().toString();
        TestCase.assertEquals("age", propertyPath);
        String message = violation.getMessage();
        TestCase.assertEquals("must be less than or equal to 150", message);

        // ■ 验证环境
    }

    /**
     * 异常 - address长度不正确(3)
     */
    @SuppressWarnings("unchecked")
    @Test
    public void test006() {

        // ■ 准备环境

        // ■ 准备参数
        Person p = new Person();
        p.setId("U001");
        p.setName("zhang3");
        p.setAge(25);
        p.setAddress("abc");

        // ■ 执行
        Set<ConstraintViolation<Person>> violations = validator.validate(p);

        // ■ 验证结果
        ConstraintViolation<Person>[] arr = violations.toArray(new ConstraintViolation[0]);
        TestCase.assertEquals(1, arr.length);

        ConstraintViolation<Person> violation = arr[0];
        String propertyPath = violation.getPropertyPath().toString();
        TestCase.assertEquals("address", propertyPath);
        String message = violation.getMessage();
        TestCase.assertEquals("地址长度不能为>=5 && <=10", message);

        // ■ 验证环境
    }

    /**
     * spring message source
     */
    @SuppressWarnings("unchecked")
    @Test
    public void test007() {

        // ■ 准备环境

        ClassPathXmlApplicationContext appContext = new ClassPathXmlApplicationContext("test007.xml");

        LocalValidatorFactoryBean vf = (LocalValidatorFactoryBean) appContext.getBean("vf");
        Validator validator = vf.getValidator();

        // ■ 准备参数
        Person p = new Person();
        p.setId("U001");
        p.setName("zhang3");
        p.setAge(25);
        p.setAddress("abc");

        // ■ 执行
        Set<ConstraintViolation<Person>> violations = validator.validate(p);

        // ■ 验证结果
        ConstraintViolation<Person>[] arr = violations.toArray(new ConstraintViolation[0]);
        TestCase.assertEquals(1, arr.length);

        ConstraintViolation<Person> violation = arr[0];
        String propertyPath = violation.getPropertyPath().toString();
        TestCase.assertEquals("address", propertyPath);
        String message = violation.getMessage();
        TestCase.assertEquals("地址长度不能为>=5 && <=10", message);

        // ■ 验证环境
    }

    /**
     * test ScriptAssert
     */
    @SuppressWarnings("unchecked")
    @Test
    public void test008() {

        // ■ 准备环境

        // ■ 准备参数
        Person p = new Person();
        p.setId("U001");
        p.setName("zhang3");
        p.setAge(25);
        p.setAddress("abcde");
        p.setPwd1("123");
        p.setPwd1("456");

        // ■ 执行
        Set<ConstraintViolation<Person>> violations = validator.validate(p);

        // ■ 验证结果
        ConstraintViolation<Person>[] arr = violations.toArray(new ConstraintViolation[0]);
        TestCase.assertEquals(1, arr.length);

        ConstraintViolation<Person> violation = arr[0];
        String propertyPath = violation.getPropertyPath().toString();
        TestCase.assertEquals("", propertyPath);
        String message = violation.getMessage();
        TestCase.assertEquals("两次密码不一致", message);

        // ■ 验证环境
    }
}