package me.test.jdk.javax.xml.bind;

import javax.xml.bind.annotation.*;
import java.util.List;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name="div")
public class Div2 {


    @XmlElementRef(name = "span", type = Span.class)
    @XmlMixed
    private List<Object> items;

    public List<Object> getItems() {
        return items;
    }

    public void setItems(List<Object> items) {
        this.items = items;
    }
}
