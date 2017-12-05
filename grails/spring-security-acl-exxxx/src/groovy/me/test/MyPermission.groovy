package me.test

import org.springframework.security.acls.domain.BasePermission
import org.springframework.security.acls.model.Permission

/**
 *
 * 参考：
 *  https://grails-plugins.github.io/grails-spring-security-acl/v2/guide/usage.html#customPermissions
 *
 */
class MyPermission extends BasePermission {
    public static final Permission APPROVE = new MyPermission(1 << 5, 'V' as char);

    protected MyPermission(int mask) {
        super(mask);
    }

    protected MyPermission(int mask, char code) {
        super(mask, code);
    }
}
