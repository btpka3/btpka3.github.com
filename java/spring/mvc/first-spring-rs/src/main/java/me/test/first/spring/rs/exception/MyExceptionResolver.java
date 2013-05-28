package me.test.first.spring.rs.exception;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.AbstractHandlerExceptionResolver;

public class MyExceptionResolver extends AbstractHandlerExceptionResolver {

    @Override
    protected ModelAndView doResolveException(HttpServletRequest request, HttpServletResponse response, Object handler,
            Exception ex) {

        try {
            if (ex instanceof BusinessException) {
                return handleBusinessException((BusinessException) ex, request, response, handler);
            }
            return handlException(ex, request, response, handler);
        } catch (IOException e) {
            logger.warn("Handling of [" + ex.getClass().getName() + "] resulted in Exception", e);
        }
        return null;
    }

    protected ModelAndView handleBusinessException(BusinessException ex,
            HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {

        response.sendError(ex.getHttpStatus(), ex.getMessage());
        return new ModelAndView();
    }

    protected ModelAndView handlException(Exception ex,
            HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {

        response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage());
        return new ModelAndView();
    }

}
