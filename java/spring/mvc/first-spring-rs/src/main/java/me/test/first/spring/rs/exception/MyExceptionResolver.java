package me.test.first.spring.rs.exception;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.DefaultHandlerExceptionResolver;

public class MyExceptionResolver extends DefaultHandlerExceptionResolver {

    @Override
    protected ModelAndView doResolveException(HttpServletRequest request, HttpServletResponse response, Object handler,
            Exception ex) {

        try {
            if (ex instanceof BusinessException) {
                return handleBusinessException((BusinessException) ex, request, response, handler);
            }
        } catch (IOException e) {
            logger.warn("Handling of [" + ex.getClass().getName() + "] resulted in Exception", e);
        }
        return super.doResolveException(request, response, handler, ex);
    }

    protected ModelAndView handleBusinessException(BusinessException ex,
            HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {

        response.sendError(ex.getHttpStatus().value());
        return new ModelAndView();
    }

}
