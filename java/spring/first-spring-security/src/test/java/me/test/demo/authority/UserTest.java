package me.test.demo.authority;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.flyway.FlywayAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.GroupManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;

/**
 * 该测试用例，每个 testcase 之间没有事务回滚，
 * 方便大家通过启动独立 h2 databse,
 * 每跑一个test case，
 * 就通过 h2 的 web 控制台验证一下数据库内容。
 * <p>
 * 注意：需要修改 application.yaml 中数据库的配置
 *
 * @author 当千
 * @date 2019-01-16
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = UserTest.Application.class)
//@Import(UserTest.Application.class)
//@TestPropertySource
//@AutoConfigureTestDatabase
//@JdbcTest
@Transactional
@Commit
public class UserTest {


    @SpringBootApplication(exclude = FlywayAutoConfiguration.class)
    @EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
    public static class Application {


        @Autowired
        void initDb(DataSource dataSource) {
            ResourceDatabasePopulator p = new ResourceDatabasePopulator(
                    //new ClassPathResource("drop.sql"),

                    // 确保数据库表存在，不删除
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


    }

    private static final Logger log = LoggerFactory.getLogger(UserTest.class);

    @Autowired
    private DataSource dataSource;

    @Autowired
    private GroupManager groupManager;

    @Autowired
    private UserDetailsManager userDetailsManager;

    @Autowired
    private AuthenticationManager authenticationManager;


    /**
     *
     */
    @Test
//    @Transactional
    public void addUser01() {

        log.info("userDetailsManager = " + userDetailsManager);

        UserDetails user = new User(
                "zhang3_" + System.currentTimeMillis(),
                "zhang3_pwd",
                AuthorityUtils.createAuthorityList("ROLE_A", "R_A")
        );
        userDetailsManager.createUser(user);


    }

}

