
package me.test.redis;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponseWrapper;
import javax.servlet.http.HttpSession;

import me.test.MySessionFilter;
import me.test.SessionManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MyResponse extends HttpServletResponseWrapper {

    private Logger logger = LoggerFactory.getLogger(MyResponse.class);
    private SessionManager sessionManager;

    public MyResponse(SessionManager sessionManager) {

        super(MySessionFilter.responseHolder.get());
        this.sessionManager = sessionManager;
    }

    public String encodeURL(String url) {

        HttpServletRequest request = MySessionFilter.wrappedRequestHolder.get();

        // is cross domain? just return url without modified.
        URI u = null;
        try {
            u = new URI(url);
        } catch (URISyntaxException e) {
            throw new IllegalArgumentException(e);
        }
        String path = u.getPath() == null ? "" : u.getPath();
        if (u.getHost() != null) {
            int port = u.getPort();
            if (port < 0) {
                port = "https".equalsIgnoreCase(u.getScheme()) ? 443 : 80;
            }
            if (!request.getServerName().equalsIgnoreCase(u.getHost())
                    || request.getServerPort() != port
                    || path.startsWith(request.getContextPath())) {
                return url;
            }
        }

        // session id for url not set
        String paramName = sessionManager.getSessionIdUrlMatrixParamName();
        if (paramName == null) {
            return url;
        }
        String sessionURLPrefix = ";" + paramName + "=";

        // is session id from cookie and URL contains session id? remove from url
        if (request.isRequestedSessionIdFromCookie()) {
            int prefix = url.indexOf(sessionURLPrefix);
            if (prefix != -1) {
                int suffix = url.indexOf("?", prefix);
                if (suffix < 0) {
                    suffix = url.indexOf("#", prefix);
                }
                if (suffix <= prefix) {
                    return url.substring(0, prefix);
                }
                return url.substring(0, prefix) + url.substring(suffix);
            }
            return url;
        }

        // url already contains session id? replace with actual session id.
        HttpSession session = request.getSession(false);
        if (session == null) {
            return url;
        }
        // Already encoded
        String id = session.getId();
        int prefix = url.indexOf(sessionURLPrefix);
        if (prefix != -1) {
            int suffix = url.indexOf("?", prefix);
            if (suffix < 0) {
                suffix = url.indexOf("#", prefix);
            }

            if (suffix <= prefix) {
                return url.substring(0, prefix + sessionURLPrefix.length()) + id;
            }
            return url.substring(0, prefix + sessionURLPrefix.length()) + id +
                    url.substring(suffix);
        }

        // insert session id to url
        int suffix = url.indexOf('?');
        if (suffix < 0) {
            suffix = url.indexOf('#');
        }
        String rootPath = ("https".equalsIgnoreCase(u.getScheme()) || "http".equalsIgnoreCase(u.getScheme())) && u.getPath() == null ? "/" : "";
        // if no path, insert the root path
        if (suffix < 0) {
            return url + rootPath + sessionURLPrefix + id;
        }
        // if no path so insert the root path
        return url.substring(0, suffix) + rootPath + sessionURLPrefix + id + url.substring(suffix);

    }

    public String encodeUrl(String url) {

        return encodeURL(url);
    }

    public String encodeRedirectURL(String url) {

        return encodeURL(url);
    }

    public String encodeRedirectUrl(String url) {

        return encodeRedirectURL(url);
    }

}
