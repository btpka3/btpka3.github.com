package me.test.first.spring.boot.test.context.a;

import lombok.Data;
import org.springframework.stereotype.Service;

/**
 * @author dangqian.zll
 * @date 2019-06-28
 */
@Data
@Service
public class AaaService {

    private String name;

    public int add(int a, int b) {
        return a + b;
    }
}
