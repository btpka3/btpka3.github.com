
package me.test;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class CGlibAopTest {

    @Resource
    private MyImpl myImpl;

    @Test
    public void test() {

        myImpl.aa();
        myImpl.bb();
        myImpl.cc();

    }

}
