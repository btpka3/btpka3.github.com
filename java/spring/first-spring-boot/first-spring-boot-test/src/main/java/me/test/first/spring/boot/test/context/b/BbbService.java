package me.test.first.spring.boot.test.context.b;

import me.test.first.spring.boot.test.context.a.AaaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author dangqian.zll
 * @date 2019-06-28
 */
@Service
public class BbbService {

    @Autowired
    AaaService aaaService;

    public int add2(int a, int b) {
        return a + b;
    }
}
