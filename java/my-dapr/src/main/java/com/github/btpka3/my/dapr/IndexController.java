package com.github.btpka3.my.dapr;

import com.github.btpka3.my.dapr.utils.LogUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

/**
 *
 * @author dangqian.zll
 * @date 2025/12/4
 */
@RestController
public class IndexController {

    @PostMapping(path = "/my/")
    public Mono<String> index(
    ) {
        return Mono.fromSupplier(() -> {
            try {
                return "hello world. " + System.currentTimeMillis() + ", " + LogUtils.getHostIpV4();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }
}
