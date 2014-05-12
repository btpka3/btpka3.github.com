
package me.test;

import java.security.SecureRandom;
import java.util.Random;

import javax.servlet.ServletRequest;

public class SessionIdGenerator {

    private Random random = new SecureRandom();

    public String generateSessionId(ServletRequest request) {

        byte[] randomBytes = new byte[128];
        random.nextBytes(randomBytes);

        String newId = (String) request.getAttribute(__NEW_SESSION_ID);
        if (newId != null) {
            return newId;
        }
    }
}
