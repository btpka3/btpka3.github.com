package me.test.first.spring.boot.webflux.controller;

import org.springframework.boot.autoconfigure.web.ErrorProperties;
import org.springframework.boot.autoconfigure.web.servlet.error.BasicErrorController;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorViewResolver;
import org.springframework.boot.web.servlet.error.ErrorAttributes;

import java.util.List;

/**
 * @author 当千
 * @date 2018-12-06
 */
public class ErrorController extends BasicErrorController {
    public ErrorController(ErrorAttributes errorAttributes, ErrorProperties errorProperties) {
        super(errorAttributes, errorProperties);
    }

    public ErrorController(ErrorAttributes errorAttributes, ErrorProperties errorProperties, List<ErrorViewResolver> errorViewResolvers) {
        super(errorAttributes, errorProperties, errorViewResolvers);
    }

//    @RequestMapping(path = "/error")
//    public Object handle() {
//        Map<String, Object> map = new HashMap<>(4);
//        map.put("time", new Date());
//        map.put("error", "Oh my god~");
//        return map;
//    }
}
