package com.github.btpka3.mvn.deps.checker;

import io.vavr.Tuple;
import io.vavr.Tuple3;
import org.apache.xml.serialize.OutputFormat;
import org.apache.xml.serialize.XMLSerializer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.w3c.dom.*;
import org.w3c.dom.bootstrap.DOMImplementationRegistry;
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSOutput;
import org.w3c.dom.ls.LSSerializer;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.BiFunction;
import java.util.function.Supplier;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * @author dangqian.zll
 * @date 2020/12/3
 */
public class XercesTest {

    String pomXmlFile = "/Users/zll/data0/work/git-repo/ali/ali_security/arm-mbus/pom.xml";


    /**
     * 直接使用 OutputFormat 来配置格式化
     *
     * @throws ParserConfigurationException
     * @throws IOException
     * @throws SAXException
     */

    @Test
    public void format01() throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        InputSource is = new InputSource(new InputStreamReader(new FileInputStream(pomXmlFile), StandardCharsets.UTF_8));
        Document document = db.parse(is);

        OutputFormat format = new OutputFormat(document);
        format.setLineWidth(150);
        format.setIndenting(true);
        format.setIndent(4);

        try (FileWriter fileWriter = new FileWriter("/tmp/Dom4jTest.test01.xml")) {
            XMLSerializer serializer = new XMLSerializer(fileWriter, format);
            serializer.serialize(document);
        }
    }

    /**
     * 使用 LSSerializer 进行格式化。
     *
     * @throws Exception
     */
    @Test
    public void format02() throws Exception {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        InputSource is = new InputSource(new InputStreamReader(new FileInputStream(pomXmlFile), StandardCharsets.UTF_8));
        Document document = db.parse(is);

        DOMImplementationRegistry registry = DOMImplementationRegistry.newInstance();

        DOMImplementationLS impl = (DOMImplementationLS) registry.getDOMImplementation("LS");


        LSSerializer writer = impl.createLSSerializer();

        // Set this to true if the output needs to be beautified.
        writer.getDomConfig().setParameter("format-pretty-print", Boolean.TRUE);
        // Set this to true if the declaration is needed to be outputted.
        writer.getDomConfig().setParameter("xml-declaration", true);

        LSOutput output = impl.createLSOutput();
        output.setByteStream(new FileOutputStream("/tmp/Dom4jTest.test02.xml"));
        output.setEncoding("UTF-8");
        writer.write(document, output);
    }


    protected Stream<Node> toStream(NodeList nodeList) {
        return IntStream.range(0, nodeList.getLength())
                .mapToObj(nodeList::item);
    }


    protected Optional<Element> findPropertiesElement(Document document) {

        return Optional.ofNullable(document)
                .map(Node::getChildNodes)
                .flatMap(nodeList -> this.toStream(nodeList)
                        .filter(node -> Node.ELEMENT_NODE == node.getNodeType())
                        .map(Element.class::cast)
                        .filter(element -> Objects.equals("properties", element.getNodeName()))
                        .findFirst()

                );

    }


    protected void sortProperties(Document document) {


    }

    /**
     * 自定义排序。
     *
     * @throws Exception
     */
    @Test
    public void custom() throws Exception {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        InputSource is = new InputSource(new InputStreamReader(XercesTest.class.getResourceAsStream("XercesTest01.xml"), StandardCharsets.UTF_8));
        Document document = db.parse(is);

        Element root = (Element) document.getChildNodes().item(0);

        NodeList nodeList = root.getChildNodes();

        Element nullEle = document.createElement("______null");

        List<Tuple3<AtomicReference<Element>, List<Node>, AtomicBoolean>> groupList = new ArrayList<>();

        Tuple3<AtomicReference<Element>, List<Node>, AtomicBoolean> curGroup = newEmptyGroup();
        curGroup._1.set(nullEle);
        groupList.add(curGroup);

        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            if (Node.TEXT_NODE == node.getNodeType()) {
                // 前一个分组未完结
                if (!curGroup._3.get()) {
                    Text text = (Text) node;
                    String wholeText = text.getWholeText();
                    int newLineIndex = wholeText.indexOf("\n");

                    if (newLineIndex < 0) {
                        curGroup._2.add(node);
                    } else {

                        if (curGroup._1.get() == null) {
                            curGroup._2.add(text);
                        } else {
                            // 换行符跟着前面的元素走
                            String lineEndStr = wholeText.substring(0, newLineIndex + 1);
                            Text lineEndText = document.createTextNode(lineEndStr);
                            curGroup._2.add(lineEndText);
                            curGroup._3.set(true);

                            text.setData(wholeText.substring(newLineIndex + 1));
                            curGroup = newEmptyGroup();
                            groupList.add(curGroup);
                            curGroup._2.add(text);
                        }
                    }
                } else {
                    curGroup = newEmptyGroup();
                    groupList.add(curGroup);
                    curGroup._2.add(node);
                }
            }

            if (Node.COMMENT_NODE == node.getNodeType()) {
                Comment comment = (Comment) node;
                if (!curGroup._3.get()) {
                    String data = comment.getData();
                    boolean isMultiLineComment = data.contains("\n");
                    if (isMultiLineComment) {
                        // 元素前的多行注释
                        if (curGroup._1.get() == null) {
                            curGroup._2.add(comment);
                        } else {
                            // 元素后的多行注释
                            curGroup._2.add(document.createTextNode("\n"));
                            curGroup._3.set(true);

                            curGroup = newEmptyGroup();
                            groupList.add(curGroup);
                            curGroup._2.add(comment);
                        }
                    } else {
                        curGroup._2.add(comment);
                    }
                } else {
                    curGroup = newEmptyGroup();
                    groupList.add(curGroup);
                    curGroup._2.add(node);
                }
            }
            if (Node.ELEMENT_NODE == node.getNodeType()) {
                Element element = (Element) node;
                if (!curGroup._3.get()) {
                    if (curGroup._1.get() != null) {
                        throw new IllegalArgumentException("aaa");
                    }
                    curGroup._1.set(element);
                    curGroup._2.add(element);
//                    curGroup._3.set(true);
//
//                    curGroup = newEmptyGroup();
//                    groupList.add(curGroup);
//                    curGroup._2.add(element);
                } else {
                    curGroup = newEmptyGroup();
                    groupList.add(curGroup);
                    curGroup._1.set(element);
                    curGroup._2.add(element);
                }
            }
        }
        for (int i = 0; i < nodeList.getLength(); i++) {
            root.removeChild(nodeList.item(i));
        }
        groupList.sort(Comparator.comparing(
                t3 -> {
                    Element e = t3._1.get();
                    if (e == null) {
                        return null;
                    }
                    return e.getNodeName();
                },
                Comparator.nullsFirst(Comparator.naturalOrder()))
        );

        for (Tuple3<AtomicReference<Element>, List<Node>, AtomicBoolean> group : groupList) {
            for (Node node : group._2) {
                root.appendChild(node);
            }
        }

        System.out.println(toString(document));
    }

    protected String toString(Document document) throws TransformerException {
        StringWriter writer = new StringWriter();
        Source source = new DOMSource(document);
        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
//        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
//        System.out.println(transformer.getOutputProperties());
        //transformer.setOutputProperty(OutputKeys.STANDALONE, "yes");
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.transform(source, new StreamResult(writer));
        return writer.toString();
    }


    protected Tuple3<AtomicReference<Element>, List<Node>, AtomicBoolean> newEmptyGroup() {
        return Tuple.of(new AtomicReference(null), new ArrayList<>(), new AtomicBoolean(false));
    }


    @Test
    public void notOutputStandalone() throws Exception {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        InputSource is = new InputSource(new StringReader("<?xml version=\"1.0\" encoding=\"UTF-8\"?><root></root>"));
        Document document = db.parse(is);


        boolean standalone = document.getXmlStandalone();
        Assertions.assertFalse(standalone);

        document.setXmlStandalone(true);

        String newXmlStr = toString(document);
        System.out.println(newXmlStr);
        Assertions.assertFalse(newXmlStr.contains("standalone"));
    }


    @Test
    public void newLineBetweenAttributes() throws Exception {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        InputSource is = new InputSource(new StringReader(
                "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
                        "<root a=\"a1\"" +
                        "      b=\"b1\">" +
                        "</root>"

        ));
        Document document = db.parse(is);
        boolean standalone = document.getXmlStandalone();
        Assertions.assertFalse(standalone);
        String newXmlStr = toString(document);
        System.out.println(newXmlStr);
    }


    @Test
    public void indent4space() throws Exception {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        InputSource is = new InputSource(new StringReader(
                "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
                        "<root>\n" +
                        "<a>aaa</a>" +
                        "<b>bbb</b>\n" +
                        "</root>"
        ));
        Document document = db.parse(is);

        Supplier<String> docToStr1 = () -> {
            try {
                DOMImplementationRegistry registry = DOMImplementationRegistry.newInstance();
                DOMImplementationLS impl = (DOMImplementationLS) registry.getDOMImplementation("LS");
                LSSerializer writer = impl.createLSSerializer();

                writer.getDomConfig().setParameter("format-pretty-print", Boolean.TRUE);

                LSOutput output = impl.createLSOutput();
                ByteArrayOutputStream byteArrOut = new ByteArrayOutputStream();
                output.setByteStream(byteArrOut);
                output.setEncoding("UTF-8");
                writer.write(document, output);
                return new String(byteArrOut.toByteArray(), StandardCharsets.UTF_8);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        };
        String newXmlStr = docToStr1.get();
        System.out.println(newXmlStr);
        Assertions.assertTrue(newXmlStr.contains("    <a>aaa</a>\n"));
        Assertions.assertTrue(newXmlStr.contains("    <b>bbb</b>\n"));
    }


    @Test
    public void noXmlOutput01() throws Exception {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        InputSource is = new InputSource(new StringReader(
                "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
                        "<root>\n" +
                        "<a>aaa</a>" +
                        "<b>bbb</b>\n" +
                        "</root>"
        ));
        Document document = db.parse(is);

        BiFunction<Document, Boolean, String> docToStr = (Document doc, Boolean declareXml) -> {
            try {
                DOMImplementationRegistry registry = DOMImplementationRegistry.newInstance();
                DOMImplementationLS impl = (DOMImplementationLS) registry.getDOMImplementation("LS");
                LSSerializer writer = impl.createLSSerializer();

                writer.getDomConfig().setParameter("xml-declaration", declareXml);

                LSOutput output = impl.createLSOutput();
                ByteArrayOutputStream byteArrOut = new ByteArrayOutputStream();
                output.setByteStream(byteArrOut);
                output.setEncoding("UTF-8");
                writer.write(doc, output);
                return new String(byteArrOut.toByteArray(), StandardCharsets.UTF_8);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        };
        {
            String newXmlStr = docToStr.apply(document, true);
            System.out.println(newXmlStr);
            Assertions.assertTrue(newXmlStr.startsWith("<?xml"));
        }
        System.out.println("------------");
        {
            String newXmlStr = docToStr.apply(document, false);
            System.out.println(newXmlStr);
            Assertions.assertFalse(newXmlStr.startsWith("<?xml"));
        }
    }

}
