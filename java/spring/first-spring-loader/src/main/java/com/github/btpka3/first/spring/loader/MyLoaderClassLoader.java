package com.github.btpka3.first.spring.loader;

import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLStreamHandlerFactory;

/**
 * 以独立的classlaoder 去加载 spring-loader, 进而再去加载业务 main 类。
 */
public class MyLoaderClassLoader extends URLClassLoader {
    public MyLoaderClassLoader(URL[] urls, ClassLoader parent) {
        super(urls, parent);
    }

    public MyLoaderClassLoader(URL[] urls) {
        super(urls);
    }

    public MyLoaderClassLoader(URL[] urls, ClassLoader parent, URLStreamHandlerFactory factory) {
        super(urls, parent, factory);
    }

    public MyLoaderClassLoader(String name, URL[] urls, ClassLoader parent) {
        super(name, urls, parent);
    }

    public MyLoaderClassLoader(String name, URL[] urls, ClassLoader parent, URLStreamHandlerFactory factory) {
        super(name, urls, parent, factory);
    }
}
