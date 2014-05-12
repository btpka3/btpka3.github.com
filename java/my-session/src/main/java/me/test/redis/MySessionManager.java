
package me.test.redis;

import javax.servlet.ServletContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import me.test.SessionManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MySessionManager implements SessionManager {

    private Logger logger = LoggerFactory.getLogger(MySessionManager.class);

    private String sessionPath;
    private String sessionCookieName = "JSESSIONID";
    private String sessionIdPathParameterNamePrefix = ";" + sessionCookieName + "=";
    private int defaultTimeoutInSecond = 30 * 60;

    public HttpSession newHttpSession() {

        // TODO
        return null;
    }

    public boolean isValid(String sessionId) {

        return false;// TODO
    }

    public String getSessionPath() {

        return sessionPath;
    }

    public HttpSession getHttpSession(String sessionId) {

        return null;// TODO
    }

    public HttpSession createHttpSession() {

        return null;// TODO
    }

    public void removeSession(String sessionId) {

        // TODO
    }

    public void setSessionPath(String sessionPath) {

        this.sessionPath = sessionPath;
    }

    public String getSessionCookieName() {

        return sessionCookieName;
    }

    public void setSessionCookieName(String sessionCookieName) {

        if (sessionCookieName == null || sessionCookieName.length() == 0) {
            throw new IllegalArgumentException("session cookie name can not be empty");
        }

        this.sessionCookieName = sessionCookieName;
    }

    public String getSessionIdPathParameterNamePrefix() {

        return sessionIdPathParameterNamePrefix;
    }

    public void setSessionIdPathParameterNamePrefix(String sessionIdPathParameterNamePrefix) {

        this.sessionIdPathParameterNamePrefix = sessionIdPathParameterNamePrefix;
    }

    public ServletContext getServletContext() {

        return null;
    }

    public void accessSession(String sessionId) {

    }

    public HttpServletRequest wrapHttpSevletRequest(HttpServletRequest rquest,HttpServletResponse response) {

        // TODO Auto-generated method stub
        MyRequest myRequest = new MyRequest(rquest,response)
        return null;
    }

    public HttpServletResponse wrapHttpSevletResponse(HttpServletRequest rquest, HttpServletResponse response) {

        // TODO Auto-generated method stub
        return null;
    }

    public Cookie newSessionCookie(HttpServletRequest request, HttpServletResponse response, String sessionId) {

        Cookie cookie = new Cookie(getSessionCookieName(), sessionId);
        cookie.setSecure(request.isSecure());
        cookie.setMaxAge(0);

        String path = sessionPath != null ? sessionPath : request.getContextPath();
        path = (path == null || path.length() == 0) ? "/" : path;
        cookie.setPath(path);

        request.get
        
        return cookie;
    }

    public HttpSession newHttpSession(HttpServletRequest request) {

        // TODO Auto-generated method stub
        return null;
    }

    public boolean isSessionValid(String sessionId) {

        // TODO Auto-generated method stub
        return false;
    }

    public String getSessionIdCookieName() {

        // TODO Auto-generated method stub
        return null;
    }

    public String getSessionIdUrlMatrixParamName() {

        // TODO Auto-generated method stub
        return null;
    }

    // public boolean isUsingCookies() {
    //
    // return true;
    // }

}
