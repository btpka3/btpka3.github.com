package me.test.first.spring.boot.test.context;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * @author dangqian.zll
 * @date 2022/4/1
 */
@Data
@AllArgsConstructor
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

    @PreDestroy
    public void destroy() {
        System.out.println("myPojo destroy");
    }
}
