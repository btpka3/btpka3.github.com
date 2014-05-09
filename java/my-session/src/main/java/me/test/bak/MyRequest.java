
package me.test.bak;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MyRequest extends HttpServletRequestWrapper {

    private Logger logger = LoggerFactory.getLogger(MyRequest.class);

    // private InRequestSession session;
    private HttpServletResponse response;
    private MySessionManager sessionManager = null; // FIXME
    private HttpSession session;

    private String requestedSessionId;
    private boolean requestedSessionIdFromCookie = false;

    public MyRequest(HttpServletRequest request, HttpServletResponse response) {

        super(request);
        this.response = response;
        // 获取 sessionID

        readRequestedSessionId();

    }

    private void readRequestedSessionId() {

        readRequestedSessionIdFromCookie();

        if (requestedSessionId == null || session == null) {
            readRequestedSessionIdFromURL();
        }
        if (session != null) {
            sessionManager.accessSession(session.getId());
        }
    }

    private void readRequestedSessionIdFromCookie() {

        Cookie[] cookies = getCookies();
        if (cookies != null && cookies.length > 0) {
            for (int i = 0; i < cookies.length; i++) {
                if (sessionManager.getSessionCookieName().equalsIgnoreCase(cookies[i].getName())) {
                    requestedSessionId = cookies[i].getValue();
                    requestedSessionIdFromCookie = true;

                    logger.debug("Got Session ID {} from cookie", requestedSessionId);

                    if (requestedSessionId != null) {
                        if (sessionManager.isValid(requestedSessionId)) {
                            session = sessionManager.getHttpSession(requestedSessionId);
                            break;
                        }
                    } else {
                        logger.warn("null session id from cookie");
                    }
                }
            }
        }
    }

    private void readRequestedSessionIdFromURL() {

        String uri = getRequestURI();

        String prefix = sessionManager.getSessionIdPathParameterNamePrefix();
        if (prefix != null) {
            int s = uri.indexOf(prefix);
            if (s >= 0) {
                s += prefix.length();
                int i = s;
                while (i < uri.length()) {
                    char c = uri.charAt(i);
                    if (c == ';' || c == '#' || c == '?' || c == '/')
                        break;
                    i++;
                }

                requestedSessionId = uri.substring(s, i);
                requestedSessionIdFromCookie = false;
                if (sessionManager.isValid(requestedSessionId)) {
                    session = sessionManager.getHttpSession(requestedSessionId);
                } else {
                    session = null;
                }

                logger.debug("Got Session ID {} from URL", requestedSessionId);
            }
        }
    }

    public String getRequestedSessionId() {

        return requestedSessionId;
    }

    public HttpSession getSession(boolean create) {

        // 可用HttpSession对象是否已经创建了？
        if (session != null) {
            if (!sessionManager.isValid(session.getId())) {
                return session;
            } else {
                session = null;
            }
        }

        if (!create) {
            return null;
        }

        session = sessionManager.createHttpSession();

        String sessionPath = sessionManager.getSessionPath();
        sessionPath = sessionPath != null ? sessionPath : this.getContextPath();
        sessionPath = (sessionPath == null || sessionPath.length() == 0) ? "/" : sessionPath;

        Cookie cookie = null;// TODO
        response.addCookie(cookie);

        return session;

    }

    public HttpSession getSession() {

        return getSession(true);
    }

    public boolean isRequestedSessionIdValid() {

        return sessionManager.isValid(this.getRequestedSessionId());
    }

    public boolean isRequestedSessionIdFromCookie() {

        return requestedSessionId != null && requestedSessionIdFromCookie;
    }

    public boolean isRequestedSessionIdFromURL() {

        return requestedSessionId != null && !requestedSessionIdFromCookie;
    }

    public boolean isRequestedSessionIdFromUrl() {

        return isRequestedSessionIdFromURL();
    }

}
