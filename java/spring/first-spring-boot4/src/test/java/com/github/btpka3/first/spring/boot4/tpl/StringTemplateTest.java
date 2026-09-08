package com.github.btpka3.first.spring.boot4.tpl;

import lombok.SneakyThrows;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;
import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroup;
import org.stringtemplate.v4.STGroupString;

import java.nio.charset.StandardCharsets;
import java.util.Objects;

/**
 *
 * @author dangqian.zll
 * @date 2026/7/28
 */

public class StringTemplateTest {

    @Test
    public void x() {
        ST hello = new ST("Hello, <name>");
        hello.add("name", "World");
        System.out.println(hello.render());
    }

    @SneakyThrows
    @Test
    public void demo2() {
        String stgText = IOUtils.toString(
                Objects.requireNonNull(StringTemplateTest.class.getResourceAsStream("demo.java.stg")),
                StandardCharsets.UTF_8);
        STGroup group = new STGroupString(stgText);
        ST st = group.getInstanceOf("a");
        st.add("xxxClass", JteTest.generateXxxClassBean());
        // ST4 的 {f | body} 子模板中 } 是子模板结束符, 无法在 body 内使用字面 },
        // 所以 { 和 } 通过 ob/cb 参数传入; nl 用作 separator 以在迭代项之间插入换行
        st.add("ob", "{");
        st.add("cb", "}");
        st.add("nl", "\n");
        String result = st.render();
        System.out.println(result);
    }
}
