package my.grails3

import grails.plugin.springsecurity.userdetails.GrailsUser
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.provisioning.MutableUser
import org.springframework.security.core.userdetails.User as SecUser
import org.springframework.stereotype.Service

@Service("userDetailsService")
// FIXME not work
class MyUserDetails implements UserDetailsService {


    @Override
    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {


        def user = MyUser.findWhere(username: username)
        if (!user) {
            log.warn 'User not found: {}', username
            throw new UsernameNotFoundException()
        }

        Collection<GrantedAuthority> authorities = loadAuthorities(user, username, true)
//        createUserDetails user, authorities
//        new GrailsUser(username, password, enabled, !accountExpired, !passwordExpired,
//                !accountLocked, authorities, user.id)
//
//
//        public User(String username, String password, boolean enabled,
//                    boolean accountNonExpired, boolean credentialsNonExpired,
//                    boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities) {
//
//        }
        return new MutableUser(new SecUser(
                user.username,
                user.password,
                user.enabled,
                !user.accountExpired,
                !user.passwordExpired,
                !user.accountLocked,
                authorities

        ))
    }


    protected Collection<GrantedAuthority> loadAuthorities(MyUser user, String username, boolean loadRoles) {
        if (!loadRoles) {
            return []
        }

        user.authorities.collect { new SimpleGrantedAuthority(it) } ?: [NO_ROLE]

    }
}
