package io.github.btpka3.first.aliyun.sls;

import lombok.SneakyThrows;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.junit.jupiter.api.Test;

import java.sql.*;

/**
 * @author dangqian.zll
 * @date 2023/12/22
 */
public class SlsJdbcTest {

    @SneakyThrows
    @Test
    public void test() {

        AkSkConf akSkConf = AliyunUtUtils.getConfig("AkSkConf.more_engine.slsRead.json");
        String ak = akSkConf.getAccessKeyId();
        String sk = akSkConf.getAccessKeySecret();

        final String endpoint = "cn-zhangjiakou-2-share.log.aliyuncs.com";
        final String port = "10005";
        // 日志服务Project名称。
        final String project = "mtee3log";
        // 日志服务Logstore名称。
        final String logstore = "mtee3-exception-analyse";
        // 本示例从环境变量中获取AccessKey ID和AccessKey Secret。
        Connection conn = null;
        Statement stmt = null;


        String now = DateFormatUtils.format(System.currentTimeMillis(), "yyyy-MM-dd HH:mm:ss");
        String lastHour = DateFormatUtils.format(System.currentTimeMillis() - (60 * 60 * 1000), "yyyy-MM-dd HH:mm:ss");

        try {
            // 步骤1 ：加载JDBC驱动。
            Class.forName("com.mysql.cj.jdbc.Driver");
            // 步骤2 ：创建一个链接。
            conn = DriverManager.getConnection("jdbc:mysql://" + endpoint + ":" + port + "/" + project, ak, sk);
            // 步骤3 ：创建statement。
            stmt = conn.createStatement();
            // 步骤4 ：定义查询语句
            String sql = "select count(*) as c from " + logstore + "  " +
                    "where __date__  >=  '" + lastHour + "'   " +
                    " and  __date__  <   '" + now + "'" +
                    " and     exceptionCode =301";
            System.out.println("sql = " + sql);
            // 步骤5 ：执行查询条件。
            ResultSet rs = stmt.executeQuery(sql);
            // 步骤6 ：提取查询结果。
            while (rs.next()) {
                System.out.println("c:" + rs.getLong("c"));
            }
            System.out.println("Done.");
            rs.close();
        } finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
