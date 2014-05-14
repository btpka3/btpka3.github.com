
package me.test;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.filter.GenericFilterBean;

public class MySessionFilter extends GenericFilterBean {

    private Logger logger = LoggerFactory.getLogger(MySessionFilter.class);
    public static final ThreadLocal<HttpServletRequest> requestHolder = new ThreadLocal<HttpServletRequest>();
    public static final ThreadLocal<HttpServletResponse> responseHolder = new ThreadLocal<HttpServletResponse>();
    public static final ThreadLocal<HttpServletRequest> wrappedRequestHolder = new ThreadLocal<HttpServletRequest>();
    public static final ThreadLocal<HttpServletResponse> wrappedResponseHolder = new ThreadLocal<HttpServletResponse>();
    public static final ThreadLocal<ServletContext> servletContextHolder = new ThreadLocal<ServletContext>();

    private SessionManager sessionManager;

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        ContextHolder.setServletContext(getServletContext());
        servletContextHolder.set(this.getServletContext());

        requestHolder.remove();
        responseHolder.remove();
        wrappedRequestHolder.remove();
        wrappedResponseHolder.remove();
        if (request instanceof HttpServletRequest
                && response instanceof HttpServletResponse) {

            requestHolder.set((HttpServletRequest) request);
            responseHolder.set((HttpServletResponse) response);

            HttpServletRequest wrappedRequest = sessionManager.wrapHttpSevletRequest();
            HttpServletResponse wrappedResponse = sessionManager.wrapHttpSevletResponse();


            wrappedRequestHolder.set(wrappedRequest);
            wrappedResponseHolder.set(wrappedResponse);

            chain.doFilter(wrappedRequest, wrappedResponse);

        } else {
            logger.warn("request is not HttpServletRequest or response is not HttpServletResponse, will not preform session filter");
            chain.doFilter(request, response);
        }
    }

    public SessionManager getSessionManager() {

        return sessionManager;
    }

    public void setSessionManager(SessionManager sessionManager) {

        this.sessionManager = sessionManager;
    }

}
