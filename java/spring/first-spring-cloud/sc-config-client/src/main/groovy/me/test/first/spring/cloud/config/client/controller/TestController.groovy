package me.test.first.spring.cloud.config.client.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationContext
import org.springframework.core.env.Environment
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*

/**
 *
 */
@RestController()
@RequestMapping("/test")
class TestController {

    @Autowired
    Environment env
    ApplicationContext appCtx;

    @RequestMapping(path = "/hi",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    Object hi() {

        Map<String, Object> map = new HashMap();
//        for(Iterator it = ((AbstractEnvironment) env).getPropertySources().iterator(); it.hasNext(); ) {
//            PropertySource ps = (PropertySource) it.next();
//
//
//
//            if (ps instanceof MapPropertySource) {
//                map.putAll(((MapPropertySource) ps).getSource());
//            }
//        }

        String k = "path._application"
        String v = env.getProperty(k)
        map.put(k, v);

        k = "path._aaa_sc-config-client_application"
        v = env.getProperty(k)
        map.put(k, v);

        k = "password"
        v = env.getProperty(k)
        map.put(k, v);

        k = "foo"
        v = env.getProperty(k)
        map.put(k, v);

        k = "server.port"
        v = env.getProperty(k)
        map.put(k, v);

        return [
                env : map,
                a   : "aaa",
                date: new Date()
        ]
    }
}
