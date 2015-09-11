
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
import javax.servlet.http.HttpServletRequestWrapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 根据Nginx HTTPS反向代理的追加的HTTP头重载HttpServerletRequest的部分方法。
 * <p>
 * 比如：getScheme()、isSecure()等。 需要Nginx服务在配置HTTPS反向代理时设置：
 *
 * <pre>
 *  location / {
 *      # ...
 *      proxy_pass              http://his;
 *      proxy_set_header        Host                $host;   # ???  $http_host;
 *      proxy_set_header        X-Real-IP           $remote_addr;
 *      proxy_set_header        X-Forwarded-For     $proxy_add_x_forwarded_for;
 *      proxy_set_header        X-Forwarded-Proto   $scheme;
 *      add_header              Front-End-Https     on;
 *      # ...
 * }
 *
 * <pre>
 * </p>
 *
 */
public class ReverseProxyInfoFilter implements Filter {

    private Logger logger = LoggerFactory.getLogger(ReverseProxyInfoFilter.class);

    public void init(FilterConfig filterConfig) throws ServletException {

    }

    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain) throws IOException, ServletException {

        ServletRequest req = request;

        if (request instanceof HttpServletRequest) {

            // 调试信息，调试在wrap之前的request信息。
            if (logger.isDebugEnabled()) {
                HttpServletRequest httpReq = (HttpServletRequest) request;
                logger.debug("================================================");
                logger.debug("scheme = {}", httpReq.getScheme());
                logger.debug("serverName = {}", httpReq.getServerName());
                logger.debug("serverPort = {}", httpReq.getServerPort());
                logger.debug("serverPath = {}", httpReq.getServletPath());
                logger.debug("pathInfo = {}", httpReq.getPathInfo());
                logger.debug("pathTranslated = {}", httpReq.getPathTranslated());
                logger.debug("requestURI = {}", httpReq.getRequestURI());
                logger.debug("requestURL = {}", httpReq.getRequestURL());
                logger.debug("protocol = {}", httpReq.getProtocol());
                logger.debug("queryString = {}", httpReq.getQueryString());
                logger.debug("authType = {}", httpReq.getAuthType());
                logger.debug("header: ");

                @SuppressWarnings("unchecked")
                Enumeration<String> headerNames = httpReq.getHeaderNames();
                while (headerNames.hasMoreElements()) {
                    String headerName = headerNames.nextElement();
                    String headerValue = httpReq.getHeader(headerName);
                    logger.debug("\t{} : {}", headerName, headerValue);
                }
            }
            req = new ReverseProxyInfoRequest((HttpServletRequest) request);
        }

        chain.doFilter(req, response);
    }

    public void destroy() {

    }

    public static class ReverseProxyInfoRequest extends HttpServletRequestWrapper {

        public ReverseProxyInfoRequest(HttpServletRequest request) {

            super(request);
        }

        @Override
        public String getScheme() {

            HttpServletRequest request = (HttpServletRequest) this.getRequest();
            String proto = request.getHeader("X-Forwarded-Proto");

            return proto == null || proto.length() == 0
                    ? request.getScheme()
                    : proto;
        }

        @Override
        public int getServerPort() {

            HttpServletRequest request = (HttpServletRequest) this.getRequest();
            String forwardedFor = request.getHeader("X-Forwarded-For");
            if (forwardedFor == null || forwardedFor.length() == 0) {
                return super.getServerPort();
            }

            return "https".equalsIgnoreCase(this.getScheme()) ? 443 : 80;
        }

        @Override
        public String getRemoteAddr() {

            HttpServletRequest request = (HttpServletRequest) this.getRequest();
            String forwardedFor = request.getHeader("X-Forwarded-For");
            if (forwardedFor == null || forwardedFor.length() == 0) {
                return request.getRemoteAddr();
            }
            String[] ips = forwardedFor.split(",");
            return ips.length == 0
                    ? request.getRemoteAddr()
                    : ips[0].trim();
        }

        @Override
        public String getRemoteHost() {

            return super.getRemoteAddr();
        }

        @Override
        public boolean isSecure() {

            return "https".equalsIgnoreCase(this.getScheme());
        }

        @Override
        public int getRemotePort() {

            HttpServletRequest request = (HttpServletRequest) this.getRequest();
            String forwardedFor = request.getHeader("X-Forwarded-For");
            return forwardedFor == null || forwardedFor.length() == 0
                    ? super.getRemotePort()
                    : -1;
        }

    }

}
