
package me.test;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import me.test.redis.MyRequest;
import me.test.redis.MyResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.filter.GenericFilterBean;

public class MySessionFilter extends GenericFilterBean {

    private Logger logger = LoggerFactory.getLogger(MySessionFilter.class);
    private SessionManager sessionManager;

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        ContextHolder.setServletContext(getServletContext());

        if (request instanceof HttpServletRequest
                && response instanceof HttpServletResponse) {

            HttpServletRequest wrappedRequest = sessionManager.wrapHttpSevletRequest(
                    (HttpServletRequest) request,
                    (HttpServletResponse) response);

            HttpServletResponse wrappedResponse = sessionManager.wrapHttpSevletResponse(
                    (HttpServletRequest) request,
                    (HttpServletResponse) response);

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
