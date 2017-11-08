package io.github.btpka3.netty.socks5.controller;

import org.springframework.security.access.prepost.*;
import org.springframework.security.core.annotation.*;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class HomeController {

    @RequestMapping(path = "/", method = RequestMethod.GET)
    @ResponseBody
    @PreAuthorize("permitAll")
    public String index(@AuthenticationPrincipal Object curUser) {
        return "my-netty-socks5 :: Hello World!~ " + curUser + " @ " + new Date();
    }

}
