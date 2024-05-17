package me.test.first.spring.boot.test.context.scan.pkg1;

import org.springframework.stereotype.Component;

/**
 * @author dangqian.zll
 * @date 2024/5/17
 */
@Component
public class MyService {
    public String getName() {
        return "MyService001";
    }
}
