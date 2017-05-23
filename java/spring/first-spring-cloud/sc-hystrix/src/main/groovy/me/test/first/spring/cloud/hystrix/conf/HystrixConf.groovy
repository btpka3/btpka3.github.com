package me.test.first.spring.cloud.hystrix.conf

import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard
import org.springframework.cloud.netflix.turbine.EnableTurbine
import org.springframework.context.annotation.Configuration

/**
 *
 */
@Configuration
@EnableCircuitBreaker
@EnableHystrixDashboard
@EnableTurbine
class HystrixConf {


}
