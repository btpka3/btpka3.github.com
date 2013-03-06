package me.test;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

public class MyAuthenticationProvider implements AuthenticationProvider {

    public Authentication authenticate(Authentication authentication)
            throws AuthenticationException {
        // TODO Auto-generated method stub
        return null;
    }

    public boolean supports(Class<?> authentication) {
        // TODO Auto-generated method stub
        return false;
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        // TODO Auto-generated method stub

    }

}
