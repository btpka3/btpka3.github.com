package me.test;

import org.springframework.security.core.GrantedAuthority;

public class HISAuthority implements GrantedAuthority {

    private static final long serialVersionUID = 1L;

    private String authority = null;

    /** 权限：读取所有预约记录*/
    public static HISAuthority READ_APPOINTMENT = new HISAuthority("READ_APPOINTMENT");

    /** 权限：读取所有员工信息*/
    public static HISAuthority READ_STAFF = new HISAuthority("READ_STAFF");

    private HISAuthority(String authority) {
        this.authority = authority;
    }

    public String getAuthority() {
        return authority;
    }

}
