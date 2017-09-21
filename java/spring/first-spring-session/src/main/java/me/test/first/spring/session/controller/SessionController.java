package me.test.first.spring.session.controller;

import org.springframework.beans.factory.annotation.*;
import org.springframework.core.env.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.*;
import java.util.*;

/**
 *
 */
@RestController
@RequestMapping("/session")
public class SessionController {

    private static final String ATTR_NAME_STR = "str";
    private static final String ATTR_NAME_MAP = "map";
    private static final String ATTR_NAME_MAP_SAVE = "mapSave";
    private String[] attrs = {
            ATTR_NAME_STR,
            ATTR_NAME_MAP,
            ATTR_NAME_MAP_SAVE
    };


    @Autowired
    Environment env;

    @RequestMapping(
            path = "/exists",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseBody
    public Object exists(HttpServletRequest request) {

        HttpSession session = request.getSession(false);

        Map<String, Object> result = new LinkedHashMap<>();
        result.put("p", env.getProperty("p"));
        result.put("exists", session == null);

        return result;
    }

    @RequestMapping(
            path = "/values",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseBody
    public Object values(HttpServletRequest request) {


        Map<String, Object> result = new LinkedHashMap<>();
        result.put("p", env.getProperty("p"));


        HttpSession session = request.getSession(false);
        if (session != null) {

            List<String> attrNamesInSession = Collections.list(session.getAttributeNames());
            Arrays.stream(attrs).forEach(attr -> {

                Map<String, Object> attrResult = new LinkedHashMap<>();

                attrResult.put("exists", attrNamesInSession.contains(attr));
                attrResult.put("value", session.getAttribute(attr));
                result.put(attr, attrResult);
            });
        }
        return result;
    }

    @RequestMapping(
            path = "/save",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseBody
    public Object save(HttpServletRequest request) {

        HttpSession session = request.getSession(true);


        Map<String, Object> result = new LinkedHashMap<>();
        result.put("p", env.getProperty("p"));

        // 基本数据类型每次都 save
        long time = System.currentTimeMillis();
        session.setAttribute(ATTR_NAME_STR, "s-" + time);


        // 引用数据类型仅仅创建时 save
        Map<String, Object> map = (Map<String, Object>) session.getAttribute(ATTR_NAME_MAP);
        if (map == null) {
            map = new LinkedHashMap<>();
            map.put("x", "x-" + time);
            session.setAttribute(ATTR_NAME_MAP, map);
        } else {
            map.put("x", "x-" + time);
        }

        // 引用数据类型创建/每次更新后，都 save
        Map<String, Object> mapSave = (Map<String, Object>) session.getAttribute(ATTR_NAME_MAP_SAVE);
        if (mapSave == null) {
            mapSave = new LinkedHashMap<>();
        }
        mapSave.put("y", "y-" + time);
        session.setAttribute(ATTR_NAME_MAP_SAVE, mapSave);

        return Arrays.asList("save : " + new Date());
    }


    @RequestMapping(
            path = "/destroy",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseBody
    public Object destroy(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return Arrays.asList("destroy : " + new Date());
    }


}
