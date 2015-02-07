package me.test;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.ScriptAssert;

@ScriptAssert.List(
        @ScriptAssert(lang = "javascript", script = "_this.pwd1.equals(_this.pwd2)", message = "两次密码不一致")
)
public class Person {

    @Pattern(regexp = "^U\\d{3}$", message = "{me.test.Pattern.message}")
    private String id;

    @NotNull(message = "用户名不能为null")
    private String name;

    @Min(value = 1, message = "{me.test.Min.message}")
    @Max(value = 150)
    private Integer age;

    @Size(min = 5, max = 10, message = "地址长度不能为>={min} && <=10")
    private String address = null;

    private String pwd1;

    private String pwd2;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPwd1() {
        return pwd1;
    }

    public void setPwd1(String pwd1) {
        this.pwd1 = pwd1;
    }

    public String getPwd2() {
        return pwd2;
    }

    public void setPwd2(String pwd2) {
        this.pwd2 = pwd2;
    }

}