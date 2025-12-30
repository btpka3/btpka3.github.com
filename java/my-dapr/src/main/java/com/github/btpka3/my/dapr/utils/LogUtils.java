package com.github.btpka3.my.dapr.utils;

import lombok.SneakyThrows;
import org.apache.commons.text.StringSubstitutor;
import org.slf4j.LoggerFactory;

import javax.annotation.Nonnull;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * 打印日志的工具类
 *
 * @author dangqian.zll
 * @date 2020-08-04
 */
public class LogUtils {

    public static final Set<String> DEFAULT_PREFERRED_INTERFACES = toStringSet(System.getProperty("gong9-log.network.interface.preferred", ""));
    public static final Set<String> DEFAULT_IGNORED_INTERFACES = toStringSet(System.getProperty("gong9-log.network.interface.ignored", ""));


    public static Set<String> toStringSet(String str){
        if (str == null) {
            return Collections.emptySet();
        }
        return Arrays.stream(str.split(","))
            .map(String::trim)
            .filter(s -> !s.isEmpty())
            .collect(Collectors.toSet()) ;
    }


    /**
     * 检查当前是否在使用 logback 作为日志输出框架。
     *
     * @return
     */
    public static boolean isUsingLogback() {
        return "ch.qos.logback.classic.LoggerContext".equals(LoggerFactory.getILoggerFactory().getClass().getName());
    }

    @Nonnull
    public static Optional<String> getProperty(Properties properties, String propertyName) {
        return getProperty(properties, propertyName, null);
    }

    @Nonnull
    public static Optional<String> getProperty(Properties properties, String propertyName, String defaultValue) {
        return OptionalUtils.or(
                () -> Optional.ofNullable(properties)
                    .map(p -> p.getProperty(propertyName)),
                () -> Optional.ofNullable(System.getProperty(propertyName)),
                () -> Optional.ofNullable(defaultValue)
            )
            .map(StringSubstitutor::replaceSystemProperties);
    }

    @SneakyThrows
    public static String getHostIpV4() {
        return getHostIpV4(DEFAULT_PREFERRED_INTERFACES, DEFAULT_IGNORED_INTERFACES);
    }
    @SneakyThrows
    public static String getHostIpV4(Set<String> preferredInterfaces, Set<String> ignoredInterfaces) {
        return enumerationAsStream(NetworkInterface.getNetworkInterfaces())
            .filter(iface -> {
                String ifaceName = iface.getName();
                // 满足白名单，则直接使用
                if (preferredInterfaces != null && !preferredInterfaces.isEmpty() && preferredInterfaces.contains(ifaceName)) {
                    return true;
                }
                // 满足黑名单, 则直接不使用
                if (ignoredInterfaces != null && !ignoredInterfaces.isEmpty() && ignoredInterfaces.contains(ifaceName)) {
                    return false;
                }
                // 否则按照默认过滤逻辑匹配
                try {
                    return iface.isUp() && !iface.isLoopback() && !iface.isVirtual() && !iface.isPointToPoint();
                } catch (Exception e) {
                    return false;
                }
            })
            .flatMap(iface -> enumerationAsStream(iface.getInetAddresses())
                .filter(addr -> Inet4Address.class.isAssignableFrom(addr.getClass()))
                .map(InetAddress::getHostAddress)
            )
            .findFirst()
            .orElse(null);
    }


    @SneakyThrows
    public static String getHostName() {
        return InetAddress.getLocalHost().getHostName();
    }

    public static <T> Stream<T> enumerationAsStream(Enumeration<T> e) {
        Iterator<T> it = new Iterator<T>() {
            @Override
            public T next() {
                return e.nextElement();
            }

            @Override
            public boolean hasNext() {
                return e.hasMoreElements();
            }

            @Override
            public void forEachRemaining(Consumer<? super T> action) {
                while (e.hasMoreElements()) {
                    action.accept(e.nextElement());
                }
            }
        };
        return StreamSupport.stream(Spliterators.spliteratorUnknownSize(it, Spliterator.ORDERED), false);
    }


}
