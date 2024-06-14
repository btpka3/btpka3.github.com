package me.test.first.spring.boot.test;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.joran.JoranConfigurator;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.Appender;
import ch.qos.logback.core.ContextBase;
import ch.qos.logback.core.util.StatusPrinter;
import org.slf4j.LoggerFactory;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Optional;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;


public class LogbackUtils {

    public static boolean isRootLogger(@Nonnull Logger logger) {
        return "ROOT".equals(logger.getName());
    }

    /**
     * 将当前logback配置清除，并按照新的配置进行初始化。
     *
     * @param configStr
     */
    public static void reInitLogback(String configStr) {
        try {
            LoggerContext loggerContext = getLoggerContext();
            loggerContext.reset();
            configureLogback(configStr);
        } catch (Exception e) {
            throw new RuntimeException(
                    "Cannot configure logback with following string :\n" + configStr,
                    e
            );
        }
    }

    /**
     * 在当前logback配置的基础上继续进行对应的初始化。
     *
     * @param configStr
     */
    public static void configureLogback(String configStr) {
        try (InputStream configInputStream = new ByteArrayInputStream(configStr.getBytes(StandardCharsets.UTF_8))) {
            JoranConfigurator configurator = new JoranConfigurator();
            LoggerContext loggerContext = getLoggerContext();
            configurator.setContext(loggerContext);
            configurator.doConfigure(configInputStream);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * 检查当前是否在使用 logback 作为日志输出框架。
     *
     * @return
     */
    public static boolean isUsingLogback() {
        return (LoggerFactory.getILoggerFactory() instanceof LoggerContext);
    }

    static void ensureUsingLogback() {
        if (!isUsingLogback()) {
            throw new RuntimeException("using logback is required, please guard your code with LogbackUtils#isUsingLogback()");
        }
    }

    /**
     * 获取 logback 到当前时点的内部初始化、运行日志。
     * 比如包含以下信息：
     * <ol>
     *     <li>使用 logback.xml 等初始化时的 logger/appender 信息，以及配置错误信息。</li>
     *     <li>logback 初始化完成后，实际日志输出中遇到异常信息，比如：RollingFileAppender 轮转的原始文件和轮转的目标文件不在一个目录下。</li>
     * </ol>
     *
     * @return logback 初始化日志
     * @see ch.qos.logback.core.status.StatusManager
     * @see ch.qos.logback.core.util.StatusPrinter
     */
    public static String getLogbackStatus() {
        ensureUsingLogback();
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

    public static Stream<Appender<ILoggingEvent>> appenderStream(Logger logger) {
        return StreamSupport.stream(
                Spliterators.spliteratorUnknownSize(logger.iteratorForAppenders(), Spliterator.ORDERED),
                false
        );
    }

    @Nonnull
    public static LoggerContext getLoggerContext() {
        ensureUsingLogback();
        return (LoggerContext) LoggerFactory.getILoggerFactory();
    }

    static <V> V getObj(@Nullable ContextBase contextBase, String contextKey) {
        ensureUsingLogback();
        return Optional.ofNullable(contextBase)
                .map(ctx -> (V) ctx.getObject(contextKey))
                .orElseThrow(() -> new RuntimeException(
                        "Cannot found object from loggerContext[" + (contextBase != null ? contextBase.getName() : null) + "] with key=" + contextKey
                ));
    }
}
