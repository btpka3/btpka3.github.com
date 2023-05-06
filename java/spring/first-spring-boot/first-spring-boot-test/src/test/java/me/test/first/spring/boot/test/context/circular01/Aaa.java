package me.test.first.spring.boot.test.context.circular01;

import lombok.Data;
import lombok.ToString;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author dangqian.zll
 * @date 2022/10/17
 */
@Component("myAaa")
@Data
@ToString(exclude = "bbb")
public class Aaa {
    private String name = "a1";
    private Bbb bbb;

    @Resource(name = "myBbb")
    public void setBbb(Bbb bbb) {
        this.bbb = bbb;
    }
}
