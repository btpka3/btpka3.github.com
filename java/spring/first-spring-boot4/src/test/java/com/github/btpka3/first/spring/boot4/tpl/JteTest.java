package com.github.btpka3.first.spring.boot4.tpl;

import gg.jte.CodeResolver;
import gg.jte.ContentType;
import gg.jte.TemplateEngine;
import gg.jte.TemplateOutput;
import gg.jte.output.StringOutput;
import gg.jte.resolve.ResourceCodeResolver;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

/**
 *
 * @author dangqian.zll
 * @date 2026/7/28
 */
public class JteTest {

    @Test
    public void demoHtml() {
        Page page = new Page();
        page.setTitle("demoTitle");
        page.setDescription("demoDescription");
        CodeResolver codeResolver = new ResourceCodeResolver("com/github/btpka3/first/spring/boot4/tpl"); // This is the directory where your .jte files are located.
        TemplateEngine templateEngine = TemplateEngine.create(codeResolver, ContentType.Html); // Two choices: Plain or Html
        TemplateOutput output = new StringOutput();
        templateEngine.render("demo.html.jte", page, output);
        System.out.println(output);
    }

    public static XxxClass generateXxxClassBean() {
        XxxClass xxx = new XxxClass();
        xxx.setPkg("me.test");
        xxx.setName("Demo");
        xxx.setFieldList(Arrays.asList(
                XxxField.builder().type("String").name("name").getterLogic("return \"zhang3\"; ").build(),
                XxxField.builder().type("int").name("age").getterLogic("return 1 + 2; ").build()
        ));
        return xxx;
    }

    @Test
    public void demoJava() {
        XxxClass xxx = generateXxxClassBean();
        CodeResolver codeResolver = new ResourceCodeResolver("com/github/btpka3/first/spring/boot4/tpl"); // This is the directory where your .jte files are located.
        TemplateEngine templateEngine = TemplateEngine.create(codeResolver, ContentType.Html); // Two choices: Plain or Html
        //templateEngine.setTrimControlStructures(true);
        //templateEngine.setBinaryStaticContent(true);
        TemplateOutput output = new StringOutput();
        templateEngine.render("demo.java.jte", xxx, output);
        System.out.println(output);
    }

}
