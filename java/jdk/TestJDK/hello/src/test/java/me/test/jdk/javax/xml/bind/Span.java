package me.test.jdk.javax.xml.bind;

import javax.xml.bind.annotation.*;
import java.util.List;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name="span")
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
