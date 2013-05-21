package me.test.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class OriginFilter implements Filter {

    public static final String EXTENSION_REQ_PROP_KEY = OriginFilter.class
            .getName();

    private List<MatchItem> matchItems = new ArrayList<MatchItem>();

    public void init(FilterConfig filterConfig) throws ServletException {
    }

    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain) throws IOException, ServletException {

        if (Boolean.TRUE.equals(request.getAttribute(EXTENSION_REQ_PROP_KEY))) {
            chain.doFilter(request, response);
            return;
        }

        request.setAttribute(EXTENSION_REQ_PROP_KEY, Boolean.TRUE);

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        String reqUri = req.getRequestURI();

        MatchItem firstMatchItem = null;
        for (MatchItem matchItem : matchItems) {
            if (reqUri.matches(matchItem.getUrl())) {
                firstMatchItem = matchItem;
                break;
            }
        }

        if (firstMatchItem != null) {
            String origin = req.getHeader("Origin");

            String[] origins = new String[0];
            if (origin != null && !origin.isEmpty()) {
                origins = origin.split(" ");
            }

            boolean prohibted = false;
            for (String o : origins) {
                if (!firstMatchItem.isAllowed(o)) {
                    prohibted = true;
                    break;
                }
            }
            if (prohibted) {
                resp.sendError(HttpServletResponse.SC_FORBIDDEN);
                return;
            }
        }

        chain.doFilter(request, response);
    }

    public void destroy() {

    }

}
