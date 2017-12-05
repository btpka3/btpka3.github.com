package me.test

import grails.plugin.springsecurity.acl.AclClass
import grails.plugin.springsecurity.acl.AclEntry
import grails.plugin.springsecurity.acl.AclObjectIdentity
import grails.plugin.springsecurity.acl.jdbc.GormAclLookupStrategy
import org.springframework.security.acls.model.Acl
import org.springframework.security.acls.model.ObjectIdentity
import org.springframework.security.acls.model.Sid
import org.springframework.util.Assert

/**
 *
 */
class MyGormAclLookupStrategy extends GormAclLookupStrategy {

    protected Map<ObjectIdentity, Acl> lookupObjectIdentities(
            Collection<ObjectIdentity> objectIdentities, List<Sid> sids) {

        Assert.notEmpty objectIdentities, 'Must provide identities to lookup'

        Map<Serializable, Acl> acls = [:] // contains Acls with StubAclParents

        List<AclObjectIdentity> aclObjectIdentities = AclObjectIdentity.withCriteria {
            or {
                for (ObjectIdentity objectIdentity in objectIdentities) {
                    and {
                        eq 'objectId', objectIdentity.identifier
                        eq 'aclClass', AclClass.findByClassName(objectIdentity.type)
                    }
                }
            }
            order 'objectId', 'asc'
        }

//        List<AclObjectIdentity> aclObjectIdentities = AclObjectIdentity.withCriteria {
//            createAlias 'aclClass', 'ac'
//            or {
//                for (ObjectIdentity objectIdentity in objectIdentities) {
//                    and {
//                        eq 'objectId', objectIdentity.identifier
//                        eq 'ac.className', objectIdentity.type
//                    }
//                }
//            }
//            order 'objectId', 'asc'
//        }

        unwrapProxies aclObjectIdentities

        Map<AclObjectIdentity, List<AclEntry>> aclObjectIdentityMap = findAcls(aclObjectIdentities)

        List<AclObjectIdentity> parents = convertEntries(aclObjectIdentityMap, acls, sids)
        if (parents) {
            lookupParents acls, parents, sids
        }

        // Finally, convert our 'acls' containing StubAclParents into true Acls
        Map<ObjectIdentity, Acl> result = [:]
        for (Acl inputAcl in acls.values()) {
            Acl converted = convert(acls, inputAcl.id)
            result[converted.objectIdentity] = converted
        }

        return result
    }

}
