package me.test;

import java.io.IOException;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.util.AntPathMatcher;

public class ExtensionFilter implements Filter {

    private Logger logger = LoggerFactory.getLogger(ExtensionFilter.class);

    public static final String EXTENSION_REQ_PROP_KEY = ExtensionFilter.class
            .getName();

    public static final String MATCH_PATTERN_STRING = "string";
    public static final String MATCH_PATTERN_REGEX = "regex";
    public static final String MATCH_PATTERN_ANT = "ant";

    private List<String> prohibitedUris = null;
    private List<String> exceptionUris = null;
    private String matchPattern = MATCH_PATTERN_STRING;
    private AntPathMatcher antPathMatcher = new AntPathMatcher();

    public void init(FilterConfig filterConfig) throws ServletException {
    }

    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain) throws IOException, ServletException {

        if (Boolean.TRUE.equals(request.getAttribute(EXTENSION_REQ_PROP_KEY))) {
            chain.doFilter(request, response);
            return;
        }

        request.setAttribute(EXTENSION_REQ_PROP_KEY, Boolean.TRUE);
        if (prohibitedUris == null || prohibitedUris.isEmpty()) {
            chain.doFilter(request, response);
            return;
        }

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        String uri = req.getRequestURI();

        if (exceptionUris != null && !exceptionUris.isEmpty()) {
            for (String exceptionUri : exceptionUris) {

                if (uri.equals(exceptionUri)) {
                    chain.doFilter(request, response);
                    return;
                }

            }
        }
        for (String prohibitedUri : prohibitedUris) {
            boolean prohibted = false;
            if (MATCH_PATTERN_STRING.equals(matchPattern)) {
                if (uri.endsWith(prohibitedUri)) {
                    prohibted = true;
                }
            }

            if (!prohibted && MATCH_PATTERN_REGEX.equals(matchPattern)) {
                if (uri.matches(prohibitedUri)) {
                    prohibted = true;
                    break;
                }
            }

            if (!prohibted && MATCH_PATTERN_ANT.equals(matchPattern)) {
                if (antPathMatcher.match(prohibitedUri, uri)) {
                    prohibted = true;
                    break;
                }
            }
            if (prohibted) {
                if (logger.isTraceEnabled()) {
                    logger.trace("ExtensionFilter forbid [" + uri + "] with "
                            + matchPattern + " pttern [" + prohibitedUri + "]");
                }
                resp.sendError(HttpServletResponse.SC_NOT_FOUND);
                return;
            }
        }

        chain.doFilter(request, response);
    }
    public void destroy() {

    }

    public List<String> getProhibitedUris() {
        return prohibitedUris;
    }

    public void setProhibitedUris(List<String> prohibitedUris) {
        this.prohibitedUris = prohibitedUris;
    }

    public List<String> getExceptionUris() {
        return exceptionUris;
    }

    public void setExceptionUris(List<String> exceptionUris) {
        this.exceptionUris = exceptionUris;
    }

    public String getMatchPattern() {
        return matchPattern;
    }

    public void setMatchPattern(String matchPattern) {
        if (matchPattern == null) {
            throw new NullPointerException("matchPattern could not be null.");
        }
        if (!MATCH_PATTERN_STRING.equals(matchPattern)
                && !MATCH_PATTERN_REGEX.equals(matchPattern)
                && !MATCH_PATTERN_ANT.equals(matchPattern)) {
            throw new IllegalArgumentException(
                    "matchPattern could only be 'string', 'regex' or 'ant'.");
        }
        this.matchPattern = matchPattern;
    }

}
