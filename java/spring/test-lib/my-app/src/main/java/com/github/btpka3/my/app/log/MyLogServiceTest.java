package com.github.btpka3.my.app.log;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.encoder.PatternLayoutEncoder;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.Appender;
import ch.qos.logback.core.ConsoleAppender;
import ch.qos.logback.core.FileAppender;
import com.github.btpka3.my.lib.log.MyLogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import java.util.List;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.StreamSupport;

/**
 * @author dangqian.zll
 * @date 2020-03-15
 */
public class MyLogServiceTest {

    public static void main(String[] args) {

        testLog01();
//        testNdc();
//        testAppender();

    }

    public static void testLog01() {
        try {
            MDC.put("xxx", "xxx001");
            MDC.put("yyy", null);
            MyLogService.testLog01();
            Logger log = LoggerFactory.getLogger(MyLogService.class);
            log.error("xxx", new RuntimeException("err001"));
        } catch (Exception e) {
            System.err.println("e.class" + e.getClass());
            e.printStackTrace();
        }

    }

    protected static void xx(Logger log, int a, int b, int max) {

        int sum = a + b;
        log.info("new sum={}", sum);

        if (sum >= max) {
            return;
        } else {
            int newA = b;
            int newB = sum;
            MdcEx.withMdc("a", newA)
                    .withMdc("b", newB)
                    .withMdcWhen(sum % 2 == 0, "preSum", sum)
                    .run(() -> xx(log, newA, newB, max));
        }
    }

    public static void testNdc() {

        // 1: 1+2=3
        // 2: 2+3=5
        // 3: 3+5=8
        // 4: 5+8=13
        // 5: 8+13=21
        // 6: 13+21=34
        // 6: 21+34=55
        // 6: 34+55=89
        // 6: 55+89=144
        Logger log = LoggerFactory.getLogger(MyLogService.class);
        log.info("test started");
        try {
            int a = 1;
            int b = 2;
            int max = 150;
            MdcEx.withMdc("a", a)
                    .withMdc("b", b)
                    .run(() -> xx(log, a, b, max));
        } catch (Exception e) {
            System.err.println("e.class" + e.getClass());
            e.printStackTrace();
        } finally {
            log.info("test ended");
        }
    }

    public static void testAppender() {
        createLoggerFor("aaa", "/tmp/aaa.log");
        Logger log = LoggerFactory.getLogger("aaa");
        log.info("Hi");
        log.info("Hello", new RuntimeException("err001"));
        System.out.println("done.");
    }

    private static Logger createLoggerFor(String string, String file) {
        LoggerContext lc = (LoggerContext) LoggerFactory.getILoggerFactory();

        List<ch.qos.logback.classic.Logger> loggerList = lc.getLoggerList();


        Appender consoleAppender = loggerList.stream()
                .flatMap(l -> StreamSupport.stream(
                        Spliterators.spliteratorUnknownSize(
                                l.iteratorForAppenders(),
                                Spliterator.ORDERED
                        ),
                        false))
                .filter(a -> a instanceof ConsoleAppender)
                .findFirst()
                .orElse(null);


        PatternLayoutEncoder ple = new PatternLayoutEncoder();
        ple.setPattern("%date %level [%thread] %logger{10} [%file:%line] %msg%n");
        ple.setContext(lc);
        ple.start();

        FileAppender<ILoggingEvent> fileAppender = new FileAppender<ILoggingEvent>();
        fileAppender.setFile(file);
        fileAppender.setEncoder(ple);
        fileAppender.setContext(lc);
        fileAppender.start();


        ch.qos.logback.classic.Logger logger = (ch.qos.logback.classic.Logger) LoggerFactory.getLogger(string);
        logger.addAppender(fileAppender);
        if (consoleAppender != null) {
            logger.addAppender(consoleAppender);
        }
        logger.setLevel(Level.DEBUG);
        logger.setAdditive(false);

        return logger;
    }
}
