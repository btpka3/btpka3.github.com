package me.test.first.spring.cloud.eureka.sp.conf

import org.springframework.cloud.netflix.eureka.EnableEurekaClient
import org.springframework.context.annotation.Configuration

/**
 *
 */
@Configuration
//@EnableEurekaClient // FIXME : 该注解发现只有在 ScEurekaSpApp 上才有效。
class EurekaClientConf {

}