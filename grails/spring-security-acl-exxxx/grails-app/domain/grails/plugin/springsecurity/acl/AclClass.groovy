package grails.plugin.springsecurity.acl

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

@ToString(excludes = 'version', includeNames = true)
@EqualsAndHashCode
class AclClass {

    String id

    String className

    @Override
    String toString() {
        "AclClass id $id, className $className"
    }

    static mapping = {
        className column: 'class'
        version false
    }

    static constraints = {
        className unique: true, blank: false
    }
}
