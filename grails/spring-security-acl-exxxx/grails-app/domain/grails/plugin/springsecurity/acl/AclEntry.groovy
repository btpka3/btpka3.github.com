package grails.plugin.springsecurity.acl

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

@ToString(excludes = 'version', includeNames = true)
@EqualsAndHashCode
class AclEntry {

    String id

    AclObjectIdentity aclObjectIdentity
    int aceOrder
    AclSid sid
    int mask
    boolean granting
    boolean auditSuccess
    boolean auditFailure

    @Override
    String toString() {
        "AclEntry id $id, aceOrder $aceOrder, mask $mask, granting $granting, " +
                "aclObjectIdentity $aclObjectIdentity"
    }

    static mapping = {
        version false
        sid column: 'sid'
        aclObjectIdentity column: 'acl_object_identity'
    }

    static constraints = {
        aceOrder unique: 'aclObjectIdentity'
    }
}
