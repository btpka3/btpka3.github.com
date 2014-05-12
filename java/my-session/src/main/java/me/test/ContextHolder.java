
package me.test;

import javax.servlet.ServletContext;

public class ContextHolder {

    private static ThreadLocal<ServletContext> servletContextHolder = new ThreadLocal<ServletContext>();

    public static void setServletContext(ServletContext servletContext) {

        servletContextHolder.set(servletContext);
    }

    public static ServletContext getServletContext() {

        return servletContextHolder.get();
    }
}
