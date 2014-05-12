
package me.test;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MyFilter implements Filter {

    private Logger logger = LoggerFactory.getLogger(MyFilter.class);

    public void init(FilterConfig filterConfig) throws ServletException {

    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        if (request instanceof HttpServletRequest) {
            HttpServletRequest req = (HttpServletRequest) request;
            logger.debug("================================================");
            logger.debug("scheme = {}", req.getScheme());
            logger.debug("serverName = {}", req.getServerName());
            logger.debug("serverPort = {}", req.getServerPort());
            logger.debug("serverPath = {}", req.getServletPath());
            logger.debug("pathInfo = {}", req.getPathInfo());
            logger.debug("pathTranslated = {}", req.getPathTranslated());
            logger.debug("requestURI = {}", req.getRequestURI());
            logger.debug("requestURL = {}", req.getRequestURL());
            logger.debug("protocol = {}", req.getProtocol());
            logger.debug("queryString = {}", req.getQueryString());
            logger.debug("authType = {}", req.getAuthType());
            logger.debug("header: ");

            @SuppressWarnings("unchecked")
            Enumeration<String> headerNames = req.getHeaderNames();
            while (headerNames.hasMoreElements()) {
                String headerName = headerNames.nextElement();
                String headerValue = req.getHeader(headerName);
                logger.debug("\t{} : {}", headerName, headerValue);
            }
        }

        ServletRequest req = new MyRequest((HttpServletRequest) request);
        chain.doFilter(req, response);
    }

    public void destroy() {

    }

}
