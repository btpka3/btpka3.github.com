package me.test.first.spring.boot.test;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.joran.JoranConfigurator;
import ch.qos.logback.core.util.StatusPrinter;
import com.alibaba.fastjson.JSON;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

/**
 * @author dangqian.zll
 * @date 2019-11-05
 * @see <a href="http://logback.qos.ch/manual/configuration.html">Chapter 3: Logback configuration</a>
 */
public class LogbackTest {

    static Logger log = LoggerFactory.getLogger(LogbackTest.class);

    @Test
    public void test01() {

        Exception e = new RuntimeException("test Exception");

        log.info("test01");
        log.info("info exception :", e);
        log.error("error exception :", e);
        log.info("info null exception :", e);
        log.error("error null exception :", e);
    }


    @Test
    public void test02() {

        System.out.println("----------000");
        System.out.println(getLogbackInitLog());
        System.out.println(getLogbackProps());

        System.out.println("----------001");
        reInitLogback("LogbackTest-01.xml");
        System.out.println(getLogbackInitLog());
        System.out.println(getLogbackProps());

        System.out.println("----------002-1");
        reInitLogback("LogbackTest-02.xml");
        System.out.println(getLogbackInitLog());
        System.out.println(getLogbackProps());

        System.out.println("----------002-2");
        reInitLogback("LogbackTest-02.xml");
        System.out.println(getLogbackInitLog());
        System.out.println(getLogbackProps());

        System.out.println("----------002-3");
        LoggerContext lc = (LoggerContext) LoggerFactory.getILoggerFactory();
        lc.reset();
        lc.getStatusManager().clear();
        System.out.println(lc.getLoggerList().size());
        reInitLogback("LogbackTest-03.xml");
        System.out.println(getLogbackInitLog());
        System.out.println(getLogbackProps());

        System.out.println("----------002-4");

    }


    public static String getLogbackProps() {
        LoggerContext lc = (LoggerContext) LoggerFactory.getILoggerFactory();
        return JSON.toJSONString(lc.getCopyOfPropertyMap());
    }

    public static void reInitLogback(String logFile) {
        try {
            LoggerContext lc = (LoggerContext) LoggerFactory.getILoggerFactory();
            InputStream is = LogbackTest.class.getResourceAsStream(logFile);
            JoranConfigurator configurator = new JoranConfigurator();
            configurator.setContext(lc);
            configurator.doConfigure(is);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
//            try {
//                String str = getLogbackInitLog();
//                System.out.println("finally: init log=\n" + str);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
        }
    }

    public static String getLogbackInitLog() {
        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream(2048);
            PrintStream ps = new PrintStream(out, true);
            StatusPrinter.setPrintStream(ps);
            LoggerContext lc = (LoggerContext) LoggerFactory.getILoggerFactory();
            StatusPrinter.print(lc);
            ps.flush();

            return new String(out.toByteArray(), StandardCharsets.UTF_8);
        } finally {
            StatusPrinter.setPrintStream(System.out);
        }
    }


}


