package me.test

import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class JsonController {

    @RequestMapping("/")
    public String index() {
        return "<ul>" +
                "<li><a href='json'/>json</a></li>" +
                "<li><a href='#todo'/>gsp 表单提交示例 TODO</a></li>" +
                "<li><a href='gsp'/>gsp</a></li>" +
                "</ul>";
    }

    @RequestMapping("/json")
    Object json() {
        // 没办法自动重新编译，重新加载
        [a: "aab", b: 1, c: true]
    }
}
