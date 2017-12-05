package grails.plugin.springsecurity.acl

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

@ToString(excludes = 'version', includeNames = true)
@EqualsAndHashCode
class AclSid {
    String id

    String sid
    boolean principal

    @Override
    String toString() {
        "AclSid id $id, sid $sid, principal $principal"
    }

    static mapping = {
        version false
    }

    static constraints = {
        principal unique: 'sid'
        sid blank: false, size: 1..255
    }
}
