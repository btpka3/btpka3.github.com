package me.test.jdk.javax.xml.stream;

import org.junit.jupiter.api.Test;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.StringReader;

/**
 * @author dangqian.zll
 * @date 2023/9/28
 */
public class SaxTest {

    @Test
    public void testRead() throws Exception {

        String xmlStr = "" +
                "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>\n" +
                "<project>\n" +
                "   <deps>\n" +
                "       <dep>\n" +
                "           <groupId>groupId001</groupId>\n" +
                "           <artifactId>artifactId001</artifactId>\n" +
                "       </dep>\n" +
                "   </deps>\n" +
                "</project>\n";
        InputSource inputSource = new InputSource(new StringReader(xmlStr));

        SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
        SAXParser saxParser = saxParserFactory.newSAXParser();
        MyHandler myHandler = new MyHandler();
        saxParser.parse(inputSource, myHandler);
    }

    public static class MyHandler extends DefaultHandler {
        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
            System.out.println("======== " + qName);
        }
    }
}
