
package me.test.first.cache;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class MyExceptionHandler {

    private final Logger logger = LoggerFactory.getLogger(MyExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    public void handleIOException(Exception ex, HttpServletResponse resp) throws IOException {

        logger.error("~~~~~~~~~~~~~~~~~~~~~~~~~~ ERROR.", ex);
        resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "aaaaaaaa");
    }

}
