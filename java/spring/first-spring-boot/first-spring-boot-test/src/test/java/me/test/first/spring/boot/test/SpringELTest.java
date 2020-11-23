package me.test.first.spring.boot.test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.expression.BeanFactoryResolver;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.common.TemplateParserContext;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @date 2019-03-11
 */
public class SpringELTest {

    public static class MyPojo {
        private String name;

        private Integer age;

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

        public String toString() {
            return "MyPojo[name=" + name + "]";
        }
    }


    @Test
    public void testStaticMethod01() {
        ExpressionParser parser = new SpelExpressionParser();


        Expression exp = parser.parseExpression(
                "number:#{T(java.lang.Math).random()}",
                new TemplateParserContext()
        );
        String str = exp.getValue(String.class);

        Assertions.assertTrue(str.startsWith("number:0."));
    }


    @Test
    public void testRoot01() {
        ExpressionParser parser = new SpelExpressionParser();

        MyPojo pojo = new MyPojo();
        pojo.setName("zhang3");

        EvaluationContext context = new StandardEvaluationContext(pojo);
        Expression exp = parser.parseExpression("name");
        String name = (String) exp.getValue(context);

        Assertions.assertEquals("zhang3", name);
    }


    @Test
    public void testRoot02() {
        ExpressionParser parser = new SpelExpressionParser();

        MyPojo pojo = new MyPojo();
        pojo.setName("zhang3");

        EvaluationContext context = new StandardEvaluationContext(pojo);
        Expression exp = parser.parseExpression("#this.toString()");
        String name = (String) exp.getValue(context);

        Assertions.assertEquals("MyPojo[name=zhang3]", name);
    }

    @Test
    public void testPrimes() {
        List<Integer> primes = Arrays.asList(2, 3, 5, 7, 11, 13, 17);

        EvaluationContext context = new StandardEvaluationContext();
        context.setVariable("primes", primes);

        ExpressionParser parser = new SpelExpressionParser();
        Expression exp = parser.parseExpression("#primes.?[#this>10]");
        List<Integer> primesGreaterThanTen = (List<Integer>) exp.getValue(context);

        Assertions.assertEquals(3, primesGreaterThanTen.size());
        Assertions.assertTrue(primesGreaterThanTen.contains(11));
        Assertions.assertTrue(primesGreaterThanTen.contains(13));
        Assertions.assertTrue(primesGreaterThanTen.contains(17));
    }

    @Test
    public void testVar01() {
        ExpressionParser parser = new SpelExpressionParser();

        MyPojo pojo = new MyPojo();
        pojo.setName("zhang3");

        EvaluationContext context = new StandardEvaluationContext(pojo);

        context.setVariable("age", 35);
        Expression exp = parser.parseExpression("name+#age");
        String name = (String) exp.getValue(context);

        Assertions.assertEquals("zhang335", name);
    }


    @Test
    public void testFunction01() throws NoSuchMethodException {
        List<Integer> primes = Arrays.asList(2, 3, 5, 7, 11, 13, 17);

        StandardEvaluationContext context = new StandardEvaluationContext();
        context.setVariable("str", " a b  c ");
        context.registerFunction("trimAllWhitespace",
                StringUtils.class.getDeclaredMethod("trimAllWhitespace", new Class[]{String.class}));


        ExpressionParser parser = new SpelExpressionParser();
        Expression exp = parser.parseExpression("#trimAllWhitespace(#str)");
        String newStr = (String) exp.getValue(context);

        Assertions.assertEquals("abc", newStr);
    }


    @Test
    public void testBean01() {

        GenericApplicationContext appCtx = new GenericApplicationContext();
        appCtx.registerBean("str", String.class, () -> "zhang3");
        appCtx.refresh();

        BeanFactoryResolver beanResolver = new BeanFactoryResolver(appCtx);

        StandardEvaluationContext context = new StandardEvaluationContext();
        context.setBeanResolver(beanResolver);

        ExpressionParser parser = new SpelExpressionParser();
        Expression exp = parser.parseExpression("'hi,'+@str");
        String newStr = (String) exp.getValue(context);

        Assertions.assertEquals("hi,zhang3", newStr);

    }

    @Test
    public void testCollectionProjection01() {


        List<MyPojo> list = new ArrayList<>();
        {

            MyPojo pojo = new MyPojo();
            pojo.setName("zhang3");
            pojo.setAge(11);
            list.add(pojo);
        }

        {

            MyPojo pojo = new MyPojo();
            pojo.setName("li4");
            pojo.setAge(21);
            list.add(pojo);
        }

        {

            MyPojo pojo = new MyPojo();
            pojo.setName("wang5");
            pojo.setAge(31);
            list.add(pojo);
        }


        StandardEvaluationContext context = new StandardEvaluationContext();
        context.setVariable("list", list);

        ExpressionParser parser = new SpelExpressionParser();
        Expression exp = parser.parseExpression("#list.?[age>20].![name]");
        List<String> newList = (List<String>) exp.getValue(context);

        Assertions.assertEquals(2, newList.size());
        Assertions.assertTrue(newList.contains("li4"));
        Assertions.assertTrue(newList.contains("wang5"));

    }


}