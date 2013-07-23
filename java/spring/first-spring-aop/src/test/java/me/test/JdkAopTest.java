
package me.test;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class JdkAopTest {

    @Resource
    private MyInterface myInterface;

    @Test
    public void test() {

        myInterface.aa();
        myInterface.bb();
        if (myInterface instanceof MyImpl) {
            ((MyImpl) myInterface).cc();
        }

    }

}
