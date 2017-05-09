//package me.test.first.spring.cloud.config
//
//import org.springframework.boot.SpringApplication
//import org.springframework.boot.autoconfigure.EnableAutoConfiguration
//import org.springframework.boot.autoconfigure.SpringBootApplication
//import org.springframework.boot.autoconfigure.web.ErrorController
//import org.springframework.cloud.client.discovery.EnableDiscoveryClient
//import org.springframework.cloud.config.server.EnableConfigServer
//import org.springframework.context.annotation.Configuration
//import org.springframework.web.bind.annotation.RequestMapping
//import org.springframework.web.bind.annotation.RestController
//
//@Configuration
//@EnableAutoConfiguration
////@EnableDiscoveryClient
//@EnableConfigServer
//
//@SpringBootApplication
//@RestController
//class DemoConfigServer implements ErrorController{
//
////
////    @Value("${config.name}")
////    String name = "World";
////
//
//    @RequestMapping("/hi")
//    public String hi() {
//        return "hi " + new Date();
//    }
//
//    // 当你没有实例化一个实现了 ErrorController 的 bean 时,
//    // spring-boot 会自动注册了一个 BasicErrorController
//    // 参考: org.springframework.boot.autoconfigure.web.ErrorMvcAutoConfiguration
//    @RequestMapping("/error")
//    public String error() {
//        return "Hello " + new Date();
//    }
////
//    static void main(String[] args) {
//        SpringApplication.run DemoConfigServer, args
//    }
//
//
//
//}
