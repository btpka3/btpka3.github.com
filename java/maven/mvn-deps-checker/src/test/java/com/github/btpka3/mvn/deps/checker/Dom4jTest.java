package com.github.btpka3.mvn.deps.checker;

import org.apache.commons.io.IOUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.junit.jupiter.api.Test;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.nio.charset.StandardCharsets;

/**
 * @author dangqian.zll
 * @date 2020/12/3
 */
public class Dom4jTest {

    String pomXmlFile = "/Users/zll/data0/work/git-repo/ali/ali_security/arm-mbus/pom.xml";

    @Test
    public void test01() throws IOException, DocumentException {
        SAXReader reader = new SAXReader();
        Document document = reader.read(new FileInputStream(new File(pomXmlFile)));

        OutputFormat format = OutputFormat.createPrettyPrint();
        StringWriter sw = new StringWriter();
        XMLWriter writer = new XMLWriter(sw, format);
        writer.write(document);
        writer.close();

        FileWriter fileWriter = new FileWriter("/tmp/Dom4jTest.test01.xml");
        IOUtils.write(sw.toString(), fileWriter);
        fileWriter.close();
    }


    @Test
    public void test02() throws IOException, DocumentException {
        String xml = IOUtils.toString(new FileInputStream(pomXmlFile), StandardCharsets.UTF_8);

        Document document = DocumentHelper.parseText(xml);

        OutputFormat format = OutputFormat.createPrettyPrint();
        StringWriter sw = new StringWriter();
        XMLWriter writer = new XMLWriter(sw);
        writer.write(document);
        writer.close();

        FileWriter fileWriter = new FileWriter("/tmp/Dom4jTest.test01.xml");
        IOUtils.write(sw.toString(), fileWriter);
        fileWriter.close();
    }




}
