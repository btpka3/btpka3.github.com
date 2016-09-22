package my.grails3

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

/**
 * 使用mongodb存储,并简化domain结构。
 */
@EqualsAndHashCode(includes = 'username')
@ToString(includes = 'username', includeNames = true, includePackage = false)
class MyGroup implements Serializable {


    private static final long serialVersionUID = 1

    static constraints = {
        name blank: false, unique: true
    }

    static mapping = {
        cache true
    }
    static mapWith = "mongo"


    String id
    Date dateCreated
    Date lastUpdated
    boolean deleted


    String name

    Set<String> authorities = [];
//    Set<Role> getAuthorities() {
//        RoleGroupRole.findAllByRoleGroup(this)*.role
//    }


}
