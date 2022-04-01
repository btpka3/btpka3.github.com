package me.test.first.spring.boot.test.context;

import lombok.Data;

import javax.annotation.PostConstruct;

/**
 * @author dangqian.zll
 * @date 2022/4/1
 */
@Data
public class MyPojo {

    public MyPojo() {
        System.out.println("myPojo constructor");
    }

    @PostConstruct
    public void aaa() {
        System.out.println("myPojo @PostConstruct");
    }

    public void init() {
        System.out.println("myPojo init");
    }

    private String name;
    private MyPojo myPojo;

    public String getNameStr() {
        return this.name + "," + (myPojo != null ? myPojo.getName() : "");
    }

    public String toString() {
        return name;
    }
}
