package me.test.first.spring.boot.test;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author dangqian.zll
 * @date 2019-11-05
 * @see <a href="http://logback.qos.ch/manual/configuration.html">Chapter 3: Logback configuration</a>
 */
public class LogbackTest {

    static Logger log = LoggerFactory.getLogger(LogbackTest.class);

    @Test
    public void test01() {
        log.info("test01");
    }


}
