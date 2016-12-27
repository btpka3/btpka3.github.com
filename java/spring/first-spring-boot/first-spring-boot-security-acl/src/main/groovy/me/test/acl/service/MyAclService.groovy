package me.test.acl.service

import me.test.acl.domain.Area
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.stereotype.Service

/**
 *
 */
@Service
class MyAclService {


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

    Area testProgram(Area area) {
        return area
    }

}
