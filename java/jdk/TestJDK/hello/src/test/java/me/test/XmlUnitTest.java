package me.test;

import org.junit.jupiter.api.Test;

import static org.xmlunit.assertj3.XmlAssert.assertThat;

/**
 * @author dangqian.zll
 * @date 2023/9/27
 * @see <a href="https://github.com/xmlunit/user-guide/wiki/XPath-Support#multiplenodeassert--singlenodeassert">XPath Support</a>
 */
public class XmlUnitTest {
    @Test
    public void test01() {
        final String xml = "<a><b attr=\"abc\"></b></a>";
        assertThat(xml)
                .nodesByXPath("//a/b/@attr")
                .exist();
        assertThat(xml).hasXPath("//a/b/@attr");

        assertThat(xml).nodesByXPath("//a/b/c")
                .doNotExist();
        assertThat(xml).doesNotHaveXPath("//a/b/c");
        assertThat(xml).nodesByXPath("//a/b")
                .exist();
    }
}
