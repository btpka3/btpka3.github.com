package me.test.first.spring.boot.test;

import lombok.Getter;
import org.hamcrest.Matcher;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.expression.BeanFactoryResolver;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.common.CompositeStringExpression;
import org.springframework.expression.common.LiteralExpression;
import org.springframework.expression.common.TemplateParserContext;
import org.springframework.expression.spel.SpelNode;
import org.springframework.expression.spel.ast.*;
import org.springframework.expression.spel.standard.SpelExpression;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.util.StringUtils;

import javax.annotation.Nullable;
import java.util.*;
import java.util.function.Consumer;

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
        {
            MatcherAssert.assertThat(exp, Matchers.instanceOf(CompositeStringExpression.class));
            CompositeStringExpression compositeStringExpression = (CompositeStringExpression) exp;
            Expression[] expressions = compositeStringExpression.getExpressions();
            MatcherAssert.assertThat(expressions.length, Matchers.equalTo(2));

            {
                MatcherAssert.assertThat(expressions[0], Matchers.instanceOf(LiteralExpression.class));
                LiteralExpression exp0 = (LiteralExpression) expressions[0];
                MatcherAssert.assertThat(exp0.getExpressionString(), Matchers.equalTo("number:"));
            }

            {
                MatcherAssert.assertThat(expressions[1], Matchers.instanceOf(SpelExpression.class));
                SpelExpression exp1 = (SpelExpression) expressions[1];
                MatcherAssert.assertThat(exp1.getAST().getChildCount(), Matchers.equalTo(2));
            }
        }

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


    public static class Matchable<T> {
        public T obj;

        public Matchable(T obj) {
            this.obj = obj;
        }

        public boolean matches(Matcher matcher) {
            return matcher.matches(obj);
        }

        public static <T> Matchable<T> of(T obj) {
            return new Matchable(obj);
        }
    }

    public static class MyRoot {

        @Getter
        public Set<String> props = new HashSet<>();

        public Matchable p(String s) {
            props.add(s);
            return Matchable.of(s);
        }

        public Object kv(String key) {
            return Matchable.of(key);
        }

        public Matcher<String> containsString(String str) {
            return Matchers.containsString(str);
        }

        public <T> Matcher<T> in(java.util.Collection<T> collection) {
            return Matchers.in(collection);
        }

        public <T> Matcher<T> equalTo(T operand) {
            return Matchers.equalTo(operand);
        }

        public int getNum() {
            return 1;
        }
    }


    @Test
    public void getGetter01() {
        MyRoot rootObj = new MyRoot();

        StandardEvaluationContext context = new StandardEvaluationContext(rootObj);
        ExpressionParser expressionParser = new SpelExpressionParser();
        Expression exp = expressionParser.parseExpression("num + 2");
        Object value = exp.getValue(context);
        System.out.println("value = " + value);


    }

    @Test
    public void testHamcrest00() {
        MyRoot rootObj = new MyRoot();

        StandardEvaluationContext context = new StandardEvaluationContext(rootObj);
        ExpressionParser expressionParser = new SpelExpressionParser();
        Expression exp = expressionParser.parseExpression("p('aaa')  && a.b . c  .p('bbb')  && x.y  .p('bbb').length()");
        System.out.println(exp);


    }

    @Test
    public void testHamcrest01() {
        MyRoot rootObj = new MyRoot();

        StandardEvaluationContext context = new StandardEvaluationContext(rootObj);
        ExpressionParser expressionParser = new SpelExpressionParser();
        Expression exp = expressionParser.parseExpression("p('a'+'aa').matches(containsString('aaa')) &&   p( 'bbb'  ).matches(in({'bbb','ccc'})) ");

        {
            Set<String> props = new LinkedHashSet<>(8);

            SpelNodeVisitor visitor = new SpelNodeVisitor() {
                public void visit(MethodReference methodReference) {
                    if (!Objects.equals("p", methodReference.getName())) {
                        return;
                    }
                    if (methodReference.getChildCount() != 1) {
                        System.out.println("err001");
                        return;
                    }
                    SpelNode arg0 = methodReference.getChild(0);
                    if (!(arg0 instanceof StringLiteral)) {
                        System.out.println("err002");
                        return;
                    }
                    StringLiteral str0 = (StringLiteral) arg0;
                    props.add(str0.getOriginalValue());
                }
            };

            SpelNodeWalker walker = new SpelNodeWalker(visitor);
            walker.walking(exp);

            System.out.println("props find by walker : " + props);
        }

        {
            Object result = exp.getValue(context);
            System.out.println("result : " + result);
            System.out.println("props find by rooObj : " + rootObj.getProps());
        }
    }
//
//    protected void findProps(Set<String> resultSet, Expression expression) {
//        if (expression instanceof CompositeStringExpression) {
//            for (Expression expr : ((CompositeStringExpression) expression).getExpressions()) {
//                findProps(resultSet, expr);
//            }
//        } else if (expression instanceof SpelExpression) {
//            findProps(resultSet, ((SpelExpression) expression).getAST());
//        }
//    }
//
//    protected void findProps(@Nonnull Set<String> resultSet, @Nullable SpelNode spelNode) {
//        if (spelNode == null) {
//            return;
//        }
//
//
//        if (spelNode instanceof Operator) {
//            Operator op = (Operator) spelNode;
//            findProps(resultSet, op.getLeftOperand());
//            findProps(resultSet, op.getRightOperand());
//        }
//        if (spelNode instanceof Operator) {
//            Operator op = (Operator) spelNode;
//            findProps(resultSet, op.getLeftOperand());
//            findProps(resultSet, op.getRightOperand());
//        }
//
//        // Assign
//        // BeanReference
//
//
//    }

    public static class SpelNodeWalker {
        Map<Class, Consumer> reg = new HashMap<>();
        private SpelNodeVisitor visitor;

        public SpelNodeWalker(SpelNodeVisitor visitor) {
            this.visitor = visitor;
            Map<Class, Consumer> reg = new HashMap<>();
            reg.put(Assign.class, (Consumer<Assign>) visitor::visit);
            reg.put(BeanReference.class, (Consumer<BeanReference>) visitor::visit);
            reg.put(BooleanLiteral.class, (Consumer<BooleanLiteral>) visitor::visit);
            reg.put(CompoundExpression.class, (Consumer<CompoundExpression>) visitor::visit);
            reg.put(ConstructorReference.class, (Consumer<ConstructorReference>) visitor::visit);
            reg.put(Elvis.class, (Consumer<Elvis>) visitor::visit);
            reg.put(FloatLiteral.class, (Consumer<FloatLiteral>) visitor::visit);
            reg.put(FunctionReference.class, (Consumer<FunctionReference>) visitor::visit);
            reg.put(Identifier.class, (Consumer<Identifier>) visitor::visit);
            reg.put(Indexer.class, (Consumer<Indexer>) visitor::visit);
            reg.put(InlineList.class, (Consumer<InlineList>) visitor::visit);
            reg.put(InlineMap.class, (Consumer<InlineMap>) visitor::visit);
            reg.put(IntLiteral.class, (Consumer<IntLiteral>) visitor::visit);
            reg.put(Literal.class, (Consumer<Literal>) visitor::visit);
            reg.put(LongLiteral.class, (Consumer<LongLiteral>) visitor::visit);
            reg.put(MethodReference.class, (Consumer<MethodReference>) visitor::visit);
            reg.put(NullLiteral.class, (Consumer<NullLiteral>) visitor::visit);
            reg.put(OpAnd.class, (Consumer<OpAnd>) visitor::visit);
            reg.put(OpDec.class, (Consumer<OpDec>) visitor::visit);
            reg.put(OpDivide.class, (Consumer<OpDivide>) visitor::visit);
            reg.put(OpEQ.class, (Consumer<OpEQ>) visitor::visit);
            reg.put(Operator.class, (Consumer<Operator>) visitor::visit);
            reg.put(OperatorBetween.class, (Consumer<OperatorBetween>) visitor::visit);
            reg.put(OperatorInstanceof.class, (Consumer<OperatorInstanceof>) visitor::visit);
            reg.put(OperatorMatches.class, (Consumer<OperatorMatches>) visitor::visit);
            reg.put(OperatorNot.class, (Consumer<OperatorNot>) visitor::visit);
            reg.put(OperatorPower.class, (Consumer<OperatorPower>) visitor::visit);
            reg.put(OpGE.class, (Consumer<OpGE>) visitor::visit);
            reg.put(OpGT.class, (Consumer<OpGT>) visitor::visit);
            reg.put(OpInc.class, (Consumer<OpInc>) visitor::visit);
            reg.put(OpLE.class, (Consumer<OpLE>) visitor::visit);
            reg.put(OpLT.class, (Consumer<OpLT>) visitor::visit);
            reg.put(OpMinus.class, (Consumer<OpMinus>) visitor::visit);
            reg.put(OpModulus.class, (Consumer<OpModulus>) visitor::visit);
            reg.put(OpMultiply.class, (Consumer<OpMultiply>) visitor::visit);
            reg.put(OpNE.class, (Consumer<OpNE>) visitor::visit);
            reg.put(OpOr.class, (Consumer<OpOr>) visitor::visit);
            reg.put(OpPlus.class, (Consumer<OpPlus>) visitor::visit);
            reg.put(Projection.class, (Consumer<Projection>) visitor::visit);
            reg.put(PropertyOrFieldReference.class, (Consumer<PropertyOrFieldReference>) visitor::visit);
            reg.put(QualifiedIdentifier.class, (Consumer<QualifiedIdentifier>) visitor::visit);
            reg.put(RealLiteral.class, (Consumer<RealLiteral>) visitor::visit);
            reg.put(Selection.class, (Consumer<Selection>) visitor::visit);
            reg.put(SpelNodeImpl.class, (Consumer<SpelNodeImpl>) visitor::visit);
            reg.put(StringLiteral.class, (Consumer<StringLiteral>) visitor::visit);
            reg.put(Ternary.class, (Consumer<Ternary>) visitor::visit);
            reg.put(TypeReference.class, (Consumer<TypeReference>) visitor::visit);
            reg.put(VariableReference.class, (Consumer<VariableReference>) visitor::visit);
            this.reg = reg;
        }


        public void walking(Expression expression) {
            if (expression instanceof CompositeStringExpression) {
                for (Expression expr : ((CompositeStringExpression) expression).getExpressions()) {
                    walking(expr);
                }
            } else if (expression instanceof SpelExpression) {
                walking(((SpelExpression) expression).getAST());
            }
        }


        public void walking(@Nullable SpelNode spelNode) {
            if (spelNode == null) {
                return;
            }
            reg.entrySet().stream()
                    .filter(entry -> entry.getKey().isInstance(spelNode))
                    .forEach(entry -> entry.getValue().accept(spelNode));

            for (int i = 0; i < spelNode.getChildCount(); i++) {
                walking(spelNode.getChild(i));
            }
        }
    }

    public interface SpelNodeVisitor {

        default void visit(Assign assign) {
        }

        default void visit(BooleanLiteral booleanLiteral) {
        }

        default void visit(CompoundExpression compoundExpression) {
        }

        default void visit(ConstructorReference constructorReference) {
        }

        default void visit(Elvis elvis) {
        }

        default void visit(FloatLiteral floatLiteral) {
        }

        default void visit(FunctionReference functionReference) {
        }

        default void visit(Identifier identifier) {
        }

        default void visit(Indexer Indexer) {
        }

        default void visit(InlineList inlineList) {
        }

        default void visit(InlineMap inlineMap) {
        }


        default void visit(IntLiteral intLiteral) {
        }

        default void visit(Literal literal) {
        }

        default void visit(LongLiteral longLiteral) {
        }

        default void visit(MethodReference methodReference) {
        }

        default void visit(NullLiteral nullLiteral) {
        }

        default void visit(OpAnd opAnd) {
        }

        default void visit(OpDec opDec) {
        }

        default void visit(OpDivide opDivide) {
        }

        default void visit(OpEQ opEQ) {
        }

        default void visit(Operator operator) {
        }


        default void visit(OperatorBetween operatorBetween) {
        }

        default void visit(OperatorInstanceof operatorInstanceof) {
        }

        default void visit(OperatorMatches operatorMatches) {
        }

        default void visit(OperatorNot operatorNot) {
        }

        default void visit(OperatorPower operatorPower) {
        }

        default void visit(OpGE opGE) {
        }

        default void visit(OpGT opGT) {
        }

        default void visit(OpInc opInc) {
        }

        default void visit(OpLE opLE) {
        }

        default void visit(OpLT opLT) {
        }

        default void visit(OpMinus opMinus) {
        }

        default void visit(OpModulus opModulus) {
        }

        default void visit(OpNE opNE) {
        }

        default void visit(OpOr opOr) {
        }

        default void visit(OpPlus opPlus) {
        }

        default void visit(Projection projection) {
        }

        default void visit(PropertyOrFieldReference propertyOrFieldReference) {
        }

        default void visit(QualifiedIdentifier qualifiedIdentifier) {
        }

        default void visit(RealLiteral realLiteral) {
        }

        default void visit(Selection selection) {
        }

        default void visit(SpelNodeImpl spelNodeImpl) {
        }

        default void visit(StringLiteral stringLiteral) {
        }

        default void visit(Ternary ternary) {
        }

        default void visit(TypeReference typeReference) {
        }

        default void visit(VariableReference variableReference) {
        }

    }


}