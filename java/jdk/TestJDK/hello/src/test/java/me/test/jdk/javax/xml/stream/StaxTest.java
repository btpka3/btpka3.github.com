package me.test.jdk.javax.xml.stream;

import java.io.StringReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamReader;
import org.junit.jupiter.api.Test;

/**
 * @author dangqian.zll
 * @date 2023/9/28
 */
public class StaxTest {

    @Test
    public void testRead() throws Exception {

        String xmlStr = "" + "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>\n"
                + "<project>\n"
                + "   <deps>\n"
                + "       <dep>\n"
                + "           <groupId>groupId001</groupId>\n"
                + "           <artifactId>artifactId001</artifactId>\n"
                + "       </dep>\n"
                + "   </deps>\n"
                + "</project>\n";
        XMLInputFactory factory = XMLInputFactory.newInstance();
        XMLStreamReader streamReader = factory.createXMLStreamReader(new StringReader(xmlStr));
        while (streamReader.hasNext()) {
            int eventType = streamReader.next();
            if (eventType == XMLStreamConstants.START_ELEMENT) {
                String localName = streamReader.getLocalName();
                System.out.println("======== " + localName);
            }
        }
    }
}
