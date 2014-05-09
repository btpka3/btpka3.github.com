
package me.test;

import javax.servlet.ServletContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public interface SessionManager {

    HttpServletRequest wrapHttpSevletRequest(HttpServletRequest rquest, HttpServletResponse response);

    HttpServletResponse wrapHttpSevletResponse(HttpServletRequest rquest, HttpServletResponse response);

    Cookie newSessionCookie(HttpServletRequest request, HttpServletResponse response, String sessionId);

    HttpSession newHttpSession(HttpServletRequest request);

    HttpSession getHttpSession(String sessionId);

    void removeSession(String sessionId);

    ServletContext getServletContext();

    void accessSession(String sessionId);

    boolean isSessionValid(String sessionId);

    String getSessionIdCookieName();

    String getSessionIdUrlMatrixParamName();

    String getSessionPath();

}
