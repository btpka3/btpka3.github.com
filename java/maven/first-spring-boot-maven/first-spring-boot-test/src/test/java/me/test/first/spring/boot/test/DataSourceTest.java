package me.test.first.spring.boot.test;

import com.mysql.cj.jdbc.MysqlConnectionPoolDataSource;
import com.mysql.cj.jdbc.MysqlDataSource;
import com.mysql.cj.jdbc.MysqlXADataSource;
import com.zaxxer.hikari.HikariDataSource;
import lombok.SneakyThrows;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.util.Properties;

/**
 * @author dangqian.zll
 * @date 2023/6/27
 * @sse DataSourceAutoConfiguration
 * @see <a href="https://github.com/brettwooldridge/HikariCP/wiki/MySQL-Configuration">MySQL Configuration</a>
 * @see <a href="https://dev.mysql.com/doc/connector-j/8.0/en/connector-j-reference-configuration-properties.html">6.3 Configuration Properties</a>
 * @see HikariDataSource
 * @see MysqlDataSource
 * @see MysqlConnectionPoolDataSource
 * @see MysqlXADataSource
 * @see DataSourceProperties
 * @see DataSourceAutoConfiguration
 * @see org.springframework.boot.autoconfigure.jdbc.DataSourceConfiguration.Hikari
 * @see com.zaxxer.hikari.pool.PoolBase#initializeDataSource
 */
public class DataSourceTest {

    /**
     * 代码层面不依赖内部数据库的driver类，（PS: 运行态依赖）
     */
    @SneakyThrows
    public void hikariTest01() {
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setJdbcUrl("jdbc:mysql://localhost:3306/simpsons");
        dataSource.setUsername("test");
        dataSource.setPassword("test");

        // 默认: -1
        dataSource.setMaximumPoolSize(20);
        // 默认: 10分钟
        dataSource.setIdleTimeout(10 * 60 * 1000L);
        // 默认: 30秒
        dataSource.setConnectionTimeout(30 * 1000L);
        // 默认: 30分钟
        dataSource.setMaxLifetime(30 * 60 * 1000L);
        dataSource.setPoolName("spring-boot-hikari-mysql-cp");

        dataSource.setDataSourceClassName("com.mysql.cj.jdbc.MysqlDataSource");
        {
            Properties dsProperties = new Properties();
            dsProperties.put("cachePrepStmts", true);
            dsProperties.put("prepStmtCacheSize", 250);
            dataSource.setDataSourceProperties(dsProperties);
        }
    }

    /**
     * 直接指定 HikariDataSource#setDataSource
     */

    @SneakyThrows
    public void hikariTest02() {
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setJdbcUrl("jdbc:mysql://localhost:3306/simpsons");
        dataSource.setUsername("test");
        dataSource.setPassword("test");
        // 默认: -1
        dataSource.setMaximumPoolSize(20);
        // 默认: 10分钟
        dataSource.setIdleTimeout(10 * 60 * 1000L);
        // 默认: 30秒
        dataSource.setConnectionTimeout(30 * 1000L);
        // 默认: 30分钟
        dataSource.setMaxLifetime(30 * 60 * 1000L);
        dataSource.setPoolName("spring-boot-hikari-mysql-cp");

        {
            MysqlDataSource mysqlDataSource = new MysqlDataSource();
            // 默认: false
            mysqlDataSource.setCachePrepStmts(true);
            // 默认: 25
            mysqlDataSource.setPrepStmtCacheSize(250);
            // 默认: 256
            mysqlDataSource.setPrepStmtCacheSqlLimit(2048);
            // 默认: false
            mysqlDataSource.setUseServerPrepStmts(true);
            // 默认: false
            mysqlDataSource.setUseLocalSessionState(true);
            // 默认: false
            mysqlDataSource.setRewriteBatchedStatements(true);
            // 默认: false
            mysqlDataSource.setCacheResultSetMetadata(true);
            // 默认: false
            mysqlDataSource.setCacheServerConfiguration(true);
            // 默认: false
            mysqlDataSource.setElideSetAutoCommits(true);
            // 默认: true
            mysqlDataSource.setMaintainTimeStats(false);

            // 默认: false
            mysqlDataSource.setCreateDatabaseIfNotExist(false);
            // 默认: 无
            mysqlDataSource.setCharacterEncoding("utf-8");
            // 默认: true
            mysqlDataSource.setUseSSL(false);
            // 默认: false
            mysqlDataSource.setAllowPublicKeyRetrieval(true);
            mysqlDataSource.setServerTimezone("UTC");

            dataSource.setDataSource(mysqlDataSource);
        }
    }

    /**
     * spring boot 配置， 参考: DataSouceTest-hikari.properties
     *
     * @see DataSourceConfiguration.Hikari#dataSource(DataSourceProperties)
     */
    public void hikariTest03() {

        /*
         * org.springframework.boot.autoconfigure.jdbc.DataSourceConfiguration.Hikari#dataSource(DataSourceProperties)
         * - 入参：是按照 prefix = "spring.datasource" 创建的 spring bean: DataSourceProperties，
         *      此时 DataSourceProperties 是没有 "data-source-properties" 相关配置的。
         * - 该方法又标注了 @ConfigurationProperties(prefix = "spring.datasource.hikari")，
         *      故会在创建好 HikariDataSource 实例之后之后，
         *      再将 prefix = "spring.datasource.hikari" 中的相关配置信息 设置到 HikariDataSource 实例上，
         *      即：此时 "data-source-properties" -> HikariDataSource#setDataSourceProperties
         */
    }

    /**
     * FIXME: 如何在 spring boot 项目中配置多个 dataSource?
     *
     * @see <a href="https://www.baeldung.com/spring-boot-configure-multiple-datasources">Configure and Use Multiple DataSources in Spring Boot</a>
     */
    public void springBootMultipleDataSource01() {

        /*
        1. application.properties 中 指定多个 DataSourceProperties 配置信息，示例：
            my.datasources.aaa.url=...
            my.datasources.aaa.username=...
            my.datasources.aaa.password=...
            my.datasources.aaa.driverClassName=...
            my.datasources.bbb.url=...
            my.datasources.bbb.username=...
            my.datasources.bbb.password=...
            my.datasources.bbb.driverClassName=...
         2. 参考下面的 SpringBootMultipleDataSourceConfiguration 进行配置
         */

    }

    @Configuration
    public static class SpringBootMultipleDataSourceConfiguration {

        @Bean
        @ConfigurationProperties("spring.datasource.aaa")
        public DataSourceProperties aaaDataSourceProperties() {
            return new DataSourceProperties();
        }

        @Bean
        @ConfigurationProperties("spring.datasource.bbb")
        public DataSourceProperties bbbDataSourceProperties() {
            return new DataSourceProperties();
        }

        @Bean
        public DataSource aaaDataSource() {
            return aaaDataSourceProperties()
                    .initializeDataSourceBuilder()
                    .build();
        }

        @Bean
        public DataSource bbbDataSource() {
            return bbbDataSourceProperties()
                    .initializeDataSourceBuilder()
                    .build();
        }
    }
}
