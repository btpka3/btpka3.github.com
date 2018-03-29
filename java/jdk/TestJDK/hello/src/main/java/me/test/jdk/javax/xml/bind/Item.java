package me.test.jdk.javax.xml.bind;

import javax.xml.bind.annotation.*;
import java.util.Objects;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name="item")
public class Item {

    @XmlAttribute
    private String name;

    @XmlValue
    private String value;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Item)) {
            return false;
        }
        Item item = (Item) o;
        return Objects.equals(name, item.name) &&
                Objects.equals(value, item.value);
    }

    @Override
    public int hashCode() {

        return Objects.hash(name, value);
    }

    @Override
    public String toString() {
        return "Item{" +
                "name='" + name + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}
