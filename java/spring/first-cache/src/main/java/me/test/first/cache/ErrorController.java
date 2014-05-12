
package me.test.first.cache;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ErrorController {

    private final Logger logger = LoggerFactory.getLogger(ErrorController.class);

    @RequestMapping(value = { "/error" })
    public String error(HttpServletResponse resp) {

        logger.debug("==========================error()");
        return "error";
    }

}
