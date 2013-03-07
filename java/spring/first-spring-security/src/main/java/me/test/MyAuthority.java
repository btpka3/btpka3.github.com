package me.test;

import org.springframework.security.core.GrantedAuthority;

public enum MyAuthority implements GrantedAuthority {

    /** 权限：读取所有预约记录 */
    ROLE_READ_APPOINTMENT("ROLE_READ_APPOINTMENT"),

    /** 权限：读取所有员工信息 */
    ROLE_READ_STAFF("ROLE_READ_STAFF");

    /** 权限代码，该值可根据需要插入到【用户权限关联表】等表中的【权限】字段中 */
    private String authority = null;

    private MyAuthority(String authority) {
        this.authority = authority;
    }

    public String getAuthority() {
        return authority;
    }

}
