package me.test.spring

import org.springframework.beans.BeansException
import org.springframework.beans.factory.InitializingBean
import org.springframework.context.ApplicationContext
import org.springframework.context.ApplicationContextAware
import org.springframework.context.ApplicationListener
import org.springframework.context.SmartLifecycle
import org.springframework.context.event.ContextClosedEvent

// 该 bean 主要用以测试通过 @Bean 的方式声明的bean是否会被调用相应的接口。
class AAA implements ApplicationContextAware,
        InitializingBean,
        ApplicationListener<ContextClosedEvent>,
        SmartLifecycle {

    // ------------------------------------ ApplicationContextAware,
    @Override
    void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        println "------------- AAA : ApplicationContextAware : setApplicationContext($applicationContext)"
    }

    // ------------------------------------ InitializingBean
    @Override
    void afterPropertiesSet() throws Exception {
        println "------------- AAA : InitializingBean : afterPropertiesSet"
    }

    // ------------------------------------ ApplicationListener<ContextClosedEvent>
    @Override
    void onApplicationEvent(ContextClosedEvent event) {
        println "------------- AAA : ApplicationListener<ContextClosedEvent> : onApplicationEvent(ContextClosedEvent) :$event"
    }

    // ------------------------------------ Lifecycle
    boolean running = false

    @Override
    void start() {
        println "------------- AAA : Lifecycle : start"
        running = true
    }

    @Override
    void stop() {
        running = false
        println "------------- AAA : Lifecycle : stop"
    }

    @Override
    boolean isRunning() {
        println "------------- AAA : Lifecycle : isRunning"
        return running
    }

    // ------------------------------------ SmartLifecycle
    @Override
    boolean isAutoStartup() {
        println "------------- AAA : SmartLifecycle : isAutoStartup"
        return true
    }

    @Override
    void stop(Runnable callback) {
        println "------------- AAA : SmartLifecycle : stop(Runnable) : $running"
        stop();
        callback.run();
    }

    // ------------------------------------ Phased
    @Override
    int getPhase() {
        println "------------- AAA : Phased : getPhase : $running"
        return 0
    }
}