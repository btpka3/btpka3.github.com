package me.test;

import java.io.StringReader;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.dom.DOMSource;
import org.junit.jupiter.api.Test;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import static org.xmlunit.assertj3.XmlAssert.assertThat;

/**
 * @author dangqian.zll
 * @date 2023/9/27
 * @see <a href="https://github.com/xmlunit/user-guide/wiki/XPath-Support#multiplenodeassert--singlenodeassert">XPath Support</a>
 * @see <a href="https://github.com/xmlunit/user-guide/wiki/Providing-Input-to-XMLUnit">Providing Input to XMLUnit</a>
 */
public class XmlUnitTest {
    final String xmlStr = "<a><b attr=\"abc\"></b></a>";

    protected void assert01(Object obj) {
        assertThat(obj)
                .nodesByXPath("//a/b/@attr")
                .exist();
        assertThat(obj).hasXPath("//a/b/@attr");

        assertThat(obj).nodesByXPath("//a/b/c")
                .doNotExist();
        assertThat(obj).doesNotHaveXPath("//a/b/c");
        assertThat(obj).nodesByXPath("//a/b")
                .exist();
    }


    /**
     * input=String
     */
    @Test
    public void test01() {
        assert01(xmlStr);
    }

    /**
     * input=DOMSource
     */
    @Test
    public void test02() throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = factory.newDocumentBuilder();
        InputSource inputSource = new InputSource(new StringReader(xmlStr));
        Document doc = dBuilder.parse(inputSource);
        DOMSource domSource = new DOMSource(doc);

        assert01(domSource);
    }

    /**
     * 检查报错的异常类型== java.lang.AssertionError， 故仅仅适用于单元测试，不适合业务正常逻辑。
     */
    @Test
    public void testError01() {
        assertThat(xmlStr)
                .nodesByXPath("//a/b/@attr")
                .doNotExist();
    }
}
