package com.github.btpka3.mvn.deps.checker;

//import com.sun.org.apache.xerces.internal.dom.AttrImpl;
//import com.sun.org.apache.xerces.internal.dom.AttributeMap;
//import com.sun.org.apache.xerces.internal.dom.CoreDocumentImpl;
//import com.sun.org.apache.xerces.internal.dom.ElementImpl;
//import com.sun.org.apache.xml.internal.serialize.OutputFormat;
//import com.sun.org.apache.xml.internal.serialize.XMLSerializer;
import org.apache.commons.io.output.StringBuilderWriter;
import org.apache.xerces.dom.AttrImpl;
import org.apache.xerces.dom.AttributeMap;
import org.apache.xerces.dom.CoreDocumentImpl;
import org.apache.xerces.dom.ElementImpl;
import org.apache.xml.serialize.OutputFormat;
import org.apache.xml.serialize.XMLSerializer;
import org.junit.jupiter.api.Test;
import org.w3c.dom.*;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

/**
 * @author dangqian.zll
 * @date 2021/2/18
 */
public class KeepAttrOrderTest {

//    @Test
//    public void test001() throws Exception {
//        InputSource is = new InputSource(new InputStreamReader(KeepAttrOrderTest.class.getResourceAsStream("KeepAttrOrderTest.01.xml"), StandardCharsets.UTF_8));
//
//        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
//        DocumentBuilder builder = dbFactory.newDocumentBuilder();
//        Document document = dbFactory.newDocumentBuilder().parse(is);
//        Element sourceRoot = document.getDocumentElement();
//
//
//        Document outDocument = builder.newDocument();
//
//
//
//
//        Element outRoot = outDocument.createElementNS(sourceRoot.getNamespaceURI(), sourceRoot.getTagName());
//        outDocument.appendChild(outRoot);
//
//        copyAtts(sourceRoot.getAttributes(), outRoot);
//        copyElement(sourceRoot.getChildNodes(), outRoot, outDocument);
//    }

//
//    protected String toString(Document document) throws TransformerException {
//        StringWriter writer = new StringWriter();
//        Source source = new DOMSource(document);
//        Transformer transformer = TransformerFactory.newInstance().newTransformer();
//        transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
////        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
////        System.out.println(transformer.getOutputProperties());
//        //transformer.setOutputProperty(OutputKeys.STANDALONE, "yes");
//        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
//        transformer.transform(source, new StreamResult(writer));
//        return writer.toString();
//    }


    private List<String> sortAtts = Arrays.asList("last_name", "first_name");


    @Test
    public void format() throws Exception {
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = dbFactory.newDocumentBuilder();
        Document outDocument = builder.newDocument();
        InputSource is = new InputSource(new InputStreamReader(KeepAttrOrderTest.class.getResourceAsStream("KeepAttrOrderTest.01.xml"), StandardCharsets.UTF_8));

        Document document = dbFactory.newDocumentBuilder().parse(is);
        Element sourceRoot = document.getDocumentElement();
        Element outRoot = outDocument.createElementNS(sourceRoot.getNamespaceURI(), sourceRoot.getTagName());
        outDocument.appendChild(outRoot);

        copyAtts(sourceRoot.getAttributes(), outRoot);
        copyElement(sourceRoot.getChildNodes(), outRoot, outDocument);
        {

            StringBuilderWriter writer = new StringBuilderWriter();
            OutputFormat format = new OutputFormat();
            format.setLineWidth(0);
            format.setIndenting(false);
            format.setIndent(2);

            XMLSerializer serializer = new XMLSerializer(writer, format);
            serializer.serialize(outDocument);
            System.out.println(writer.toString());
        }
    }

    private void copyElement(NodeList nodes, Element parent, Document document) {
        for (int i = 0; i < nodes.getLength(); i++) {
            Node node = nodes.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = new ElementImpl((CoreDocumentImpl) document, node.getNodeName()) {
                    @Override
                    public NamedNodeMap getAttributes() {
                        return new AttributeSortedMap(this, (AttributeMap) super.getAttributes());
                    }
                };
                copyAtts(node.getAttributes(), element);
                copyElement(node.getChildNodes(), element, document);

                parent.appendChild(element);
            }
        }
    }

    private void copyAtts(NamedNodeMap attributes, Element target) {
        for (int i = 0; i < attributes.getLength(); i++) {
            Node att = attributes.item(i);
            target.setAttribute(att.getNodeName(), att.getNodeValue());
        }
    }

    public class AttributeSortedMap extends AttributeMap {
        AttributeSortedMap(ElementImpl element, AttributeMap attributes) {
            super(element, attributes);
            nodes.sort((o1, o2) -> {
                AttrImpl att1 = (AttrImpl) o1;
                AttrImpl att2 = (AttrImpl) o2;

                Integer pos1 = sortAtts.indexOf(att1.getNodeName());
                Integer pos2 = sortAtts.indexOf(att2.getNodeName());
                if (pos1 > -1 && pos2 > -1) {
                    return pos1.compareTo(pos2);
                } else if (pos1 > -1 || pos2 > -1) {
                    return pos1 == -1 ? 1 : -1;
                }
                return att1.getNodeName().compareTo(att2.getNodeName());
            });
        }
    }
//
//    public void main(String[] args) throws Exception {
//        new AttOrderSorter().format("src/main/resources/test.xml", "src/main/resources/output.xml");
//    }
}
