package com.github.btpka3.hello.maven.plugin;

import org.apache.commons.lang3.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

import javax.xml.xpath.*;

/**
 * @author dangqian.zll
 * @date 2023/9/28
 */
public class BomTemplateChecker {

    public void check(Document doc) throws XPathExpressionException {

        //
        if (getNode(doc, "//project") == null) {
            throw new RuntimeException("xxx");
        }
        if (StringUtils.isNotBlank(getNodeText(doc, "//project/parent/relativePath"))) {
            throw new RuntimeException("`//project/parent/relativePath` with non-empty value is not supported");
        }
        if (getNode(doc, "//project/dependencyManagement") == null) {
            throw new RuntimeException("`//project/dependencyManagement` is required");
        }
        if (getNode(doc, "//project/dependencies") == null) {
            throw new RuntimeException("`//project/dependencies` show not existed.");
        }
    }

    protected Node getNode(Document doc, String nodeSelectionXpath) throws XPathExpressionException {
        XPathExpression epr = createXPathExpr(nodeSelectionXpath);
        return (Node) epr.evaluate(doc, XPathConstants.NODE);
    }

    protected String getNodeText(Document doc, String nodeSelectionXpath) throws XPathExpressionException {
        XPathExpression epr = createXPathExpr(nodeSelectionXpath);
        return (String) epr.evaluate(doc, XPathConstants.STRING);
    }


    protected XPathExpression createXPathExpr(String exprStr) throws XPathExpressionException {
        XPath xPath = XPathFactory.newInstance().newXPath();
        return xPath.compile(exprStr);
    }
}
