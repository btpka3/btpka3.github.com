package me.test.demo.acl;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.flyway.FlywayAutoConfiguration;
import org.springframework.cache.Cache;
import org.springframework.cache.support.NoOpCache;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.access.expression.SecurityExpressionRoot;
import org.springframework.security.acls.AclPermissionEvaluator;
import org.springframework.security.acls.domain.*;
import org.springframework.security.acls.jdbc.BasicLookupStrategy;
import org.springframework.security.acls.jdbc.JdbcMutableAclService;
import org.springframework.security.acls.model.*;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationTrustResolverImpl;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.test.context.TestSecurityContextHolder;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * @author 当千
 * @date 2019-01-07
 * @see SidRetrievalStrategyImpl
 * @see ObjectIdentityRetrievalStrategyImpl
 */
@RunWith(SpringRunner.class)
@Import(AclTest.Application.class)
@Transactional
@Rollback
public class AclTest {


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


        @Bean
        MutableAclService mutableAclService(
                DataSource dataSource
        ) {

            Cache cache = new NoOpCache("aa");

            // 只有当前用户有 'ROLE_admin' 权限的，才能管理 ACL
            AclAuthorizationStrategy aclAuthorizationStrategy = new AclAuthorizationStrategyImpl(
                    new SimpleGrantedAuthority("ROLE_admin")
            );
            AuditLogger auditLogger = new ConsoleAuditLogger();
            AclCache aclCache = new SpringCacheBasedAclCache(
                    cache,
                    new DefaultPermissionGrantingStrategy(auditLogger),
                    aclAuthorizationStrategy
            );
            return new JdbcMutableAclService(
                    dataSource,
                    new BasicLookupStrategy(
                            dataSource,
                            aclCache,
                            aclAuthorizationStrategy,
                            auditLogger
                    ),
                    aclCache
            );
        }

        @Bean
        PermissionEvaluator permissionEvaluator(AclService aclService) {
            AclPermissionEvaluator evaluator = new AclPermissionEvaluator(aclService);
            return evaluator;
        }
    }

    @Autowired
    private MutableAclService mutableAclService;

    @Autowired
    private UserDetailsManager userDetailsManager;

    @Autowired
    private PermissionEvaluator permissionEvaluator;

    public static class MyNameList {

        private String name;
        private Long id;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }
    }

    /**
     * 验证 对树状层级对象（资源）的管理。
     */
    @Test
    public void test01() {


        MyNameList o1000 = new MyNameList();
        o1000.setId(1000L);
        o1000.setName("L1000");

        MyNameList o1001 = new MyNameList();
        o1001.setId(1001L);
        o1001.setName("L1001");


        // 等价于 oi1000 = new ObjectIdentityImpl(o1000);
        ObjectIdentity oi1000 = new ObjectIdentityImpl(MyNameList.class, 1000);
        ObjectIdentity oi1001 = new ObjectIdentityImpl(MyNameList.class, 1001);

        Sid zhang3 = new PrincipalSid("zhang3");
        Sid li4 = new PrincipalSid("li4");
        Sid wang5 = new PrincipalSid("wang5");

        UsernamePasswordAuthenticationToken authZhang3 = new UsernamePasswordAuthenticationToken(
                "zhang3",
                "zhang3_pwd",
                Arrays.asList(new SimpleGrantedAuthority("ROLE_admin"))
        );
        UsernamePasswordAuthenticationToken authLi4 = new UsernamePasswordAuthenticationToken(
                "li4",
                "li4_pwd",
                Arrays.asList(new SimpleGrantedAuthority("ROLE_admin"))
        );

        // 模拟当前登录的用户
        {
            SecurityContext securityContext = new SecurityContextImpl();
            securityContext.setAuthentication(authZhang3);
            TestSecurityContextHolder.setContext(securityContext);
        }

        CumulativePermission permission1 = new CumulativePermission();
        permission1.set(BasePermission.ADMINISTRATION);
        permission1.set(BasePermission.CREATE);
        permission1.set(BasePermission.WRITE);
        permission1.set(BasePermission.READ);
        permission1.set(BasePermission.DELETE);


        // 向 父资源 配置权限
        MutableAcl acl1000;
        {
            // 向 acl_object_identity 中插入一条记录
            MutableAcl acl = mutableAclService.createAcl(oi1000);
            acl1000 = acl;
            acl.setOwner(zhang3);

            // 授权给 li4  一个权限（注意：这里是一个新的权限，而非所有权限，
            {
                acl.insertAce(acl.getEntries().isEmpty() ? 0 : acl.getEntries().size() - 1, BasePermission.WRITE, li4, true);
            }

            // 更新
            // 会删除 acl_entry 中相关记录，再重新插入; 更新 acl_object_identity; 更新缓存。
            mutableAclService.updateAcl(acl);

        }

        // 向 子资源 配置权限
        MutableAcl acl1001;
        {

            // 向 acl_object_identity 中插入一条记录
            MutableAcl acl = mutableAclService.createAcl(oi1001);
            acl1001 = acl;
            acl.setOwner(zhang3);
            acl.setParent(acl1000);
            acl.setEntriesInheriting(true);

            // 拒绝 li4 的  ADMINISTRATION  权限。
            {
                acl.insertAce(acl.getEntries().isEmpty() ? 0 : acl.getEntries().size() - 1, BasePermission.ADMINISTRATION, li4, false);
            }

            // 授权给 li4  一个权限（注意：这里是一个新的权限，而非所有权限，
            // 因为 DefaultPermissionGrantingStrategy 没有按照 bitwise 进行比较。
            {
                acl.insertAce(acl.getEntries().isEmpty() ? 0 : acl.getEntries().size() - 1, permission1, li4, true);
            }

            // 更新
            // 会删除 acl_entry 中相关记录，再重新插入; 更新 acl_object_identity; 更新缓存。
            mutableAclService.updateAcl(acl);
        }


        // 检查
        {

            Acl acl = mutableAclService.readAclById(oi1001);

            List<AccessControlEntry> aceList = acl.getEntries();

            Assert.assertEquals(2, aceList.size());

            AccessControlEntry aceLi4 = aceList.stream()
                    .filter(ace -> Objects.equals("li4", ((PrincipalSid) ace.getSid()).getPrincipal()))
                    .findFirst()
                    .orElse(null);

            Assert.assertNotNull(aceLi4);


            Assert.assertSame(acl, aceLi4.getAcl());
            Assert.assertTrue(aceLi4.isGranting());

            // 验证 子资源中 授权和拒绝的权限
            Assert.assertTrue(acl.isGranted(Arrays.asList(permission1), Arrays.asList(li4), true));
            Assert.assertFalse(acl.isGranted(Arrays.asList(BasePermission.ADMINISTRATION), Arrays.asList(li4), true));

            // 确认没有按照 bitwise 进行权限判定
            try {
                acl.isGranted(Arrays.asList(BasePermission.READ), Arrays.asList(li4), true);
                Assert.fail("should not found the permission");
            } catch (NotFoundException e) {

            }

            // 通过 继承父资源 的权限，而获取了权限
            Assert.assertTrue(acl.isGranted(Arrays.asList(BasePermission.WRITE), Arrays.asList(li4), true));

        }

        // spring EL 校验
        {
            SecurityExpressionRoot root = new SecurityExpressionRoot(authLi4) {
            };

            root.setTrustResolver(new AuthenticationTrustResolverImpl());
            root.setPermissionEvaluator(permissionEvaluator);
            Assert.assertTrue(root.hasPermission(o1001, permission1));
            Assert.assertFalse(root.hasPermission(o1001, BasePermission.ADMINISTRATION));
            Assert.assertFalse(root.hasPermission(o1001, BasePermission.READ));
            Assert.assertTrue(root.hasPermission(o1001, BasePermission.WRITE));
        }
    }
//
//
//    /**
//     * 通过层级用户组 对 资源的权限管理。  验证 acl 的 SpringEL。
//     */
//    @Test
//    public void test02() {
//
//        // 创建用户 zhang3，并对其直接授权
//        {
//            UserDetails user = new User(
//                    "zhang3",
//                    "zhang3_pwd",
//                    AuthorityUtils.createAuthorityList("ROLE_A", "R_A")
//            );
//            userDetailsManager.createUser(user);
//        }
//
//        // 创建用户 li4
//        {
//            UserDetails user = new User(
//                    "li4",
//                    "li4_pwd",
//                    Collections.emptyList()
//            );
//            userDetailsManager.createUser(user);
//        }
//
//        MyNameList o1000 = new MyNameList();
//        o1000.setId(1000L);
//        o1000.setName("L1000");
//
//        MyNameList o1001 = new MyNameList();
//        o1001.setId(1000L);
//        o1001.setName("L1001");
//
//
//        ObjectIdentity oi1000 = new ObjectIdentityImpl(MyNameList.class, 1000L);
//        ObjectIdentity oi1001 = new ObjectIdentityImpl(MyNameList.class, 1001L);
//
//        Sid zhang3 = new PrincipalSid("zhang3");
//        Sid li4 = new PrincipalSid("li4");
//        Sid wang5 = new PrincipalSid("wang5");
//
//        // 模拟当前登录的用户
//        {
//            SecurityContext securityContext = new SecurityContextImpl();
//            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
//                    "Username_zhang3",
//                    "Password_zhang3",
//                    Arrays.asList(new SimpleGrantedAuthority("ROLE_admin"))
//            );
//
//            securityContext.setAuthentication(authentication);
//            TestSecurityContextHolder.setContext(securityContext);
//        }
//
//        CumulativePermission permission1 = new CumulativePermission();
//        permission1.set(BasePermission.ADMINISTRATION);
//        permission1.set(BasePermission.CREATE);
//        permission1.set(BasePermission.WRITE);
//        permission1.set(BasePermission.READ);
//        permission1.set(BasePermission.DELETE);
//
//
//        // 向 父资源 配置权限
//        MutableAcl acl1000;
//        {
//            // 向 acl_object_identity 中插入一条记录
//            MutableAcl acl = mutableAclService.createAcl(oi1000);
//            acl1000 = acl;
//            acl.setOwner(zhang3);
//
//            // 授权给 li4  一个权限（注意：这里是一个新的权限，而非所有权限，
//            {
//                acl.insertAce(acl.getEntries().isEmpty() ? 0 : acl.getEntries().size() - 1, BasePermission.WRITE, li4, true);
//            }
//
//            // 更新
//            // 会删除 acl_entry 中相关记录，再重新插入; 更新 acl_object_identity; 更新缓存。
//            mutableAclService.updateAcl(acl);
//
//        }
//
//        // 向 子资源 配置权限
//        MutableAcl acl1001;
//        {
//
//            // 向 acl_object_identity 中插入一条记录
//            MutableAcl acl = mutableAclService.createAcl(oi1001);
//            acl1001 = acl;
//            acl.setOwner(zhang3);
//            acl.setParent(acl1000);
//            acl.setEntriesInheriting(true);
//
//            // 拒绝 li4 的  ADMINISTRATION  权限。
//            {
//                acl.insertAce(acl.getEntries().isEmpty() ? 0 : acl.getEntries().size() - 1, BasePermission.ADMINISTRATION, li4, false);
//            }
//
//            // 授权给 li4  一个权限（注意：这里是一个新的权限，而非所有权限，
//            // 因为 DefaultPermissionGrantingStrategy 没有按照 bitwise 进行比较。
//            {
//                acl.insertAce(acl.getEntries().isEmpty() ? 0 : acl.getEntries().size() - 1, permission1, li4, true);
//            }
//
//            // 更新
//            // 会删除 acl_entry 中相关记录，再重新插入; 更新 acl_object_identity; 更新缓存。
//            mutableAclService.updateAcl(acl);
//        }
//        {
//            SecurityExpressionRoot root = new SecurityExpressionRoot(auth2) {
//            };
//
//            root.setTrustResolver(new AuthenticationTrustResolverImpl());
//            root.setPermissionEvaluator(null);
//            root.hasPermission(o1000, permission1);
//        }
//
//        // 检查
//        {
//
//            Acl acl = mutableAclService.readAclById(oi1001);
//
//            List<AccessControlEntry> aceList = acl.getEntries();
//
//            Assert.assertEquals(2, aceList.size());
//
//            AccessControlEntry aceLi4 = aceList.stream()
//                    .filter(ace -> Objects.equals("li4", ((PrincipalSid) ace.getSid()).getPrincipal()))
//                    .findFirst()
//                    .orElse(null);
//
//            Assert.assertNotNull(aceLi4);
//
//
//            Assert.assertSame(acl, aceLi4.getAcl());
//            Assert.assertTrue(aceLi4.isGranting());
//
//            // 验证 子资源中 授权和拒绝的权限
//            Assert.assertTrue(acl.isGranted(Arrays.asList(permission1), Arrays.asList(li4), true));
//            Assert.assertFalse(acl.isGranted(Arrays.asList(BasePermission.ADMINISTRATION), Arrays.asList(li4), true));
//
//            // 确认没有按照 bitwise 进行权限判定
//            try {
//                acl.isGranted(Arrays.asList(BasePermission.READ), Arrays.asList(li4), true);
//                Assert.fail("should not found the permission");
//            } catch (NotFoundException e) {
//
//            }
//
//            // 通过 继承父资源 的权限，而获取了权限
//            Assert.assertTrue(acl.isGranted(Arrays.asList(BasePermission.WRITE), Arrays.asList(li4), true));
//
//        }
//    }

}
