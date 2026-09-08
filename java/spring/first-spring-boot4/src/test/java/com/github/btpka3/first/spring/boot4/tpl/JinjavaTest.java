package com.github.btpka3.first.spring.boot4.tpl;

import com.hubspot.jinjava.Jinjava;
import lombok.SneakyThrows;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 *
 * @author dangqian.zll
 * @date 2026/7/28
 * @see <a href="https://jinja.palletsprojects.com/en/stable/templates/">jinja: Template Designer Documentation</a>
 * @see <a href="https://github.com/HubSpot/jinjava">HubSpot/jinjava</a>
 */
public class JinjavaTest {

    @SneakyThrows
    @Test
    public void demoJava() {
        String tpl = IOUtils.toString(
                Objects.requireNonNull(JinjavaTest.class.getResourceAsStream("demo.java.jinja")),
                StandardCharsets.UTF_8);

        Jinjava jinjava = new Jinjava();
        Map<String, Object> context = new HashMap<>();
        context.put("xxxClass", JteTest.generateXxxClassBean());
        String result = jinjava.render(tpl, context);
        System.out.println(result);
    }
}
