
package me.test;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public interface SessionManager {

    HttpServletRequest wrapHttpSevletRequest();

    HttpServletResponse wrapHttpSevletResponse();

    HttpSession newHttpSession();

    // 不出错
    HttpSession getHttpSession(String sessionId);

    ServletContext getServletContext();

    void accessSession(HttpSession session);

    boolean isSessionValid(String sessionId);

    String getSessionIdCookieName();

    String getSessionIdUrlMatrixParamName();

    String getSessionPath();

}
