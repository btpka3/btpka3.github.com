package me.test.first.spring.boot.test;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.joran.JoranConfigurator;
import ch.qos.logback.core.util.StatusPrinter;
import com.alibaba.fastjson.JSON;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.slf4j.NDC;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

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


    @Test
    public void checkProperty01() {
        Logger log = LoggerFactory.getLogger("my.var");
        System.out.println("请检查logbak初始化日志,确认 `my.log.path` 的值");
        log.info("hello my var");
        // 第一次运行：不指定JVM属性，则默认是输出日志到该路径
        // cat /tmp/my/logs/root.log

        // 第二次运行：指定jvm属性 `-Dmy.log.path=1`, 则期望日志时输出到该路径
        // cat /tmp/1/root.log
    }

    @Test
    public void ndc01() {
        try {
            MDC.put("a", "a01");
            printList(Arrays.asList("xxx", "yyy", "zzz"), 0);
        } finally {
            MDC.clear();
        }
        System.out.println("======= Done.");
    }

    /**
     * 为了演示NDC输出，以递归的方式打印List
     */
    protected void printList(List list, int start) {
        Logger log = LoggerFactory.getLogger("my.ndc");
        if (start < 0 || start >= list.size()) {
            return;
        }
        Object obj = list.get(start);
        try {
            NDC.push("my-ndc=" + start + "," + obj);
            log.info("hello");
            printList(list, start + 1);
        } finally {
            NDC.pop();
        }
        /* demo output :
        1169 [Test worker] INFO  my.ndc |a=a01, NDC0=my-ndc=0,xxx| - hello
        1173 [Test worker] INFO  my.ndc |NDC1=my-ndc=1,yyy, a=a01, NDC0=my-ndc=0,xxx| - hello
        1174 [Test worker] INFO  my.ndc |NDC1=my-ndc=1,yyy, a=a01, NDC2=my-ndc=2,zzz, NDC0=my-ndc=0,xxx| - hello
        ======= Done.
         */
    }

    @Test
    public void json01() {
        Logger log = LoggerFactory.getLogger("my.json");
        try {
            MDC.put("a", "a01");
            NDC.push("my-ndc-a=aaa1");
            Throwable e = new RuntimeException("demo err");
            log.info("hello : {}", "zhang3", e);
            System.out.println("======= Done.");
        } finally {
            MDC.remove("a");
            NDC.pop();
        }
    }

    /**
     * @throws IOException
     * @see ch.qos.logback.classic.joran.action.ConfigurationAction#begin(ch.qos.logback.core.joran.spi.InterpretationContext, String, org.xml.sax.Attributes)
     */
    @Test
    public void defineVars() throws IOException {
        String xmlStr = IOUtils.toString(LogbackTest.class.getResourceAsStream("LogbackTest-defineVars.xml"), StandardCharsets.UTF_8);
        LoggerContext loggerContext = LogbackUtils.getLoggerContext();
        loggerContext.putProperty("mySuffix", "demo");
        loggerContext.putProperty("debug", "true");
        LogbackUtils.configureLogback(xmlStr);
    }


}


