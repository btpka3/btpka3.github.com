package me.test.first.spring.boot.test;

import ch.qos.logback.classic.pattern.ClassicConverter;
import ch.qos.logback.classic.spi.ILoggingEvent;

/**
 *
 * @author dangqian.zll
 * @date 2026/1/20
 */
public class ThreadIdConverter extends ClassicConverter {
    @Override
    public String convert(final ILoggingEvent e) {
        return String.valueOf(Thread.currentThread().getId());
    }
}
