package me.test.jdk.java.beans;

import java.io.Serializable;

/**
 * - 必须实现 Serializable 接口
 * - 必须有一个 public 的无参构造函数。
 * @author dangqian.zll
 * @date 2025/4/30
 */

public class MyJavaBean implements Serializable {
    private static final long serialVersionUID = 1L;



    private String name;
    private boolean dead;
    private Integer age;

    public MyJavaBean() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isDead() {
        return dead;
    }

    public void setDead(boolean dead) {
        this.dead = dead;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}
