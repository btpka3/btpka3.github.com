package me.test.first.spring.boot.test.tomcat;

import org.apache.catalina.LifecycleException;
import org.apache.catalina.loader.WebappLoader;

/**
 * @author dangqian.zll
 * @date 2025/6/19
 */
public class MyWebappLoader extends WebappLoader {
    @Override
    protected void startInternal() throws LifecycleException {
        super.startInternal();
    }
}
