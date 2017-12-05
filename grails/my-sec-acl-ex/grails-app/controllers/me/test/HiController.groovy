package me.test

import grails.converters.JSON
import grails.plugin.springsecurity.SpringSecurityService
import grails.plugin.springsecurity.acl.AclUtilService
import grails.plugin.springsecurity.annotation.Secured
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.acls.model.AclCache
import org.springframework.security.core.Authentication
import org.springframework.util.Assert
import xyz.kingsilk.qh.core.code.QhPermission

class HiController {

    AclCache aclCache
    MyService myService
    SpringSecurityService springSecurityService
    AclUtilService aclUtilService

    def index() {
        render "OK~~~" + new Date() + " : " + springSecurityService.authentication
    }

    @Secured(["hasAnyRole('USER')"])
    def user() {
        render "user only page"
    }

    @Secured(["hasAnyRole('ADMIN')"])
    def admin() {
        render "admin only page"
    }

    @Secured(["isAuthenticated()"])
    def user1() {
        render "user1 : " + myService.user()
    }

    @Secured(["isAuthenticated()"])
    def admin1() {
        render "admin1 : " + myService.admin()
    }

    // --------------------------- ACL
    @Secured(["isAuthenticated()"])
    def li4() {
        User curUser = springSecurityService.currentUser
        Assert.isTrue(curUser.username == "li4", "Please login as 'li4'")

        aclCache.clearCache()

        Authentication curAuth = springSecurityService.authentication

        Org orgZh = Org.findByName("浙江")
        Org orgHz = Org.findByName("杭州")
        Org orgHn = Org.findByName("河南")
        Org orgZz = Org.findByName("郑州")
        Org orgSd = Org.findByName("山东")
        Org orgWh = Org.findByName("威海")

        // 浙江
        Assert.isTrue(aclUtilService.hasPermission(curAuth, orgZh, QhPermission.READ))
        Assert.isTrue(aclUtilService.hasPermission(curAuth, orgZh, QhPermission.ADMINISTRATION))
        Assert.isTrue(!aclUtilService.hasPermission(curAuth, orgZh, QhPermission.WRITE))

        // 杭州 (因为继承，所以有与 '浙江' 相同的权限）
        Assert.isTrue(aclUtilService.hasPermission(curAuth, orgHz, QhPermission.READ))
        Assert.isTrue(aclUtilService.hasPermission(curAuth, orgHz, QhPermission.ADMINISTRATION))
        Assert.isTrue(!aclUtilService.hasPermission(curAuth, orgHz, QhPermission.WRITE))

        // 河南
        Assert.isTrue(aclUtilService.hasPermission(curAuth, orgHn, QhPermission.READ))
        Assert.isTrue(aclUtilService.hasPermission(curAuth, orgHn, QhPermission.ADMINISTRATION))
        Assert.isTrue(!aclUtilService.hasPermission(curAuth, orgHn, QhPermission.WRITE))

        // 郑州 (因为没有继承，又没有任何明确赋予权限，所以没有任何权限）
        Assert.isTrue(!aclUtilService.hasPermission(curAuth, orgZz, QhPermission.READ))
        Assert.isTrue(!aclUtilService.hasPermission(curAuth, orgZz, QhPermission.ADMINISTRATION))
        Assert.isTrue(!aclUtilService.hasPermission(curAuth, orgZz, QhPermission.WRITE))

        // 山东
        Assert.isTrue(aclUtilService.hasPermission(curAuth, orgSd, QhPermission.READ))
        Assert.isTrue(aclUtilService.hasPermission(curAuth, orgSd, QhPermission.ADMINISTRATION))
        Assert.isTrue(!aclUtilService.hasPermission(curAuth, orgSd, QhPermission.WRITE))

        // 威海 (数据库中 'AclObjectIdentity', 'AclEntry' 中应当就没有相应的记录）
        // AclService 会抛出 NotFoundException， AclPermissionEvaluator 会捕获该异常并返回 false。
        Assert.isTrue(!aclUtilService.hasPermission(curAuth, orgWh, QhPermission.READ))
        Assert.isTrue(!aclUtilService.hasPermission(curAuth, orgWh, QhPermission.ADMINISTRATION))
        Assert.isTrue(!aclUtilService.hasPermission(curAuth, orgWh, QhPermission.WRITE))

        // 测试注解
        Assert.isTrue(myService.testRead(orgHz))
        try {
            myService.testRead(orgZz)
            Assert.isTrue(false) // 不应该走到该步骤的。
        } catch (AccessDeniedException e) {
            Assert.notNull(e)
        }

        render "li4 only page"
    }

    @Secured(["isAuthenticated()"])
    def postFilters() {

        List<Org> orgList = myService.orgs()
        render(orgList*.name as JSON)
    }

}
