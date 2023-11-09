package me.test.first.spring.boot.test;

import lombok.SneakyThrows;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

/**
 * @author dangqian.zll
 * @date 2023/8/16
 */
public class MavenSortExcludes {

    /**
     * TODO 尚未实现
     */
    @SneakyThrows
    public void sort() {

        InputSource inputSource = new InputSource(MavenSortExcludes.class.getResourceAsStream("MavenSortExcludes2.xml"));
        //String xmlStr = IOUtils.toString(MavenSortExcludes.class.getResourceAsStream("MavenSortExcludes.xml"), StandardCharsets.UTF_8);
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = factory.newDocumentBuilder();
        Document doc = dBuilder.parse(inputSource);
        // 根节点
        Element docElement = doc.getDocumentElement();
        docElement.normalize();
        NodeList nList = docElement.getElementsByTagName("exclusion");
        for (int i = 0; i < nList.getLength(); i++) {
            Node nNode = nList.item(i);
            System.out.println("\nCurrent Element: " + nNode.getNodeName());
            if (nNode.getNodeType() == Node.ELEMENT_NODE) {

                Element elem = (Element) nNode;

                String uid = elem.getAttribute("id");

                Node node1 = elem.getElementsByTagName("firstname").item(0);
                String fname = node1.getTextContent();

                Node node2 = elem.getElementsByTagName("lastname").item(0);
                String lname = node2.getTextContent();

                Node node3 = elem.getElementsByTagName("occupation").item(0);
                String occup = node3.getTextContent();

                System.out.printf("User id: %s%n", uid);
                System.out.printf("First name: %s%n", fname);
                System.out.printf("Last name: %s%n", lname);
                System.out.printf("Occupation: %s%n", occup);
            }
        }


    }


}
