package me.test.first.spring.boot.cxf.acl.controller

import me.test.first.spring.boot.cxf.acl.dao.CityDao
import me.test.first.spring.boot.cxf.acl.domain.Area
import me.test.first.spring.boot.cxf.acl.service.MyAclService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.acls.domain.BasePermission
import org.springframework.security.acls.domain.PrincipalSid
import org.springframework.security.acls.model.Sid
import org.springframework.security.core.Authentication
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.stereotype.Controller
import org.springframework.util.Assert
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody

import javax.transaction.Transactional

@Controller
class MyAclController {

    Logger log = LoggerFactory.getLogger(MyAclController)

    @RequestMapping("/")
    @PreAuthorize("permitAll")
    @ResponseBody
    String index(Authentication auth) {
        return "Hello World!~ " + new Date() + " : " + auth;
    }


    @RequestMapping("/controller/pub")
    @ResponseBody
    String pub(@AuthenticationPrincipal Object curUser) {
        return "/controller/pub : " + curUser;
    }

    @RequestMapping("/controller/sec")
    @PreAuthorize("isAuthenticated()")
    @ResponseBody
    String sec() {
        return "/controller/sec";
    }

    // -------------------------------------- 测试 ACL
    @Autowired
    MyAclService myAclService

    @Autowired
    CityDao cityDao


    Sid sidZhang3 = new PrincipalSid("zhang3")
    Sid sidLi4 = new PrincipalSid("li4")

    @RequestMapping("/acl/zhang3")
    @PreAuthorize("isAuthenticated()")
    @ResponseBody
    @Transactional
    String zhang3(Authentication auth) {

        Assert.isTrue(auth.getPrincipal().username == "zhang3", "please login as 'zhang3'")

        Area areaZj = cityDao.findOne(10L)
        Area areaHz = cityDao.findOne(11L)
        Area areaHn = cityDao.findOne(20L)
        Area areaZz = cityDao.findOne(21L)
        Area areaSd = cityDao.findOne(30L)
        Area areaWh = cityDao.findOne(31L)

        // 因为 zhang3 只是 owner，未明确授权，所以没有相关权限。
        Assert.isTrue(canRead(areaZj))
        Assert.isTrue(canAdmin(areaZj))
        Assert.isTrue(canAdminAndRead(areaZj))
        Assert.isTrue(canWrite(areaZj))

        Assert.isTrue(canRead(areaHz))
        Assert.isTrue(canAdmin(areaHz))
        Assert.isTrue(canAdminAndRead(areaHz))
        Assert.isTrue(canWrite(areaHz))

        Assert.isTrue(canRead(areaHn))
        Assert.isTrue(canAdmin(areaHn))
        Assert.isTrue(canAdminAndRead(areaHn))
        Assert.isTrue(canWrite(areaHn))

        Assert.isTrue(!canRead(areaZz))
        Assert.isTrue(!canAdmin(areaZz))
        Assert.isTrue(!canAdminAndRead(areaZz))
        Assert.isTrue(!canWrite(areaZz))

        Assert.isTrue(!canRead(areaSd))
        Assert.isTrue(!canAdmin(areaSd))
        Assert.isTrue(!canAdminAndRead(areaSd))
        Assert.isTrue(!canWrite(areaSd))

        Assert.isTrue(!canRead(areaWh))
        Assert.isTrue(!canAdmin(areaWh))
        Assert.isTrue(!canAdminAndRead(areaWh))
        Assert.isTrue(!canWrite(areaWh))

        return "zhang3 OK " + new Date();
    }

    @RequestMapping("/acl/li4")
    @PreAuthorize("isAuthenticated()")
    @ResponseBody
    @Transactional
    String li4(Authentication auth) {

        Assert.isTrue(auth.getPrincipal().username == "li4", "please login as 'li4'")

        Area areaZj = cityDao.findOne(10L)
        Area areaHz = cityDao.findOne(11L)
        Area areaHn = cityDao.findOne(20L)
        Area areaZz = cityDao.findOne(21L)
        Area areaSd = cityDao.findOne(30L)
        Area areaWh = cityDao.findOne(31L)

        Assert.isTrue(canRead(areaZj))
        Assert.isTrue(canAdmin(areaZj))
        Assert.isTrue(canAdminAndRead(areaZj))
        Assert.isTrue(!canWrite(areaZj))

        Assert.isTrue(canRead(areaHz))
        Assert.isTrue(canAdmin(areaHz))
        Assert.isTrue(canAdminAndRead(areaHz))
        Assert.isTrue(!canWrite(areaHz))  // 未明确授权

        Assert.isTrue(!canRead(areaHn))     // bit mask 合并在一起，并不认为是两个权限，而是一个新的权限
        Assert.isTrue(!canAdmin(areaHn))
        Assert.isTrue(!canAdminAndRead(areaHn))
        Assert.isTrue(!canWrite(areaHn))

        Assert.isTrue(!canRead(areaZz))
        Assert.isTrue(!canAdmin(areaZz))
        Assert.isTrue(!canAdminAndRead(areaZz))
        Assert.isTrue(!canWrite(areaZz))

        Assert.isTrue(!canRead(areaSd))
        Assert.isTrue(!canAdmin(areaSd))
        Assert.isTrue(!canAdminAndRead(areaSd))
        Assert.isTrue(!canWrite(areaSd))

        Assert.isTrue(!canRead(areaWh))
        Assert.isTrue(!canAdmin(areaWh))
        Assert.isTrue(!canAdminAndRead(areaWh))
        Assert.isTrue(!canWrite(areaWh))

        //------
        Assert.isTrue(myAclService.testProgram(areaZj, [BasePermission.READ]))
        Assert.isTrue(!myAclService.testProgram(areaWh, [BasePermission.READ]))


        return "li4 OK " + new Date();
    }


    private boolean canRead(Area area) {
        try {
            myAclService.testRead(area)
            return true
        } catch (Exception e) {
            log.debug(e.message, e)
            return false
        }
    }

    private boolean canAdmin(Area area) {
        try {
            myAclService.testAdmin(area)
            return true
        } catch (Exception e) {
            log.debug(e.message, e)
            return false
        }
    }

    private boolean canAdminAndRead(Area area) {
        try {
            myAclService.testAdminAndRead(area)
            return true
        } catch (Exception e) {
            log.debug(e.message, e)
            return false
        }
    }

    private boolean canWrite(Area area) {
        try {
            myAclService.testWrite(area)
            return true
        } catch (Exception e) {
            log.debug(e.message, e)
            return false
        }
    }


}
