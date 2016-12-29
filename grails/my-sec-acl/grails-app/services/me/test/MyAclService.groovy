package me.test

import grails.plugin.springsecurity.acl.AclClass
import grails.plugin.springsecurity.acl.AclObjectIdentity
import grails.plugin.springsecurity.acl.AclService
import grails.plugin.springsecurity.acl.AclSid
import grails.transaction.Transactional
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.context.i18n.LocaleContextHolder
import org.springframework.security.acls.domain.ObjectIdentityImpl
import org.springframework.security.acls.model.ObjectIdentity
import org.springframework.security.acls.model.Sid

@Transactional
class MyAclService extends AclService {


    // 注意：需要明确声明一个 slf4j 的 log，
    // 否则 grails 框架会自动配置一个 org.apache.commons.logging.impl.SLF4JLog
    protected final Logger log = LoggerFactory.getLogger(getClass())

    @Override
    protected AclObjectIdentity retrieveObjectIdentity(ObjectIdentity oid) {


        AclObjectIdentity.withCriteria {
            eq 'objectId', oid.identifier
            eq 'aclClass', AclClass.findByClassName(oid.type)
//            aclClass {
//                eq 'className', oid.type
//            }
            maxResults 1
        }[0]
    }

    @Override
    protected void createObjectIdentity(ObjectIdentity object, Sid owner) {
        AclSid ownerSid = createOrRetrieveSid(owner, true)
        AclClass aclClass = createOrRetrieveClass(object.type, true)

        AclObjectIdentity aoi = new AclObjectIdentity()
        aoi.aclClass = aclClass
        aoi.objectId = object.identifier as String
        aoi.owner = ownerSid
        aoi.entriesInheriting = true

        save aoi
    }

    @Override
    protected save(bean) {
        if (!bean.save(flush: true)) {
            if (log.warnEnabled) {
                def message = new StringBuilder("problem creating ${bean.getClass().simpleName}: $bean")
                def locale = LocaleContextHolder.getLocale()
                for (fieldErrors in bean.errors) {
                    for (error in fieldErrors.allErrors) {
                        message << '\n\t' << messageSource.getMessage(error, locale)
                    }
                }
                log.warn message.toString()
            }
        }

        def c = {
            log.debug("================= ccccccc : " + log.getClass())
        }
        c.run()
        log.debug("================= saved : " + bean + log.getClass())
        bean
    }

    @Override
    List<ObjectIdentity> findChildren(ObjectIdentity parentOid) {


        List<AclObjectIdentity> parents = AclObjectIdentity.withCriteria {
            eq 'objectId', parentOid.identifier
            eq 'aclClass', AclClass.findByClassName(parentOid.type)
//                aclClass {
//                    eq 'className', parentOid.type
//                }
        }

        List<AclObjectIdentity> children = AclObjectIdentity.withCriteria {
            "in" "parent", parents
        }

        if (!children) {
            return null
        }

        children.collect { AclObjectIdentity aoi -> new ObjectIdentityImpl(lookupClass(aoi.aclClass.className), aoi.objectId) }
    }
}
