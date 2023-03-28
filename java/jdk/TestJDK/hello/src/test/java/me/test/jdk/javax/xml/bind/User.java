package me.test.jdk.javax.xml.bind;

import javax.xml.bind.annotation.*;
import java.util.Date;
import java.util.List;
import java.util.Optional;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name="user")
public class User {

    public enum Gender {

        /**
         * 女性
         */
        M,

        /**
         * 男性
         */
        F
    }

    public static enum Gender2 {

        M("male"),
        F("female");

        private Gender2(String code) {
            this.code = code;
        }

        private String code;

        public String getCode() {
            return code;
        }
    }


    @XmlAttribute
    private String name;

    @XmlAttribute
    private Integer age;

    @XmlElement
    private Integer height;

    @XmlElement
    private Date birthday;

    @XmlElement
    private Date birthday2;

    @XmlElement
    private Date birthday3;

    @XmlElement
    private Gender gender;

    @XmlElement
    private Gender2 gender2;

    @XmlElement
    private Boolean aggree;

    @XmlElement
    private Boolean aggree2;

    @XmlElement
    private Optional<Double> weight;

    @XmlElement
    private Optional<Double> weight2;

    @XmlElementWrapper
    private List<Item> items;



    private Div div;
    private Div div2;



}
