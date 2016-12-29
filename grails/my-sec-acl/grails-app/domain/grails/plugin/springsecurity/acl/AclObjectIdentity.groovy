package grails.plugin.springsecurity.acl

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

@ToString(excludes = 'version', includeNames = true, includeSuper = true)
@EqualsAndHashCode // GormAclLookupStrategy.groovy:181
class AclObjectIdentity extends AbstractAclObjectIdentity {

    String id

    String objectId  // 原来的是 Long，MongoDB的 Object Id 无法转化为long

    String zzz = "zzzz111"

    @Override
    String toString() {
        "AclObjectIdentity id $id, aclClass $aclClass.className, " +
                "objectId $objectId, entriesInheriting $entriesInheriting"
    }

    static mapping = {
        version false
        aclClass column: 'object_id_class'
        owner column: 'owner_sid'
        parent column: 'parent_object'
        objectId column: 'object_id_identity'
    }

    static constraints = {
        objectId unique: 'aclClass'
    }
}
