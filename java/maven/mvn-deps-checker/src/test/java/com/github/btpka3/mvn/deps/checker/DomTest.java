package com.github.btpka3.mvn.deps.checker;

import org.junit.jupiter.api.Test;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;

/**
 * @author dangqian.zll
 * @date 2020/12/3
 */
public class DomTest {

    String pomXmlFile = "/Users/zll/data0/work/git-repo/ali/ali_security/arm-mbus/pom.xml";

    @Test
    public void testDom01() throws IOException, ParserConfigurationException, SAXException, TransformerException {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        org.w3c.dom.Document d = db.parse(new FileInputStream(pomXmlFile));

        FileWriter fileWriter = new FileWriter("/tmp/Dom4jTest.test01.xml");
        Source source = new DOMSource(d);
        Result result = new StreamResult(fileWriter);

        Transformer xformer = TransformerFactory.newInstance().newTransformer();
        xformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
        xformer.transform(source, result);
    }

}
