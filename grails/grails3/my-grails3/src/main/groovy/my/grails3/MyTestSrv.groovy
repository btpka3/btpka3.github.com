package my.grails3

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

import javax.annotation.PostConstruct

@Service
class MyTestSrv {

    public MyTestSrv() {
        println "================----------------- MyTestSrv constructor"
    }

    @Autowired
    private UserDetailsService myUserDetails

    @PostConstruct
    void logInfo(){
        println "================----------------- MyTestSrv logInfo : myUserDetails " + myUserDetails
    }


}
