package me.test.first.spring.boot.test.context.circular01;

import lombok.Data;
import lombok.ToString;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author dangqian.zll
 * @date 2022/10/17
 */
@Component("myBbb")
@Data
@ToString(exclude = "aaa")
public class Bbb {
    private String name = "b1";
    private Aaa aaa;

    @Resource(name = "myAaa")
    public void setAaa(Aaa aaa) {
        this.aaa = aaa;
    }
}
