

spring-cloud-starter-config
    spring-cloud-starter 
    spring-boot-starter
    spring-cloud-context
        spring-security-crypto
    spring-cloud-commons
        spring-security-crypto
    spring-security-rsa


spring-cloud-config-server
    spring-cloud-config-client
    spring-boot-starter-actuator
        spring-boot-starter
        spring-boot-actuator
    spring-boot-starter-web
    spring-security-crypto
    spring-security-rsa
    org.eclipse.jgit
    snakeyaml
    
spring-cloud-config-monitor
    spring-cloud-config-server
    spring-cloud-bus
    
    
# 端口分配

|module             |port|
|-------------------|-----------|
|sc-eureka-server   |8080       |
|sc-eureka-sp       |9090,9091  |
|sc-eureka-sc       |10000      |
|sc-config-server   |10010      |
|sc-config-client   |10020      |
|sc-zuul            |10030      |
|sc-hystrix         |10040      |
|sc-eureka-sp2      |10050,10051|