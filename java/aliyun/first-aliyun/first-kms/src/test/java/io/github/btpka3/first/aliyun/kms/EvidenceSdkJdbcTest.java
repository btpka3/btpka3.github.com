package io.github.btpka3.first.aliyun.kms;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * @author dangqian.zll
 * @date 2025/6/9
 * @see <a href="https://help.aliyun.com/zh/kms/key-management-service/developer-reference/secrets-manager-jdbc">凭据JDBC客户端</a>
 * @see com.aliyun.kms.secretsmanager.MysqlSecretsManagerSimpleDriver MySQL数据库
 * @see com.aliyun.kms.secretsmanager.MariaDBSecretManagerSimpleDriver MariaDB数据库
 * @see com.aliyun.kms.secretsmanager.PostgreSQLSecretManagerSimpleDriver PostgreSQL数据库
 * @see com.aliyun.kms.secretsmanager.MssqlSecretsManagerSimpleDriver SQLServer数据库
 */
public class EvidenceSdkJdbcTest {


    @SneakyThrows
    public void demoJdbc(
            @Value("${your.spring.placeholder.access-key-id}") String secretName

    ) {
        // 加载阿里云凭据JDBC客户端 Load the JDBC Driver com.aliyun.kms.secretsmanager.MysqlSecretsManagerSimpleDriver
        Class.forName("com.aliyun.kms.secretsmanager.MysqlSecretsManagerSimpleDriver");
        String user = secretName;
        String password = "";
        Connection connect = DriverManager.getConnection("secrets-manager:mysql://<your-mysql-ip>:<your-mysql-port>/<your-database-name>", user, password);
    }
}
