package me.test.acl.service

import me.test.acl.dao.CityDao
import me.test.acl.domain.Area
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.context.ApplicationListener
import org.springframework.security.acls.domain.BasePermission
import org.springframework.security.acls.domain.ObjectIdentityImpl
import org.springframework.security.acls.domain.PrincipalSid
import org.springframework.security.acls.model.MutableAcl
import org.springframework.security.acls.model.MutableAclService
import org.springframework.security.acls.model.ObjectIdentity
import org.springframework.security.acls.model.Sid
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service

import javax.transaction.Transactional

/**
 * 启动后自动初始化数据库
 */
@Service
class InitService implements
        ApplicationListener<ApplicationReadyEvent> {
/*
ApplicationPreparedEvent
ApplicationStartedEvent
ApplicationReadyEvent
 */

    Logger log = LoggerFactory.getLogger(InitService)

    @Autowired
    CityDao cityDao

    @Autowired
    MutableAclService aclService

    @Transactional
    @Override
    void onApplicationEvent(ApplicationReadyEvent event) {

        log.info("=========================== " + event)

        aclZj = initZj()
        aclHz = initHz()

        aclHz = initHn()
        aclZz = initZz()

        aclSd = initSd()
        aclWh = initWh()

    }

    private Sid sidZhang3 = new PrincipalSid("zhang3")
    private Sid sidLi4 = new PrincipalSid("li4")

    private Authentication authAdmin = new UsernamePasswordAuthenticationToken("admin", "", []);
    private Authentication authZhang3 = new UsernamePasswordAuthenticationToken("zhang3", "", []);

    private MutableAcl aclZj
    private MutableAcl aclHz
    private MutableAcl aclHn
    private MutableAcl aclZz
    private MutableAcl aclSd
    private MutableAcl aclWh

    private MutableAcl initZj() {
        Area area = new Area()
        area.id = 10
        area.name = '浙江'
        cityDao.save(area)

        SecurityContextHolder.getContext().setAuthentication(authZhang3)
        ObjectIdentity oi = new ObjectIdentityImpl(Area, area.id)
        MutableAcl acl = aclService.createAcl(oi)
        acl.setOwner(sidZhang3)
        acl.setEntriesInheriting(false)
        acl.setParent(null)

        acl.insertAce(
                acl.getEntries().size(),
                BasePermission.ADMINISTRATION,
                sidLi4,
                true
        )
        acl.insertAce(
                acl.getEntries().size(),
                BasePermission.READ,
                sidLi4,
                true
        )
        aclService.updateAcl(acl)
        return acl
    }

    private MutableAcl initHz() {
        Area area = new Area()
        area.id = 11
        area.name = '杭州'
        cityDao.save(area)

        SecurityContextHolder.getContext().setAuthentication(authAdmin)
        ObjectIdentity oi = new ObjectIdentityImpl(Area, area.id)
        MutableAcl acl = aclService.createAcl(oi)
        acl.setEntriesInheriting(true)
        acl.setParent(aclZj)

        aclService.updateAcl(acl)
        return acl
    }

    private MutableAcl initHn() {
        Area area = new Area()
        area.id = 20
        area.name = '河南'
        cityDao.save(area)

        SecurityContextHolder.getContext().setAuthentication(authZhang3)
        ObjectIdentity oi = new ObjectIdentityImpl(Area, area.id)
        MutableAcl acl = aclService.createAcl(oi)
        acl.setOwner(sidZhang3)
        acl.setEntriesInheriting(false)
        acl.setParent(null)

        acl.insertAce(
                acl.getEntries().size(),
                new BasePermission(
                        BasePermission.READ.mask | BasePermission.ADMINISTRATION.mask,
                        'X' as char),
                sidLi4,
                true
        )
        aclService.updateAcl(acl)
        return acl
    }

    private MutableAcl initZz() {
        Area area = new Area()
        area.id = 21
        area.name = '郑州'
        cityDao.save(area)

        SecurityContextHolder.getContext().setAuthentication(authAdmin)
        ObjectIdentity oi = new ObjectIdentityImpl(Area, area.id)
        MutableAcl acl = aclService.createAcl(oi)
        acl.setEntriesInheriting(false)
        acl.setParent(aclHn)

        aclService.updateAcl(acl)
        return acl
    }


    private MutableAcl initSd() {
        Area area = new Area()
        area.id = 30
        area.name = '山东'
        cityDao.save(area)

        SecurityContextHolder.getContext().setAuthentication(authAdmin)
        ObjectIdentity oi = new ObjectIdentityImpl(Area, area.id)
        MutableAcl acl = aclService.createAcl(oi)
        acl.setEntriesInheriting(false)

        aclService.updateAcl(acl)
        return acl
    }


    private MutableAcl initWh() {
        Area area = new Area()
        area.id = 31
        area.name = '威海'
        cityDao.save(area)

        // 不进行任何配置
        return null
    }

}
