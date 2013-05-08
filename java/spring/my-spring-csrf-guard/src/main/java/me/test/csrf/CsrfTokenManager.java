package me.test.csrf;

import java.util.UUID;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.support.SimpleCacheManager;

/**
 *
 * Reference:
 * <ol>
 * <li><a href=
 * "https://www.owasp.org/index.php/Cross-Site_Request_Forgery_(CSRF)_Prevention_Cheat_Sheet"
 * > CSRF </a></li>
 * <li><a href=
 * "http://blog.eyallupu.com/2012/04/csrf-defense-in-spring-mvc-31.html">
 * SpringMVC-3.1-CSRF </a></li>
 * </ol>
 *
 * Notice: In product environment, please set one CacheManager which support TTL
 * etc for avoid memory leak.
 *
 * @author zhangliangliang
 *
 */
public class CsrfTokenManager {

    /** request parameter name for CSRF token */
    private String tokenName = "_CSRF_TOKEN";

    /** used for CSRF token signature */
    private String saltKey = UUID.randomUUID().toString();

    /** how long (in millisecond) the CSRF token would live */
    private long expiryTime;

    private CacheManager cacheManager = new SimpleCacheManager();

    private String cacheName = CsrfTokenManager.class.getName();

    /**
     * Generate a CSRF token and put it into cache.
     *
     * @return a new CSRF token
     */
    public String generateToken() {
        long curTime = System.currentTimeMillis();
        String signature = DigestUtils.md5Hex((curTime + expiryTime) + ":" + saltKey);
        String plainTextCsrfToken = expiryTime + ":" + signature;
        String csrfToken = Base64.encodeBase64String(plainTextCsrfToken.getBytes());
        Cache cache = cacheManager.getCache(cacheName);
        cache.put(csrfToken, csrfToken);
        return csrfToken;
    }

    /**
     * check the CSRF token.
     *
     * @param base64CsrfToken
     *            the CSRF token form request parameter
     * @throws InvalidCsrfTokenException
     */
    public void checkToken(String base64CsrfToken) throws InvalidCsrfTokenException {

        if (!Base64.isBase64(base64CsrfToken)) {
            throw new InvalidCsrfTokenException("CSRF token must be base64 encoded.");
        }

        String plainTextCsrfToken = new String(Base64.decodeBase64(base64CsrfToken));
        String[] tokens = plainTextCsrfToken.split(":");
        if (tokens.length != 2) {
            throw new InvalidCsrfTokenException("CSRF token expected to have two parts sperated by colon.");
        }

        long tokenExpiryTime;
        try {
            tokenExpiryTime = Long.valueOf(tokens[0]);
        } catch (NumberFormatException e) {
            throw new RuntimeException("CSRF token first part expected to time in millisecond, but was " + tokens[0]
                    + ".");
        }

        String expectedTokenSignature = DigestUtils.md5Hex(tokenExpiryTime + ":" + saltKey);
        if (!expectedTokenSignature.equals(tokens[1])) {
            throw new RuntimeException("CSRF token's signature is not matched.");
        }

        if (tokenExpiryTime > System.currentTimeMillis()) {
            throw new RuntimeException("CSRF token is expired.");
        }

        if (cacheManager.getCache(cacheName).get(base64CsrfToken) != null) {
            throw new RuntimeException("CSRF token has bean used.");
        }
    }

    public void deleteToken(String base64CsrfToken) {
        cacheManager.getCache(cacheName).evict(base64CsrfToken);
    }

    public String getTokenName() {
        return tokenName;
    }

    public void setTokenName(String tokenName) {
        this.tokenName = tokenName;
    }

    public String getSaltKey() {
        return saltKey;
    }

    public void setSaltKey(String saltKey) {
        this.saltKey = saltKey;
    }

    public CacheManager getCacheManager() {
        return cacheManager;
    }

    public void setCacheManager(CacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }

    public String getCacheName() {
        return cacheName;
    }

    public void setCacheName(String cacheName) {
        this.cacheName = cacheName;
    }

    public long getExpiryTime() {
        return expiryTime;
    }

    public void setExpiryTime(long expiryTime) {
        this.expiryTime = expiryTime;
    }

}
