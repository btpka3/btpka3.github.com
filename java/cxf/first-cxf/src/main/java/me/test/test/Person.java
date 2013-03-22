package me.test.test;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;

public class Person implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 姓名 */
    private String name;

    /** 年龄 */
    private int age;
    /** 头像 */
    private byte[] imageData;
    /** 技能 */
    private List<String> ability;
    /** 附加信息 */
    private Map<String, String> extraInfo;

    /** 配偶 */
    private Person partner;

    public String getName() {
        return this.name;
    }
    public void setName(final String name) {
        this.name = name;
    }
    public Person getPartner() {
        return this.partner;
    }
    public void setPartner(final Person partner) {
        this.partner = partner;
    }
    public int getAge() {
        return this.age;
    }
    public void setAge(final int age) {
        this.age = age;
    }
    public byte[] getImageData() {
        return this.imageData;
    }
    public void setImageData(final byte[] imageData) {
        this.imageData = imageData;
    }
    public List<String> getAbility() {
        return this.ability;
    }
    public void setAbility(final List<String> ability) {
        this.ability = ability;
    }
    public Map<String, String> getExtraInfo() {
        return this.extraInfo;
    }
    public void setExtraInfo(final Map<String, String> extraInfo) {
        this.extraInfo = extraInfo;
    }

    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append("--------本人信息--------");
        Object data = this.getName();
        buf.append("\n姓名 : " + data);
        data = this.getAge();
        buf.append("\n年龄 : " + data);

        buf.append("\n头像 : "
                + new String(Base64.encodeBase64String(this.getImageData())));

        data = this.getAbility();
        buf.append("\n技能: " + data);
        data = this.getExtraInfo();
        buf.append("\n附加信息: " + data);

        buf.append("\n--------配偶信息--------");
        Person partner = this.getPartner();
        if (partner == null) {
            buf.append("\n无");
        } else {
            data = partner.getName();
            buf.append("\n姓名 : " + data);
            data = partner.getAge();
            buf.append("\n年龄 : " + data);
            buf.append("\n头像 : "
                    + new String(Base64.encodeBase64String(partner.getImageData())));
            data = partner.getAbility();
            buf.append("\n技能: " + data);
            data = partner.getExtraInfo();
            buf.append("\n附加信息: " + data);
        }
        return buf.toString();
    }
}
