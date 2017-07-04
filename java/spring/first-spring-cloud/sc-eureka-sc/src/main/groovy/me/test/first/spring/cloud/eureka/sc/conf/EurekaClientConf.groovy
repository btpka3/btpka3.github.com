package me.test.first.spring.cloud.eureka.sc.conf

import org.springframework.cloud.client.discovery.EnableDiscoveryClient
import org.springframework.context.annotation.Configuration

/**
 *
 */
@Configuration
//@EnableEurekaClient
//@EnableDiscoveryClient // FIXME : 该注解发现只有在 ScEurekaSpApp 上才有效。
class EurekaClientConf {

}