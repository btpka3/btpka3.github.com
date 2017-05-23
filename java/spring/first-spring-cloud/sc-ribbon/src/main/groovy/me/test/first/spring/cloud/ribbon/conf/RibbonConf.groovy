package me.test.first.spring.cloud.ribbon.conf

import org.springframework.cloud.netflix.ribbon.RibbonClient
import org.springframework.context.annotation.Configuration

/**
 *
 */
@Configuration
@RibbonClient(name = "sc-ribbon")
class RibbonConf {

}