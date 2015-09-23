package me.test

import org.apache.commons.codec.digest.DigestUtils
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException

/** test only */
class MyUserDetailsService implements UserDetailsService {

    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        String password = DigestUtils.md5Hex(username.getBytes());
        Collection<GrantedAuthority> authorities = Collections.emptyList()
        return new User(username, password, authorities);
    }
}
