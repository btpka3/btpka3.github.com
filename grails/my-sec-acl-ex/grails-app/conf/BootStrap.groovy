import grails.plugin.springsecurity.SpringSecurityService
import grails.plugin.springsecurity.SpringSecurityUtils
import grails.plugin.springsecurity.acl.*
import grails.util.Holders
import me.test.*
import org.springframework.security.acls.domain.ObjectIdentityImpl
import org.springframework.security.acls.model.AclCache
import org.springframework.security.acls.model.ObjectIdentity
import xyz.kingsilk.qh.core.code.QhPermission

class BootStrap {

    def init = { servletContext ->

        // ===================================================== User
        // 用户: 张三，李四
        User zhang3, li4

        log.info "-------------------------- init db : start"
        User.withTransaction {

            // 清空数据库
            UserRole.all*.delete(flush: true)
            Role.all*.delete(flush: true)
            User.all*.delete(flush: true)

            // 创建权限
            Role roleUser = new Role("USER")
            Role roleAdmin = new Role("ADMIN")
            [roleUser, roleAdmin]*.save(flush: true)

            // 创建人员
            zhang3 = new User("zhang3", "zhang3")
            li4 = new User("li4", "li4")
            [zhang3, li4]*.save(flush: true)

            // 将人员和权限关联
            [
                    new UserRole(zhang3, roleUser),
                    new UserRole(zhang3, roleAdmin),
                    new UserRole(li4, roleUser),
            ]*.save(flush: true)
        }

        // ===================================================== Org

        // 组织: 浙江、杭州、河南、郑州、山东、威海
        Org orgZj, orgHz, orgHn, orgZz, orgSd, orgWh

        Org.withTransaction {

            Org.all*.delete(fulsh: true)

            orgZj = new Org(name: "浙江")
            orgHz = new Org(name: "杭州")
            orgHn = new Org(name: "河南")
            orgZz = new Org(name: "郑州")
            orgSd = new Org(name: "山东")
            orgWh = new Org(name: "威海")
            [orgZj, orgHz, orgHn, orgZz, orgSd, orgWh]*.save(flush: true)

        }

        // ===================================================== ACL

        SpringSecurityService springSecurityService = Holders.applicationContext.getBean(SpringSecurityService)
        AclUtilService aclUtilService = Holders.applicationContext.getBean(AclUtilService)
        AclService aclService = Holders.applicationContext.getBean(AclService)
        AclCache aclCache = Holders.applicationContext.getBean(AclCache)

//        AclEntry.withTransaction {
//            new AclSid(sid: "aaa", principal: false).save(flush: true)
//        }

        AclEntry.withTransaction {

            AclEntry.all*.delete(flush: true)
            AclObjectIdentity.all*.delete(flush: true)
            AclClass.all*.delete(flush: true)
            AclSid.all*.delete(flush: true)

            // 注意：Acl 的相关操作会从当前环境中获取 当前登录的用户
            SpringSecurityUtils.doWithAuth("zhang3") {

                // 注意：第二个参数如果前缀是 "ROLE_" 则会创建 GrantedAuthoritySid， 否则创建 PrincipalSid
                // 如果想明确使用，请自行 new， 比如:
                //   new PrincipalSid(xxxUser.id.toString())
                //   new GrantedAuthoritySid(StaffAuthorityEnum.xxx.getName())
                AclEntry.withNewSession {
                    aclService.createAcl(new ObjectIdentityImpl(orgZj))
                }

                aclUtilService.addPermission(orgZj, "li4", QhPermission.ADMINISTRATION)
                aclUtilService.addPermission(orgZj, "li4", QhPermission.READ)

                aclService.createAcl(new ObjectIdentityImpl(orgHz))

                aclService.createAcl(new ObjectIdentityImpl(orgHn))
                aclUtilService.addPermission(orgHn, "li4", QhPermission.ADMINISTRATION)
                aclUtilService.addPermission(orgHn, "li4", QhPermission.READ)

                aclService.createAcl(new ObjectIdentityImpl(orgZz))

                aclService.createAcl(new ObjectIdentityImpl(orgSd))
                aclUtilService.addPermission(orgSd, "li4", QhPermission.ADMINISTRATION)
                aclUtilService.addPermission(orgSd, "li4", QhPermission.READ)
                //aclService.createAcl(new ObjectIdentityImpl(orgWh))


            }

        }
        aclCache.clearCache()

        AclEntry.withTransaction {

            // 更新 "杭州" 的 parent ，并启用 继承
            AclObjectIdentity aoi = findAoi(orgHz)
            aoi.parent = findAoi(orgZj)
            aoi.entriesInheriting = true // 默认值
            aoi.save()
        }

        AclEntry.withTransaction {

            // 更新 "郑州" 的 parent ，并禁用 继承
            AclObjectIdentity aoi = findAoi(orgZz)
            aoi.parent = findAoi(orgHn)
            aoi.entriesInheriting = false
            aoi.save()
        }

        log.info "-------------------------- init db : end"
    }
    def destroy = {
    }

    AclObjectIdentity findAoi(Object obj) {
        ObjectIdentity oi = new ObjectIdentityImpl(obj)
        AclClass aclClazz = AclClass.findByClassName(Org.getName())
        //AclClass aclClazz = new AclClass(className: obj.getClass().name)

        AclObjectIdentity aoi = AclObjectIdentity.where {
            aclClass == aclClazz && objectId == oi.identifier
        }[0]

        return aoi
    }
}
