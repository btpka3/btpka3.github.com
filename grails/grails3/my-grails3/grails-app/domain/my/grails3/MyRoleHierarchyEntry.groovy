package my.grails3

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

@EqualsAndHashCode(includes = 'entry')
@ToString(includes = 'entry', includeNames = true, includePackage = false)
class MyRoleHierarchyEntry implements Serializable {

    private static final long serialVersionUID = 1
    static constraints = {
        entry blank: false, unique: true
    }

    static mapping = {
        cache true
    }
    static mapWith = "mongo"

    String id
    Date dateCreated
    Date lastUpdated
    boolean deleted


    String entry

}
