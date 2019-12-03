package me.test.first.spring.boot.test.context.a;

import me.test.first.spring.boot.test.context.b.BbbService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author dangqian.zll
 * @date 2019-06-28
 */
@Service
public class CccSerivce implements InitializingBean {

    @Autowired
    BbbService bbbService;

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("-----bbbService = " + bbbService);
    }

}
