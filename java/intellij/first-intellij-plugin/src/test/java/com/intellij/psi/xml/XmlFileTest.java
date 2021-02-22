package com.intellij.psi.xml;

import com.intellij.lang.ASTFactory;
import com.intellij.psi.impl.source.tree.CompositeElement;

/**
 * @author dangqian.zll
 * @date 2021/2/18
 */
public class XmlFileTest {

    public void x() {
        XmlFile f = null;

    }

    public void createDocument01() {
        // TODO
        XmlDocument doc = null;
    }

    public void docSetRootTag() {
        // TODO
        XmlDocument doc = null;
    }


    public void tagCreateTag01() {
        XmlTag tag = null;
        XmlTag subTag01 = tag.createChildTag("a:aa", null, "body001", false);
        tag.addSubTag(subTag01, false);
    }

    public void tagSetAttr01() {
        XmlTag tag = null;
        tag.setAttribute("q:qq", "xxx");
    }


    public void createCdata() {


//        XmlASTFactory factory = null;
//        CompositeElement cdata = factory.createComposite(XmlElementType.XML_CDATA);

//        cdata.addChild();

        CompositeElement cdata = ASTFactory.composite(XmlElementType.XML_CDATA);

        cdata.addChild(ASTFactory.composite(XmlTokenType.XML_CDATA_START));
        {
            CompositeElement txt = ASTFactory.composite(XmlTokenType.XML_DATA_CHARACTERS);
            cdata.addChild(txt);
        }
        cdata.addChild(ASTFactory.composite(XmlTokenType.XML_CDATA_END));

        XmlText xmlText = null;
        xmlText.getNode().addChild(cdata);

    }
}
