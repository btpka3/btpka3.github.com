
package me.test.redis;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import me.test.SessionManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MyRequest extends HttpServletRequestWrapper {

    private Logger logger = LoggerFactory.getLogger(MyRequest.class);

    private HttpServletResponse response;
    private SessionManager sessionManager;
    private HttpSession session;

    private String requestedSessionId;
    private boolean requestedSessionIdFromCookie = false;

    public MyRequest(HttpServletRequest request, HttpServletResponse response, SessionManager sessionManager) {

        super(request);
        this.response = response;
        this.sessionManager = sessionManager;

        // 从request中获取 sessionID
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
                if (sessionManager.getSessionIdCookieName().equalsIgnoreCase(cookies[i].getName())) {
                    requestedSessionId = cookies[i].getValue();
                    requestedSessionIdFromCookie = true;

                    logger.debug("Got Session ID {} from cookie", requestedSessionId);

                    if (requestedSessionId != null) {
                        if (sessionManager.isSessionValid(requestedSessionId)) {
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

        String prefix = sessionManager.getSessionIdUrlMatrixParamName();
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
                if (sessionManager.isSessionValid(requestedSessionId)) {
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
            if (!sessionManager.isSessionValid(session.getId())) {
                return session;
            } else {
                session = null;
            }
        }

        if (!create) {
            return null;
        }

        session = sessionManager.newHttpSession(this);

        Cookie cookie = sessionManager.newSessionCookie(this, response, session.getId());
        response.addCookie(cookie);

        return session;

    }

    public HttpSession getSession() {

        return getSession(true);
    }

    public boolean isRequestedSessionIdValid() {

        return sessionManager.isSessionValid(this.getRequestedSessionId());
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
