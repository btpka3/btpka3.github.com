package me.test.first.spring.rs.controller;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ErrorController {

    private static final Logger logger = LoggerFactory.getLogger(ErrorController.class);

    @RequestMapping("/error")
    @ResponseBody
    public Map<String, Object> handle(HttpServletRequest request, HttpServletResponse resp) {

        if (StringUtils.isEmpty(resp.getHeader("Cache-Control"))) {
            resp.setHeader("Cache-Control", "no-cache, no-store");
        }

        Map<String, Object> map = new LinkedHashMap<String, Object>();
        map.put("status",
                request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE));
        map.put("reason", request.getAttribute(RequestDispatcher.ERROR_MESSAGE));

        if (logger.isDebugEnabled()) {
            logger.debug((String) request.getAttribute(RequestDispatcher.ERROR_MESSAGE),
                    (Exception) request.getAttribute(RequestDispatcher.ERROR_EXCEPTION));
        }
        return map;
    }
}
