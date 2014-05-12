package me.test;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class MyUserDetailsService implements UserDetailsService {

    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {

        // 这里的userName应当是 员工ID 或者 员工别名
        // 然后从数据库中读取相应信息，构建、填充UserDetails对象并返回。
        String password = null;
        boolean enabled = false;
        boolean accountNonExpired = false;
        boolean credentialsNonExpired = false;
        boolean accountNonLocked = false;
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();

        if ("zhang3".equals(username)) {
            password = "123";
            enabled = true;
            accountNonExpired = true;
            credentialsNonExpired = true;
            accountNonLocked = true;
            authorities.add(new SimpleGrantedAuthority("XROLE_READ_APPOINTMENT"));

        } else if ("li4".equals(username)) {
            password = "123";
            enabled = true;
            accountNonExpired = true;
            credentialsNonExpired = true;
            accountNonLocked = true;
            authorities.add(new SimpleGrantedAuthority("XROLE_READ_STAFF"));

        } else if ("wang5".equals(username)) {
            password = "123";
            enabled = true;
            accountNonExpired = true;
            credentialsNonExpired = true;
            accountNonLocked = true;
            authorities.add(new SimpleGrantedAuthority("XROLE_READ_APPOINTMENT"));
            authorities.add(new SimpleGrantedAuthority("XROLE_READ_STAFF"));
        }

        //  User这个类是Spring提供的，如果需要附带更多信息，可以扩展它。
        return new User(username, password, enabled, accountNonExpired,
                credentialsNonExpired, accountNonLocked, authorities);

    }
}
