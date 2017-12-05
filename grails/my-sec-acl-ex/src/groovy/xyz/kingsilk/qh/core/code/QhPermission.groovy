package xyz.kingsilk.qh.core.code

import org.springframework.security.acls.domain.BasePermission
import org.springframework.security.acls.model.Permission

/**
 *
 * 参考：
 *  https://grails-plugins.github.io/grails-spring-security-acl/v2/guide/usage.html#customPermissions
 *
 */
class QhPermission extends BasePermission {
    public static final Permission TEST = new QhPermission(1 << 5, 'V' as char);

    protected QhPermission(int mask) {
        super(mask);
    }

    protected QhPermission(int mask, char code) {
        super(mask, code);
    }
}
