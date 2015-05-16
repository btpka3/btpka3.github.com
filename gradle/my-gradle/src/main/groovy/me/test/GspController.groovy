package me.test

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.servlet.ModelAndView
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter

@Controller
class GspController extends WebMvcConfigurerAdapter {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/results").setViewName("results");
    }

    @RequestMapping(value = "/gsp", method = RequestMethod.GET)
    public ModelAndView gsp() {
        
        return new ModelAndView("results", [
                value: "hello~",
                list : [111, 222, 333],
                map  : [
                        a: "aaa",
                        b: "bbb",
                        c: "ccc"
                ]
        ]);
    }
}
