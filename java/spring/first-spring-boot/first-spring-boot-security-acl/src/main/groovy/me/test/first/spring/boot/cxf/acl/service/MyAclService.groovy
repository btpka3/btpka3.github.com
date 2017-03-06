package me.test.first.spring.boot.cxf.acl.service

import me.test.first.spring.boot.cxf.acl.domain.Area
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.acls.domain.ObjectIdentityImpl
import org.springframework.security.acls.domain.PrincipalSid
import org.springframework.security.acls.model.*
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service

/**
 *
 */
@Service
class MyAclService {

    Logger log = LoggerFactory.getLogger(MyAclService)

    @PreAuthorize('hasPermission(#area, "ADMINISTRATION")')
    Area testAdmin(Area area) {
        return area
    }

    @PreAuthorize('hasPermission(#area, "READ")')
    Area testRead(Area area) {
        return area
    }

    @PreAuthorize('hasPermission(#area, "ADMINISTRATION") and hasPermission(#area, "READ")')
    Area testAdminAndRead(Area area) {
        return area
    }

    @PreAuthorize('hasPermission(#area, "WRITE")')
    Area testWrite(Area area) {
        return area
    }

    @Autowired
    AclService aclService

    boolean testProgram(Area area, List<Permission> permissions) {

        Sid sid = new PrincipalSid(SecurityContextHolder.getContext().getAuthentication())
        ObjectIdentity oi = new ObjectIdentityImpl(area);
        try {
            Acl acl = aclService.readAclById(oi)
            return acl.isGranted(permissions, [sid], false)
        } catch (NotFoundException e) {
            log.debug(e.message, e)
        }
    }

}
