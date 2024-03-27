package me.test.jdk.javax.xml.parsers;

import org.junit.jupiter.api.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;
import java.io.StringReader;
import java.io.StringWriter;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author dangqian.zll
 * @date 2023/9/27
 */
public class DomTest {
    @Test
    public void x() throws Throwable {

        String xmlStr = "" +
                "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>\n" +
                "<project>\n" +
                "   <deps>\n" +
                "       <dep>\n" +
                "           <groupId>groupId001</groupId>\n" +
                "           <groupId>groupId002</groupId>\n" +
                "           <artifactId>artifactId001</artifactId>\n" +
                "       </dep>\n" +
                "   </deps>\n" +
                "</project>\n";
        InputSource inputSource = new InputSource(new StringReader(xmlStr));

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = factory.newDocumentBuilder();
        Document doc = dBuilder.parse(inputSource);


        XPath xPath = XPathFactory.newInstance().newXPath();

        {
            // 只有第一个元素的文本
            String expression = "/project/deps/dep/groupId";
            String groupId = (String) xPath.compile(expression).evaluate(doc, XPathConstants.STRING);
            System.out.println("========= " + groupId);
            assertThat(groupId).isEqualTo("groupId001");
        }
        {
            // 只有第一个元素
            String expression = "/project/deps/dep/groupId";
            Node node = (Node) xPath.compile(expression).evaluate(doc, XPathConstants.NODE);
            System.out.println("========= " + node);
            assertThat(node.getTextContent()).isEqualTo("groupId001");
        }
        {
            String expression = "/project/deps/dep/groupId";
            NodeList nodeList = (NodeList) xPath.compile(expression).evaluate(doc, XPathConstants.NODESET);
            System.out.println("========= " + nodeList);
            assertThat(nodeList.getLength()).isEqualTo(2);
            assertThat(nodeList.item(0).getTextContent()).isEqualTo("groupId001");
            assertThat(nodeList.item(1).getTextContent()).isEqualTo("groupId002");
        }

        Element projectEle = doc.getDocumentElement();
        Element depsEle = (Element) projectEle.getElementsByTagName("deps").item(0);
        Element depEle = (Element) depsEle.getElementsByTagName("dep").item(0);
        Node cloneNode = depEle.cloneNode(true);
        depsEle.appendChild(cloneNode);


        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer trans = tf.newTransformer();
        StringWriter sw = new StringWriter();
        trans.transform(new DOMSource(doc), new StreamResult(sw));


        System.out.println("========= ");
        System.out.println(sw);
    }
}
