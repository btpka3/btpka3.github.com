package me.test.acl.controller

import me.test.acl.dao.CityDao
import me.test.acl.domain.Area
import me.test.acl.service.MyAclService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.access.prepost.PreAuthorize
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


    Area areaZj
    Area areaHz

    Area areaHn
    Area areaZz

    Area areaSd
    Area areaWh

    Sid sidZhang3 = new PrincipalSid("zhang3")
    Sid sidLi4 = new PrincipalSid("li4")

    @RequestMapping("/acl/zhang3")
    @PreAuthorize("isAuthenticated()")
    @ResponseBody
    @Transactional
    String zhang3(Authentication auth) {

        Assert.isTrue(auth.getPrincipal().username == "zhang3")

        initArea()

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

        Assert.isTrue(auth.getPrincipal().username == "li4")

        initArea()

        Assert.isTrue(canRead(areaZj))
        Assert.isTrue(canAdmin(areaZj))
        Assert.isTrue(canAdminAndRead(areaZj))
        Assert.isTrue(!canWrite(areaZj))

        Assert.isTrue(canRead(areaHz))
        Assert.isTrue(canAdmin(areaHz))
        Assert.isTrue(canAdminAndRead(areaHz))
        Assert.isTrue(!canWrite(areaHz))

//        Assert.isTrue(canRead(areaHn))
//        Assert.isTrue(canAdmin(areaHn))
//        Assert.isTrue(canAdminAndRead(areaHn))
//        Assert.isTrue(!canWrite(areaHn))

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

        return "li4 OK " + new Date();
    }

    private void initArea() {
        areaZj = cityDao.findOne(10L)
        areaHz = cityDao.findOne(11L)

        areaHn = cityDao.findOne(20L)
        areaZz = cityDao.findOne(21L)

        areaSd = cityDao.findOne(30L)
        areaWh = cityDao.findOne(31L)
    }


    private boolean canRead(Area area) {
        try {
            myAclService.testRead(area)
            return true
        } catch (Exception e) {
            return false
        }
    }

    private boolean canAdmin(Area area) {
        try {
            myAclService.testAdmin(area)
            return true
        } catch (Exception e) {
            return false
        }
    }

    private boolean canAdminAndRead(Area area) {
        try {
            myAclService.testAdminAndRead(area)
            return true
        } catch (Exception e) {
            return false
        }
    }

    private boolean canWrite(Area area) {
        try {
            myAclService.testWrite(area)
            return true
        } catch (Exception e) {
            return false
        }
    }


}
