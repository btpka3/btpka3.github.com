package com.github.btpka3.hello.maven.plugin;

import org.codehaus.plexus.util.xml.Xpp3Dom;
import org.codehaus.plexus.util.xml.Xpp3DomBuilder;
import org.codehaus.plexus.util.xml.pull.XmlPullParserException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.StringReader;

/**
 * @author dangqian.zll
 * @date 2023/10/24
 * @sse org.codehaus.plexus.util.xml.Xpp3DomBuilder
 * @see Xpp3Dom
 */
public class Xpp3DomTest {


    @Test
    public void strToXpp3Dom() throws XmlPullParserException, IOException {

        Xpp3Dom aaa = Xpp3DomBuilder.build(new StringReader("<aaa p1=\"p001\"><bbb p2=\"p002\">v002</bbb></aaa>"));
        Assertions.assertEquals("aaa", aaa.getName());
        Assertions.assertEquals("p001", aaa.getAttribute("p1"));

        Xpp3Dom bbb = aaa.getChild("bbb");
        Assertions.assertEquals("p002", bbb.getAttribute("p2"));
        Assertions.assertEquals("v002", bbb.getValue());
    }

}
