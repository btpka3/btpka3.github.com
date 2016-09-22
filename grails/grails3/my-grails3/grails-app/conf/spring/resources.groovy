import my.grails3.MyUserDetails
import org.springframework.security.web.access.expression.DefaultWebSecurityExpressionHandler

beans = {

//    userDetailsService(MyUserDetails){
//        println "-------------------------- this worked in resources.groovy"
//    }

    webExpressionHandler(DefaultWebSecurityExpressionHandler){
        defaultRolePrefix = ""
    }
}
