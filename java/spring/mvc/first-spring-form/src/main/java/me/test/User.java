
package me.test;

import java.io.Serializable;
import java.util.List;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    // hidden 项测试
    @NotNull
    private Long id;

    // 文本框测试
    @NotNull
    // 自定义消息
    @Size(max = 10, message = "{me.test.User.name.Size.message}")
    // 自定义约束
    @CheckCase(value = CaseMode.LOWER)
    private String name;

    @Min(value = 0)
    private Integer age;

    // 单选框测试
    /** 是否接收邮件：0-接收，1-不接收 */
    private Boolean recMail;

    // 复选框测试
    /** 爱好：1-购物，2-电子游戏，3-户外运动 */
    private List<String> hobbies;

    // 下拉列表测试(非空)
    /** 性别：0-保密，1-男，2-女 */
    private String gender;

    // 下拉列表测试(允许为空) 动态 code list
    /** 国家：1-中国，2-美国 */
    private String country;

    public Long getId() {

        return id;
    }

    public void setId(Long id) {

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

    public Boolean getRecMail() {

        return recMail;
    }

    public void setRecMail(Boolean recMail) {

        this.recMail = recMail;
    }

    public List<String> getHobbies() {

        return hobbies;
    }

    public void setHobbies(List<String> hobbies) {

        this.hobbies = hobbies;
    }

    public String getGender() {

        return gender;
    }

    public void setGender(String gender) {

        this.gender = gender;
    }

    public String getCountry() {

        return country;
    }

    public void setCountry(String country) {

        this.country = country;
    }

}
