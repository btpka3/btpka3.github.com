package me.test.first.spring.boot.test.tomcat;

import org.apache.catalina.loader.ParallelWebappClassLoader;

/**
 * @author dangqian.zll
 * @date 2025/6/19
 */
public class MyParallelWebappClassLoader extends ParallelWebappClassLoader {

    public MyParallelWebappClassLoader() {
        super();
    }


    public MyParallelWebappClassLoader(ClassLoader parent) {
        super(parent);
    }


}
