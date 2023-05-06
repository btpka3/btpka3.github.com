package com.github.btpka3.firstbootgraalvm;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

/**
 * @author dangqian.zll
 * @date 2023/3/2
 */
@RestController
@RequestMapping("/test")
public class MyAddController {

    @GetMapping("/add")
    public Mono<Long> add(
            @RequestParam(name = "a") Long a,
            @RequestParam(name = "b") Long b
    ) {
        return Mono.just(a + b);
    }
}
