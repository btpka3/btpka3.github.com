package me.test.demo.authority;

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
import org.springframework.context.annotation.Lazy;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.security.access.expression.SecurityExpressionRoot;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationTrustResolverImpl;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
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
import org.springframework.test.context.junit4.SpringRunner;
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
 * @see AuthenticationConfiguration
 */
@RunWith(SpringRunner.class)
@Import(AuthorityTest.Application.class)
@Transactional
@Rollback
public class AuthorityTest {


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


        /**
         * @param jdbcTemplate
         * @param authenticationManager 该参数前的 Lazy 注解很重要。否则会循环依赖。
         * @return
         * @see AuthenticationConfiguration#initializeUserDetailsBeanManagerConfigurer
         * 负责从 Spring 容器中获取 bean UserDetailsService,
         * 并最终作为 DaoAuthenticationProvider 包装，最后包装在 ProviderManager 中。
         */
        @Bean
        JdbcUserDetailsManager userDetailsManager(
                JdbcTemplate jdbcTemplate,
                @Lazy AuthenticationManager authenticationManager
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

//            auth.jdbcAuthentication()
//            .getUserDetailsService().setEnableGroups(true);
//
//            auth.userDetailsService();

            // @formatter:off
            // 由于我们自己明确提供了 JdbcUserDetailsManager，这里就不要再调用 jdbcAuthentication() 了
            // 否则会内部再初始化一个 JdbcUserDetailsManager 的。
//            auth.jdbcAuthentication()
//                    .dataSource(dataSource)

                    // 如果 JdbcUserDetailsManager#setEnableGroups(true) 没有明确指定
                    // 下面的代码会自动设定
                    //.groupAuthoritiesByUsername()
            ;
            // @formatter:on
        }

        @Bean
            //@Order(SecurityProperties.DEFAULT_FILTER_ORDER)
        WebSecurityConfigurerAdapter webSecurityConfigurerAdapter() {
            return new WebSecurityConfigurerAdapter() {

                @Override
                public void init(WebSecurity web) throws Exception {
                    super.init(web);
                }

                @Override
                protected void configure(HttpSecurity http) throws Exception {
                    super.configure(http);
                }

                @Override
                public void configure(WebSecurity web) throws Exception {
                    super.configure(web);
                }
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

    private static final Logger log = LoggerFactory.getLogger(AuthorityTest.class);

    @Autowired//(required = false)
    private GroupManager groupManager;
    @Autowired
    private UserDetailsManager userDetailsManager;

//    @Autowired(required = false)
//    private SecurityExpressionOperations secExprOps;
//    private SecurityExpressionHandler securityExpressionHandler;

    @Autowired
    private AuthenticationManager authenticationManager;


    /**
     * "ROLE_XXX" 用以验证 SpringEL 'hasAnyRole'
     * "R_XXX" 用以验证 SpringEL 'hasAnyAuthority'
     */
    @Test
    public void test01() {

        log.info("userDetailsManager = " + userDetailsManager);

        // 创建用户 zhang3，并对其直接授权
        {
            UserDetails user = new User(
                    "zhang3",
                    "zhang3_pwd",
                    AuthorityUtils.createAuthorityList("ROLE_A", "R_A")
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
                    AuthorityUtils.createAuthorityList("ROLE_B", "ROLE_C", "R_B", "R_C")
            );
        }
        {
            groupManager.createGroup("group_developer",
                    AuthorityUtils.createAuthorityList("ROLE_C", "ROLE_D", "R_C", "R_D")
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

            Assert.assertEquals(8, authorities.size());
            Assert.assertTrue(AuthorityUtils.authorityListToSet(authorities).contains("ROLE_A"));
            Assert.assertTrue(AuthorityUtils.authorityListToSet(authorities).contains("ROLE_B"));
            Assert.assertTrue(AuthorityUtils.authorityListToSet(authorities).contains("ROLE_C"));
            Assert.assertTrue(AuthorityUtils.authorityListToSet(authorities).contains("ROLE_D"));
            Assert.assertTrue(AuthorityUtils.authorityListToSet(authorities).contains("R_A"));
            Assert.assertTrue(AuthorityUtils.authorityListToSet(authorities).contains("R_B"));
            Assert.assertTrue(AuthorityUtils.authorityListToSet(authorities).contains("R_C"));
            Assert.assertTrue(AuthorityUtils.authorityListToSet(authorities).contains("R_D"));

        }

        // 模拟认证过程
        {

            // 代表用户填写的表单（包含用户名、密码，但尚未认证成功
            UsernamePasswordAuthenticationToken auth1 = new UsernamePasswordAuthenticationToken(
                    "zhang3",
                    "zhang3_pwd"
            );
            Authentication auth2 = authenticationManager.authenticate(auth1);

            Assert.assertNotSame(auth1, auth2);

            Assert.assertFalse(auth1.isAuthenticated());
            Assert.assertTrue(auth1.getAuthorities().isEmpty());

            Assert.assertTrue(auth2.isAuthenticated());
            Collection<? extends GrantedAuthority> authorities2 = auth2.getAuthorities();
            Assert.assertEquals(8, authorities2.size());

            // 模拟调用 SpringEL, 参考 Spring Security 的单元测试
            // https://github.com/spring-projects/spring-security/blob/master/core/src/test/java/org/springframework/security/access/expression/SecurityExpressionRootTests.java
            SecurityExpressionRoot root = new SecurityExpressionRoot(auth2) {
            };

            root.setTrustResolver(new AuthenticationTrustResolverImpl());

            Assert.assertTrue(root.isAuthenticated());
            Assert.assertTrue(root.isFullyAuthenticated());
            Assert.assertFalse(root.isAnonymous());
            Assert.assertFalse(root.isRememberMe());

            Assert.assertTrue(root.hasAnyRole("ROLE_A", "ROLE_X"));
            Assert.assertFalse(root.hasAnyRole("ROLE_X", "ROLE_Y"));

            Assert.assertTrue(root.hasAnyAuthority("R_A", "R_X"));
            Assert.assertFalse(root.hasAnyAuthority("R_X", "R_Y"));

            // TODO ACL hasPermission

        }
//
//        Map<String, List<String>> roleHierarchyMap = new HashMap<>();
//        roleHierarchyMap.put("ROLE_10", Arrays.asList("ROLE_11", "ROLE_12"));
//
//        RoleHierarchyUtils.roleHierarchyFromMap(roleHierarchyMap);

    }


}
