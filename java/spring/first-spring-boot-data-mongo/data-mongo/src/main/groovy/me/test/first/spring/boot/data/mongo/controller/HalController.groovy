package me.test.first.spring.boot.data.mongo.controller

import groovy.transform.CompileStatic
import org.springframework.hateoas.Link
import org.springframework.hateoas.ResourceSupport
import org.springframework.web.bind.annotation.*

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn

/**
 * 试用 MongoTemplate
 */
@RequestMapping("/hal")
@RestController
@CompileStatic
class HalController {


    public static class PersonResource extends ResourceSupport {
        String firstname;
        String lastname;
    }

    // 通过 mongoTemplate 查询
    @RequestMapping(path = "/list", method = [RequestMethod.GET])
    @ResponseBody
    PersonResource list(
            @RequestParam(value = "name", required = false, defaultValue = "World")
                    String name
    ) {

        PersonResource resource = new PersonResource();
        resource.firstname = "Dave";
        resource.lastname = "Matthews";
        resource.add(new Link("http://myhost/people"));
        resource.add(linkTo(((HalController) methodOn(HalController.class)).list(name)).withSelfRel());

        return resource
    }

}
