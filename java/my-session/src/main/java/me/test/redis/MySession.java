
package me.test.redis;

import java.util.Calendar;
import java.util.Collections;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionContext;

import me.test.MySessionFilter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;

@SuppressWarnings("deprecation")
public class MySession implements HttpSession {

    private Logger logger = LoggerFactory.getLogger(MySession.class);

    private MySessionManager sessionManager;
    private RedisTemplate<String, Object> redisTemplate;
    private String redisHashKey;

    private final String id;
    private boolean isNew = true;

    private static final HttpSessionContext NULL_SESSION_CONTEXT = new HttpSessionContext() {

        public HttpSession getSession(String sessionId) {

            return null;
        }

        @SuppressWarnings({ "rawtypes", "unchecked" })
        public Enumeration getIds() {

            return Collections.enumeration(Collections.EMPTY_LIST);
        }
    };

    public MySession(String id, MySessionManager sessionManager) {

        this.id = id;
        this.isNew = true;
        this.sessionManager = sessionManager;
        this.redisTemplate = sessionManager.getRedisTemplate();
        this.redisHashKey = sessionManager.getRedisHashKey(id);
    }

    private void checkAttributeValid(String name) {

        if (name == null) {
            throw new NullPointerException("Session attribute name can not be null");
        }
        if (MEATDATA_KEY_CREATE_TIME.equals(name)
                || MEATDATA_KEY_LAST_ACCESS_TIME.equals(name)
                || MEATDATA_KEY_MAX_INACTIVE_INTERVAL.equals(name)) {
            throw new IllegalArgumentException("attribute name \"" + name + "\" is reverse for internal useage.");
        }
    }

    // //////////////////////////////////////////////////////////////////
    public static final String PREFIX = MySession.class.getName();
    public static final String MEATDATA_KEY_CREATE_TIME = PREFIX + ".CREATE_TIME";
    public static final String MEATDATA_KEY_LAST_ACCESS_TIME = PREFIX + ".LAST_ACCESS_TIME";
    public static final String MEATDATA_KEY_MAX_INACTIVE_INTERVAL = PREFIX + ".MAX_INACTIVE_INTERVAL";

    public long getCreationTime() {

        return (Long) redisTemplate.opsForHash().get(redisHashKey, MEATDATA_KEY_CREATE_TIME);
    }

    public String getId() {

        return id;
    }

    private void checkValid() {

        if (!sessionManager.isSessionValid(getId())) {
            throw new IllegalStateException();
        }

    }

    public long getLastAccessedTime() {

        checkValid();

        Long lastAccessedTime = (Long) redisTemplate.opsForHash().get(redisHashKey, MEATDATA_KEY_LAST_ACCESS_TIME);
        return lastAccessedTime != null ? lastAccessedTime : 0;
    }

    protected void access() {

        long curTimeMillis = System.currentTimeMillis();

        redisTemplate.opsForHash().put(redisHashKey, MEATDATA_KEY_LAST_ACCESS_TIME, curTimeMillis);
        Calendar expireTime = Calendar.getInstance();
        expireTime.setTimeInMillis(curTimeMillis);
        expireTime.add(Calendar.SECOND, this.getMaxInactiveInterval());
        redisTemplate.expireAt(redisHashKey, expireTime.getTime());
    }

    public ServletContext getServletContext() {

        return MySessionFilter.servletContextHolder.get();
    }

    public void setMaxInactiveInterval(int interval) {

        long createTime = this.getCreationTime();

        Calendar expireTime = Calendar.getInstance();
        expireTime.setTimeInMillis(createTime);
        expireTime.add(Calendar.SECOND, interval);

        redisTemplate.opsForHash().put(redisHashKey, MEATDATA_KEY_MAX_INACTIVE_INTERVAL, interval);
        redisTemplate.expireAt(redisHashKey, expireTime.getTime());

    }

    public int getMaxInactiveInterval() {

        Integer interval = (Integer) redisTemplate.opsForHash().get(redisHashKey, MEATDATA_KEY_MAX_INACTIVE_INTERVAL);
        return interval != null ? interval : 0;
    }

    public HttpSessionContext getSessionContext() {

        checkValid();
        return NULL_SESSION_CONTEXT;
    }

    public Object getAttribute(String name) {

        checkAttributeValid(name);
        checkValid();

        return sessionManager.getRedisTemplate().opsForHash().get(redisHashKey, name);
    }

    public Object getValue(String name) {

        return getAttribute(name);
    }

    @SuppressWarnings("rawtypes")
    public Enumeration getAttributeNames() {

        checkValid();
        HashOperations<String, String, Object> hashOps = redisTemplate.opsForHash();
        Set<String> attrNameSet = hashOps.keys(redisHashKey);
        attrNameSet.remove(MEATDATA_KEY_CREATE_TIME);
        attrNameSet.remove(MEATDATA_KEY_LAST_ACCESS_TIME);
        attrNameSet.remove(MEATDATA_KEY_MAX_INACTIVE_INTERVAL);
        List<String> attrNameList = new LinkedList<String>(attrNameSet);
        Collections.sort(attrNameList);
        return Collections.enumeration(attrNameList);
    }

    public String[] getValueNames() {

        checkValid();
        return (String[]) redisTemplate.opsForHash().keys(redisHashKey).toArray();
    }

    public void setAttribute(String name, Object value) {

        checkAttributeValid(name);
        checkValid();
        redisTemplate.opsForHash().put(redisHashKey, name, value);

    }

    public void putValue(String name, Object value) {

        setAttribute(name, value);

    }

    public void removeAttribute(String name) {

        checkAttributeValid(name);
        checkValid();
        redisTemplate.opsForHash().delete(redisHashKey, name);

    }

    public void removeValue(String name) {

        removeAttribute(name);

    }

    public void invalidate() {

        checkValid();
        redisTemplate.delete(redisHashKey);

    }

    public String getRedisHashKey() {

        return redisHashKey;
    }

    public boolean isNew() {

        checkValid();
        return this.isNew;
    }

}
