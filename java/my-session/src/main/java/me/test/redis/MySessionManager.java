
package me.test.redis;

import java.security.SecureRandom;
import java.util.Random;

import javax.servlet.ServletContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import me.test.MySessionFilter;
import me.test.SessionManager;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;

public class MySessionManager implements SessionManager {

    private Logger logger = LoggerFactory.getLogger(MySessionManager.class);
    private Random random = new SecureRandom();

    private String sessionPath;
    private String sessionIdCookieName = "JSESSIONID";
    // private String sessionIdPathParameterNamePrefix = ";" + sessionCookieName + "="; FIXME
    private String sessionIdUrlMatrixParamName = "jsessionid";
    private int timeout = 30;
    private RedisTemplate<String, Object> redisTemplate;

    public HttpSession newHttpSession() {

        // 创建SessionId
        byte[] randomBytes = new byte[6];
        String id;
        do {
            random.nextBytes(randomBytes);
            id = Base64.encodeBase64String(randomBytes);
        } while (redisTemplate.hasKey(id));

        // 创建session
        MySession session = new MySession(id, this);

        // 初始化session
        long curTimeMillis = System.currentTimeMillis();
        redisTemplate.opsForHash().put(session.getRedisHashKey(), MySession.MEATDATA_KEY_CREATE_TIME, curTimeMillis);
        session.setMaxInactiveInterval(timeout * 60);

        Cookie cookie = this.newSessionCookie(session.getId());
        MySessionFilter.responseHolder.get().addCookie(cookie);

        return session;
    }

    public String getSessionPath() {

        if (StringUtils.isEmpty(sessionPath)) {
            return MySessionFilter.servletContextHolder.get().getContextPath();
        }
        return sessionPath;
    }

    public HttpSession getHttpSession(String sessionId) {

        if (!isSessionValid(sessionId)) {
            return null;
        }
        // 创建session
        MySession session = new MySession(sessionId, this);
        return session;
    }

    public void setSessionPath(String sessionPath) {

        this.sessionPath = sessionPath;
    }

    public void setSessionIdCookieName(String sessionIdCookieName) {

        if (StringUtils.isBlank(sessionIdCookieName)) {
            throw new IllegalArgumentException("session cookie name can not be empty");
        }

        this.sessionIdCookieName = sessionIdCookieName;
    }

    public String getSessionIdUrlMatrixParamName() {

        return sessionIdUrlMatrixParamName;
    }

    public void setSessionIdUrlMatrixParamName(String sessionIdUrlMatrixParamName) {

        this.sessionIdUrlMatrixParamName = sessionIdUrlMatrixParamName;
    }

    // public String getSessionIdPathParameterNamePrefix() {
    //
    // return sessionIdPathParameterNamePrefix;
    // }
    //
    // public void setSessionIdPathParameterNamePrefix(String sessionIdPathParameterNamePrefix) {
    //
    // this.sessionIdPathParameterNamePrefix = sessionIdPathParameterNamePrefix;
    // }

    public ServletContext getServletContext() {

        return MySessionFilter.servletContextHolder.get();
    }

    public void accessSession(HttpSession session) {

        MySession mySession = (MySession) session;
        mySession.access();
    }

    public HttpServletRequest wrapHttpSevletRequest() {

        return new MyRequest(this);
    }

    public HttpServletResponse wrapHttpSevletResponse() {

        return new MyResponse(this);
    }

    private Cookie newSessionCookie(String sessionId) {

        HttpServletRequest request = MySessionFilter.requestHolder.get();
        Cookie cookie = new Cookie(getSessionIdCookieName(), sessionId);
        cookie.setSecure(request.isSecure());
        cookie.setMaxAge(-1);

        String path = sessionPath != null ? sessionPath : request.getContextPath();
        path = (path == null || path.length() == 0) ? "/" : path;
        cookie.setPath(path);

        return cookie;
    }

    public boolean isSessionValid(String sessionId) {

        return redisTemplate.hasKey(getRedisHashKey(sessionId));
    }

    public String getSessionIdCookieName() {

        return sessionIdCookieName;
    }

    String getRedisHashKey(String sessionId) {

        return MySession.PREFIX + "." + sessionId;
    }

    public int getTimeout() {

        return timeout;
    }

    public void setTimeout(int timeout) {

        this.timeout = timeout;
    }

    public RedisTemplate<String, Object> getRedisTemplate() {

        return redisTemplate;
    }

    public void setRedisTemplate(RedisTemplate<String, Object> redisTemplate) {

        this.redisTemplate = redisTemplate;
    }

    public HttpSession newHttpSession(HttpServletRequest request) {

        // TODO Auto-generated method stub
        return null;
    }

    // public boolean isUsingCookies() {
    //
    // return true;
    // }

}
