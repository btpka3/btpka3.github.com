
package me.test.bak;

import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;

@SuppressWarnings("deprecation")
public class MySession implements HttpSession {

    private Logger logger = LoggerFactory.getLogger(MySession.class);
//    public static final String SESSION_MAP_KEY = MySession.class.getName() + ".SESSION_MAP_KEY";

    private static ThreadLocal<HttpServletRequest> requestHolder = new ThreadLocal<HttpServletRequest>();
    private static ThreadLocal<HttpServletResponse> responseHolder = new ThreadLocal<HttpServletResponse>();

    private static final MySession instance = new MySession();
    private RedisTemplate<Object, Object> redisTemplate; //FIXME
    private MySessionManager sessionManager; // FIXME

    public String id; // FIXME

    private static final HttpSessionContext _nullSessionContext = new HttpSessionContext() {

        public HttpSession getSession(String sessionId) {

            return null;
        }

        @SuppressWarnings({ "rawtypes", "unchecked" })
        public Enumeration getIds() {

            return Collections.enumeration(Collections.EMPTY_LIST);
        }
    };

    private MySession() {

    }

    public static MySession getInstance(HttpServletRequest request, HttpServletResponse response) {

        requestHolder.set(request);
        responseHolder.set(response);

        return instance;

    }

    private void checkValid() {

        if (!sessionManager.isValid(getId())) {
            throw new IllegalStateException();
        }

    }

    // @SuppressWarnings("unchecked")
    // private Map<Object, Object> getSessionMap() {
    //
    // HttpServletRequest req = requestHolder.get();
    //
    // Object sessionMapObj = req.getAttribute(SESSION_MAP_KEY);
    // Map<Object, Object> sessionMap = null;
    // if (sessionMapObj == null) {
    // sessionMap = new HashMap<Object, Object>();
    // } else if (!(sessionMapObj instanceof Map)) {
    // logger.warn("session map is overrided by others, rebuild it now");
    // sessionMap = new HashMap<Object, Object>();
    // } else {
    // sessionMap = (Map<Object, Object>) sessionMapObj;
    // }
    // req.setAttribute(SESSION_MAP_KEY, sessionMap);
    //
    // return sessionMap;
    // }

    // //////////////////////////////////////////////////////////////////
    public static final String PREFIX = MySession.class.getName();
    public static final String _REQUEST_KEY_SESSION_ID = PREFIX + ".SESSION_ID";
    public static final String _REQUEST_KEY_SESSION = PREFIX + ".SESSION";
    public static final String _SESSION_KEY_CREATE_TIME = PREFIX + ".CREATE_TIME";
    public static final String _SESSION_KEY_LAST_ACCESS_TIME = PREFIX + ".LAST_ACCESS_TIME";
    public static final String _SESSION_KEY_MAX_INACTIVE_INTERVAL = PREFIX + ".MAX_INACTIVE_INTERVAL";
    public static final String _SESSION_KEY_IS_NEW = PREFIX + ".IS_NEW";

    public long getCreationTime() {

        return (Long) redisTemplate.opsForHash().get(getId(), _SESSION_KEY_CREATE_TIME);
    }

    public String getId() {

        return id;
    }

    public long getLastAccessedTime() {

        checkValid();
        Long lastAccessedTime = (Long) redisTemplate.opsForHash().get(getId(), _SESSION_KEY_LAST_ACCESS_TIME);
        return lastAccessedTime == null ? lastAccessedTime : 0;
    }

    public ServletContext getServletContext() {

        return sessionManager.getServletContext();
    }

    public void setMaxInactiveInterval(int interval) {

        redisTemplate.opsForHash().put(getId(), _SESSION_KEY_MAX_INACTIVE_INTERVAL, interval);

    }

    public int getMaxInactiveInterval() {

        Integer interval = (Integer) redisTemplate.opsForHash().get(getId(), _SESSION_KEY_MAX_INACTIVE_INTERVAL);
        return interval != null ? interval : 0;
    }

    public HttpSessionContext getSessionContext() {

        checkValid();
        return _nullSessionContext;
    }

    public Object getAttribute(String name) {

        checkValid();
        return redisTemplate.opsForHash().get(getId(), name);
    }

    public Object getValue(String name) {

        return getAttribute(name);
    }

    @SuppressWarnings("rawtypes")
    public Enumeration getAttributeNames() {

        checkValid();
        return Collections.enumeration(redisTemplate.opsForHash().keys(getId()));
    }

    public String[] getValueNames() {

        checkValid();
        return (String[]) redisTemplate.opsForHash().keys(getId()).toArray();
    }

    public void setAttribute(String name, Object value) {

        if (name == null) {
            throw new NullPointerException("Session attribute name can not be null");
        }
        checkValid();
        redisTemplate.opsForHash().put(getId(), name, value);

    }

    public void putValue(String name, Object value) {

        setAttribute(name, value);

    }

    public void removeAttribute(String name) {

        if (name == null) {
            throw new NullPointerException("Session attribute name can not be null");
        }
        checkValid();
        redisTemplate.opsForHash().delete(getId(), name);

    }

    public void removeValue(String name) {

        removeAttribute(name);

    }

    public void invalidate() {

        checkValid();

        sessionManager.removeSession(getId());

    }

    public boolean isNew() {

        checkValid();
        Boolean isNew = (Boolean) redisTemplate.opsForHash().get(getId(), _SESSION_KEY_IS_NEW);
        return isNew == null ? true : isNew;
    }

}
