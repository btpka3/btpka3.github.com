package me.test.demo.acl;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.flyway.FlywayAutoConfiguration;
import org.springframework.boot.autoconfigure.security.AuthenticationManagerConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.security.access.expression.SecurityExpressionRoot;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.jdbc.JdbcDaoImpl;
import org.springframework.security.provisioning.GroupManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestWrapper;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * @author 当千
 * @date 2019-01-10
 * @see AuthenticationManagerConfiguration
 * @see JdbcDaoImpl
 * @see SecurityContextHolderAwareRequestWrapper
 * @see WebSecurityConfigurerAdapter
 */
@RunWith(SpringJUnit4ClassRunner.class)
@Import(RoleTest.Application.class)
@Transactional
@Rollback
public class RoleTest {


    @SpringBootApplication(exclude = FlywayAutoConfiguration.class)
    @EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
    public static class Application {


        @Autowired
        void initDb(DataSource dataSource) {
            ResourceDatabasePopulator p = new ResourceDatabasePopulator(
                    new ClassPathResource("drop.sql"),
                    new ClassPathResource("create.sql")
            );
            p.execute(dataSource);
        }


        // UserDetailsManager, UserDetailsService, GroupManager

        //
        @Bean
        JdbcUserDetailsManager userDetailsManager(
                JdbcTemplate jdbcTemplate,
                AuthenticationManager authenticationManager
        ) {
            JdbcUserDetailsManager manager = new JdbcUserDetailsManager();
            manager.setJdbcTemplate(jdbcTemplate);
            manager.setAuthenticationManager(authenticationManager);

            // 测试用
            manager.setEnableGroups(true);
            return manager;
        }


        @Autowired
        void configAuthenticationManager(
                AuthenticationManagerBuilder auth,
                DataSource dataSource
        ) throws Exception {

            // @formatter:off
            auth.jdbcAuthentication()
                    .dataSource(dataSource)
                    //.groupAuthoritiesByUsername()
            ;

//            auth.inMemoryAuthentication()
//                    .withUser("admin")
//                    .password("admin")
//                    .authorities("AAA")
//                    .roles("ADMIN", "USER")
//
//                    .and()
//                    .withUser("user")
//                    .password("user")
//                    .authorities("UUU")
//                    .roles("USER")
//
//                    .and()
//                    .withUser("basic")
//                    .password("basic")
//                    .authorities("UUU")
//                    .roles("U");
            // @formatter:on
        }

        @Bean
            //@Order(SecurityProperties.DEFAULT_FILTER_ORDER)
        WebSecurityConfigurerAdapter webSecurityConfigurerAdapter() {
            return new WebSecurityConfigurerAdapter() {

            };
        }


//        @Bean
//        DaoAuthenticationProvider daoAuthenticationProvider(UserDetailsService userDetailsService) {
//            DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
//            provider.setUserDetailsService(userDetailsService);
//            return provider;
//        }

//        @Bean
//        AuthenticationManager authenticationManager(
//                List<AuthenticationProvider> providers
//        ) {
//            AuthenticationManager manager = new ProviderManager(providers);
//            return manager;
//        }
//

//        @Bean
//        RoleHierarchy mutableAclService() {
//            return new RoleHierarchyImpl();
//        }
    }

    private static final Logger log = LoggerFactory.getLogger(RoleTest.class);

    @Autowired//(required = false)
    private GroupManager groupManager;
    @Autowired
    private UserDetailsManager userDetailsManager;

//    @Autowired(required = false)
//    private SecurityExpressionOperations secExprOps;
//    private SecurityExpressionHandler securityExpressionHandler;

    @Autowired
    private AuthenticationManager authenticationManager;


    @Test
    public void test01() {

        log.info("userDetailsManager = " + userDetailsManager);

        // 创建用户 zhang3，并对其直接授权
        {
            UserDetails user = new User(
                    "zhang3",
                    "zhang3_pwd",
                    AuthorityUtils.createAuthorityList("R_A")
            );
            userDetailsManager.createUser(user);
        }

        // 创建用户 li4
        {
            UserDetails user = new User(
                    "li4",
                    "li4_pwd",
                    Collections.emptyList()
            );
            userDetailsManager.createUser(user);
        }

        if (groupManager == null) {
            log.warn("groupManager is null");
            return;
        } else {

            log.info("test groupManager = " + groupManager);
        }

        {
            groupManager.createGroup("group_admin",
                    AuthorityUtils.createAuthorityList("R_B", "R_C")
            );
        }
        {
            groupManager.createGroup("group_developer",
                    AuthorityUtils.createAuthorityList("R_C", "R_D")
            );
        }

        // 检查用户组
        {
            List<String> groupList = groupManager.findAllGroups();

            Assert.assertEquals(2, groupList.size());
            Assert.assertTrue(groupList.stream().anyMatch(auth -> Objects.equals("group_admin", auth)));
            Assert.assertTrue(groupList.stream().anyMatch(auth -> Objects.equals("group_developer", auth)));
        }
        {
            groupManager.addUserToGroup("zhang3", "group_admin");
            groupManager.addUserToGroup("zhang3", "group_developer");
            groupManager.addUserToGroup("li4", "group_developer");
        }


        {
            List<String> userList = groupManager.findUsersInGroup("group_developer");
            Assert.assertEquals(2, userList.size());
            Assert.assertTrue(userList.stream().anyMatch(auth -> Objects.equals("zhang3", auth)));
            Assert.assertTrue(userList.stream().anyMatch(auth -> Objects.equals("li4", auth)));
        }


        {

            // 获取用户权限的时候，会把用户组中的权限也一并获取出来。
            UserDetails user = userDetailsManager.loadUserByUsername("zhang3");
            Assert.assertNotNull(user);
            Collection<? extends GrantedAuthority> authorities = user.getAuthorities();

            Assert.assertEquals(4, authorities.size());
            Assert.assertTrue(AuthorityUtils.authorityListToSet(authorities).contains("R_A"));
            Assert.assertTrue(AuthorityUtils.authorityListToSet(authorities).contains("R_B"));
            Assert.assertTrue(AuthorityUtils.authorityListToSet(authorities).contains("R_C"));
            Assert.assertTrue(AuthorityUtils.authorityListToSet(authorities).contains("R_D"));

        }

//        {
//            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
//                    "zhang3",
//                    "zhang3_pwd",
//                    Arrays.asList(new SimpleGrantedAuthority("ROLE_admin"))
//            );
//            authenticationManager.authenticate()
//
//            userDetailsManager.
//        }


        {
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                    "Username_zhang3",
                    "Password_zhang3",
                    AuthorityUtils.createAuthorityList("R_A", "R_B", "R_C")
            );
            SecurityExpressionRoot root = new SecurityExpressionRoot(authentication) {
            };

        }
//
//        Map<String, List<String>> roleHierarchyMap = new HashMap<>();
//        roleHierarchyMap.put("ROLE_10", Arrays.asList("ROLE_11", "ROLE_12"));
//
//        RoleHierarchyUtils.roleHierarchyFromMap(roleHierarchyMap);

    }


}
