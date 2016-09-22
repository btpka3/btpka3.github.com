// FIXME 这里已经不能再 import 了
//import com.h2.Driver
//import org.springframework.security.authentication.LockedException
//import org.springframework.security.authentication.DisabledException
//import org.springframework.security.authentication.AccountExpiredException
//import org.springframework.security.authentication.CredentialsExpiredException


grails.plugin.springsecurity.userLookup.userDomainClassName = 'my.grails3.MyUser'
grails.plugin.springsecurity.userLookup.authorityJoinClassName = null // 'com.yourapp.UserRole'
grails.plugin.springsecurity.authority.className = null // 'com.yourapp.Role'
grails.plugin.springsecurity.authority.nameField = null // "authority"
grails.plugin.springsecurity.useRoleGroups = false
grails.plugin.springsecurity.authority.groupAuthorityNameField = null // 'authorities'
grails.plugin.springsecurity.controllerAnnotations.staticRules = [
        [pattern: '/', access: ['permitAll']],
        [pattern: '/error', access: ['permitAll']],
        [pattern: '/index', access: ['permitAll']],
        [pattern: '/index.gsp', access: ['permitAll']],
        [pattern: '/shutdown', access: ['permitAll']],
        [pattern: '/assets/**', access: ['permitAll']],
        [pattern: '/**/js/**', access: ['permitAll']],
        [pattern: '/**/css/**', access: ['permitAll']],
        [pattern: '/**/images/**', access: ['permitAll']],
        [pattern: '/**/favicon.ico', access: ['permitAll']]
]

grails.plugin.springsecurity.filterChain.chainMap = [
        [pattern: '/assets/**', filters: 'none'],
        [pattern: '/**/js/**', filters: 'none'],
        [pattern: '/**/css/**', filters: 'none'],
        [pattern: '/**/images/**', filters: 'none'],
        [pattern: '/**/favicon.ico', filters: 'none'],
        [pattern: '/**', filters: 'JOINED_FILTERS']
]

grails.plugin.springsecurity.rememberMe.persistent = true
grails.plugin.springsecurity.rememberMe.persistentToken.domainClassName = 'my.grails3.MyPersistentLogin'

grails.plugin.springsecurity.roleHierarchyEntryClassName = 'my.grails3.MyRoleHierarchyEntry'

grails.plugin.springsecurity.logout.postOnly = false
grails.plugin.springsecurity.password.algorithm = 'bcrypt'
grails.plugin.springsecurity.password.encodeHashAsBase64 = true
grails.plugin.springsecurity.bcrypt.logrounds = 7
grails.plugin.springsecurity.rejectIfNoRule = false
grails.plugin.springsecurity.fii.rejectPublicInvocations = false
grails.plugin.springsecurity.securityConfigType = "Annotation"
grails.plugin.springsecurity.successHandler.defaultTargetUrl = "/"
grails.plugin.springsecurity.providerNames = [
        'daoAuthenticationProvider',
        'anonymousAuthenticationProvider',
        'rememberMeAuthenticationProvider'
]
grails.plugin.springsecurity.secureChannel.useHeaderCheckChannelSecurity = true
grails.plugin.springsecurity.useSecurityEventListener = true
grails.plugin.springsecurity.useSessionFixationPrevention = true
grails.plugin.springsecurity.failureHandler.exceptionMappings = [
        [exception: 'org.springframework.security.authentication.LockedException', url: '/user/accountLocked'],
        [exception: 'org.springframework.security.authentication.DisabledException', url: '/user/accountDisabled'],
        [exception: 'org.springframework.security.authentication.AccountExpiredException', url: '/user/accountExpired'],
        [exception: 'org.springframework.security.authentication.CredentialsExpiredException', url: '/user/passwordExpired']
]

//grails.plugin.springsecurity.failureHandler.exceptionMappings = [
//        [exception: LockedException.name,             url: '/user/accountLocked'],
//        [exception: DisabledException.name,           url: '/user/accountDisabled'],
//        [exception: AccountExpiredException.name,     url: '/user/accountExpired'],
//        [exception: CredentialsExpiredException.name, url: '/user/passwordExpired']
//]





