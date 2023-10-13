package me.test.first.spring.boot.test;

import com.google.common.collect.ImmutableSet;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;
import org.thymeleaf.context.Context;
import org.thymeleaf.context.IContext;
import org.thymeleaf.context.ITemplateContext;
import org.thymeleaf.dialect.AbstractProcessorDialect;
import org.thymeleaf.dialect.IPostProcessorDialect;
import org.thymeleaf.dialect.IPreProcessorDialect;
import org.thymeleaf.engine.*;
import org.thymeleaf.model.IProcessableElementTag;
import org.thymeleaf.model.IStandaloneElementTag;
import org.thymeleaf.model.IText;
import org.thymeleaf.postprocessor.IPostProcessor;
import org.thymeleaf.postprocessor.PostProcessor;
import org.thymeleaf.preprocessor.IPreProcessor;
import org.thymeleaf.preprocessor.PreProcessor;
import org.thymeleaf.processor.IProcessor;
import org.thymeleaf.processor.element.AbstractAttributeTagProcessor;
import org.thymeleaf.processor.element.IElementTagStructureHandler;
import org.thymeleaf.processor.text.AbstractTextProcessor;
import org.thymeleaf.processor.text.ITextStructureHandler;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.standard.processor.StandardEachTagProcessor;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ITemplateResolver;

import java.io.IOException;
import java.util.*;

/**
 * @author dangqian.zll
 * @date 2023/10/13
 * @see OutputTemplateHandler#handleText(IText)
 * @see IGatheringModelProcessable
 * @see IteratedGatheringModelProcessable
 * @see TemplateModelController#startGatheringIteratedModel(IStandaloneElementTag, ProcessorExecutionVars, String, String, Object)
 * @see ElementTagStructureHandler
 * @see StandardEachTagProcessor
 * @see StandardModelFactory
 * @see ProcessorTemplateHandler
 */

public class ThymeleafTest {

    @Configuration
    public static class Conf {

    }


    @Test
    public void test01() throws IOException {
        //String tplStr = IOUtils.toString(ThymeleafTest.class.getResourceAsStream("ThymeleafTest.01.yaml"), StandardCharsets.UTF_8);
        SpringTemplateEngine tplEngine = templateEngine();

        Map<String, Object> variables = new HashMap<>();
        variables.put("items", Arrays.asList("xxx", "yyy", "zzz"));
        IContext context = new Context(Locale.getDefault(), variables);

        String result = tplEngine.process("ThymeleafTest.01.yaml", context);
        System.out.println("================== start");
        System.out.println(result);
        System.out.println("================== end");

    }


    ITemplateResolver thymeleafTemplateResolver() {
        SpringResourceTemplateResolver templateResolver = new SpringResourceTemplateResolver();
        templateResolver.setPrefix("me/test/first/spring/boot/test/");
        templateResolver.setSuffix(".tpl");
        templateResolver.setTemplateMode("TEXT");
        templateResolver.setApplicationContext(new AnnotationConfigApplicationContext(ThymeleafTest.Conf.class));
        return templateResolver;
    }

    SpringTemplateEngine templateEngine() {
        SpringTemplateEngine templateEngine = new SpringTemplateEngine();
        templateEngine.setTemplateResolver(thymeleafTemplateResolver());
        templateEngine.addDialect(new StripElementWhitespaceDialect());
        templateEngine.addDialect(new MyPPP("aaa111", "hh", 300));
        templateEngine.addDialect(new MyPostP());
        return templateEngine;
    }


    // -------------------------------- PreProcessor

    public static class StripElementWhiteSpace extends AbstractTextProcessor {

        StripElementWhiteSpace(TemplateMode templateMode, int precedence) {
            super(templateMode, precedence);
        }

        @Override
        public void doProcess(ITemplateContext context, IText text, ITextStructureHandler structureHandler) {
            if (!StringUtils.hasText(text.getText())) {
                structureHandler.removeText();
            }
        }
    }

    public static final class StripElementWhitespaceHandler extends AbstractTemplateHandler {

        public StripElementWhitespaceHandler() {
            super();
        }

        @Override
        public void handleText(final IText text) {

            if (getNext() == null) {
                return;
            }

            if (StringUtils.hasText(text.getText())) {
                this.getNext().handleText(text);
            }
        }
    }

    public static class StripElementWhitespaceDialect implements IPreProcessorDialect {
        @Override
        public String getName() {
            return "your_dialect_name";
        }

        @Override
        public int getDialectPreProcessorPrecedence() {
            return 1000;
        }

        @Override
        public Set<IPreProcessor> getPreProcessors() {
            return ImmutableSet.of(new PreProcessor(TemplateMode.TEXT, StripElementWhitespaceHandler.class, getDialectPreProcessorPrecedence()));
        }

    }


    // -------------------------------- Processor


    public static class MyAaProcessor extends AbstractAttributeTagProcessor {

        protected MyAaProcessor(TemplateMode templateMode, String dialectPrefix, String elementName, boolean prefixElementName, String attributeName, boolean prefixAttributeName, int precedence, boolean removeAttribute) {
            super(templateMode, dialectPrefix, elementName, prefixElementName, attributeName, prefixAttributeName, precedence, removeAttribute);
        }

        @Override
        protected void doProcess(ITemplateContext context, IProcessableElementTag tag, AttributeName attributeName, String attributeValue, IElementTagStructureHandler structureHandler) {

            String[] names = attributeName.getCompleteAttributeNames();
            if (names == null || names.length == 0) {
                return;
            }


            boolean match = Arrays.stream(names).anyMatch(name -> Objects.equals("hh:outer-space-trim-mode", name));
            if (!match) {
                return;
            }

            String mode = attributeValue;
            structureHandler.setLocalVariable("outer-space-trim-mode", mode);

        }
    }

    public static class MyPPP extends AbstractProcessorDialect {

        protected MyPPP(String name, String prefix, int processorPrecedence) {
            super(name, prefix, processorPrecedence);
        }

        @Override
        public Set<IProcessor> getProcessors(String dialectPrefix) {
            return new HashSet<>(Arrays.asList(new MyAaProcessor(TemplateMode.TEXT, dialectPrefix, null, false, "outer-space-trim-mode", true, 100, true)));
        }
    }


    // -------------------------------- PostProcessor


    public static final class RemoveOuterSpaceHandler extends AbstractTemplateHandler {

        public RemoveOuterSpaceHandler() {
            super();
        }

        @Override
        public void handleText(final IText text) {
            System.out.println(text);

            // TODO 1: 检查并处理前面一个元素与当前元素之间的空白
            // TODO 2: 检查并处理当前元素与后一个元素之间的空白
        }
    }


    public static class MyPostP implements IPostProcessorDialect {

        @Override
        public int getDialectPostProcessorPrecedence() {
            return 0;
        }

        @Override
        public Set<IPostProcessor> getPostProcessors() {
            return new HashSet<>(Arrays.asList(new PostProcessor(TemplateMode.TEXT, RemoveOuterSpaceHandler.class, 999)));
        }

        @Override
        public String getName() {
            return "aaabbb";
        }
    }


}
