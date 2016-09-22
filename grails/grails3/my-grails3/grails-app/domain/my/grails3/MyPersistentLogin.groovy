package my.grails3

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

@EqualsAndHashCode(includes = ['series', 'username'])
@ToString(includes = ['series', 'username'], cache = true, includeNames = true, includePackage = false)
class MyPersistentLogin implements Serializable {

    private static final long serialVersionUID = 1
    static constraints = {
        series maxSize: 64
        token maxSize: 64
        username maxSize: 64
    }

    static mapping = {
        //table 'persistent_login'
        //id name: 'series', generator: 'assigned'
        //version false
    }
    static mapWith = "mongo"


    String id
    Date dateCreated
    Date lastUpdated
    boolean deleted



    String series
    String username
    String token
    Date lastUsed


}
