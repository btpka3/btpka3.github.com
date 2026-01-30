package me.test.jdk.javax.xml.bind;

import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlMixed;
import javax.xml.bind.annotation.XmlRootElement;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "span")
public class Span {

    @XmlMixed
    @XmlElementRef(type = Span.class, name = "div")
    private List<Object> items;

    public List<Object> getItems() {
        return items;
    }

    public void setItems(List<Object> mixed) {
        this.items = items;
    }

}
