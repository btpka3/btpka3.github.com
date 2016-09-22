package my.grails3

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

/**
 * 使用mongodb存储,并简化domain结构。
 */
@EqualsAndHashCode(includes = 'username')
@ToString(includes = 'username', includeNames = true, includePackage = false)
class MyUser implements Serializable {

    private static final long serialVersionUID = 1


    static constraints = {
        password blank: false, password: true
        username blank: false, unique: true
    }
    static embedded = [
            "tencent",
            "sinaWeibo"
    ]
    static mapping = {
        password column: '`password`'
    }
    static mapWith = "mongo"
    static transients = [
            'springSecurityService'
    ]



    transient springSecurityService

    String id
    Date dateCreated
    Date lastUpdated
    boolean deleted

    String username
    String password
    boolean enabled = true
    boolean accountExpired
    boolean accountLocked
    boolean passwordExpired

    Set<String> authorities = [];
//    Set<RoleGroup> getAuthorities() {
//        UserRoleGroup.findAllByUser(this)*.roleGroup
//    }

    def beforeInsert() {
        encodePassword()
    }

    def beforeUpdate() {
        if (isDirty('password')) {
            encodePassword()
        }
    }

    protected void encodePassword() {
        password = springSecurityService?.passwordEncoder ? springSecurityService.encodePassword(password) : password
    }

}
