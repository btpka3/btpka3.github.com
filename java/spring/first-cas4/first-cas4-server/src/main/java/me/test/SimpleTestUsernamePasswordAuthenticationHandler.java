package me.test;

import java.security.GeneralSecurityException;

import javax.security.auth.login.AccountNotFoundException;
import javax.security.auth.login.FailedLoginException;

import org.jasig.cas.authentication.HandlerResult;
import org.jasig.cas.authentication.PreventedException;
import org.jasig.cas.authentication.UsernamePasswordCredential;
import org.jasig.cas.authentication.handler.support.AbstractUsernamePasswordAuthenticationHandler;
import org.jasig.cas.authentication.principal.SimplePrincipal;
import org.springframework.util.StringUtils;

/**
 *  仅可用作测试。
 */
public class SimpleTestUsernamePasswordAuthenticationHandler extends AbstractUsernamePasswordAuthenticationHandler {

    @Override
    protected HandlerResult authenticateUsernamePasswordInternal(UsernamePasswordCredential credential)
            throws GeneralSecurityException, PreventedException {

        final String username = credential.getUsername();
        final String password = credential.getPassword();

        if (!StringUtils.hasText(username)) {
            throw new AccountNotFoundException("username can not be blank.");
        }
        if (!StringUtils.hasText(password)) {
            throw new FailedLoginException("password can not be blank.");
        }
        if (!username.equals(password)) {
            throw new FailedLoginException("password is not equal with username.");
        }
        return createHandlerResult(credential, new SimplePrincipal(username), null);
    }
}
