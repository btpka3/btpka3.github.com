package me.test.csrf;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpMethod;
import org.springframework.util.Assert;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class CsrfHandlerInterceptor extends HandlerInterceptorAdapter {

    private List<HttpMethod> protectedMethods = Arrays.asList(HttpMethod.POST, HttpMethod.PUT, HttpMethod.DELETE);

    private CsrfTokenManager csrfTokenManager;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        HttpMethod reqMethod = HttpMethod.valueOf(request.getMethod().toUpperCase());
        if (!protectedMethods.contains(reqMethod)) {
            return true;
        }

        String[] tokenValus = request.getParameterValues(csrfTokenManager.getTokenName());

        if (tokenValus.length == 0) {
            throw new InvalidCsrfTokenException("There is no csrf token.");
        }
        if (tokenValus.length > 1) {
            throw new InvalidCsrfTokenException("Csrf token has too many valus.");
        }

        csrfTokenManager.checkToken(tokenValus[0]);

        return true;
    }

    public List<HttpMethod> getProtectedMethods() {
        return protectedMethods;
    }

    public void setProtectedMethods(List<HttpMethod> protectedMethods) {
        Assert.notNull(protectedMethods);
        this.protectedMethods = protectedMethods;
    }

}
