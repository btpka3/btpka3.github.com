package me.test.jdk.java.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JdbcSample {

    public static void main(String[] args) {
        Connection conn = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost/dbName",
                    "jack", "password");
            conn.setAutoCommit(false);
            PreparedStatement preStmt = conn
                    .prepareStatement("select * from person where key = ?");
            preStmt.setString(1, "aaa");
            ResultSet rs = preStmt.executeQuery();
            while (rs.next()) {
                // Person p = new Person();
                // p.setIdCardNo(rs.getString(0));
                // p.setAge(rs.getString(1));
                // ...
            }
            rs.close();
            preStmt.close();
            conn.commit(); // conn.rollback();
            conn.close();
        } catch (Exception e) {
            try {
                conn.rollback();
            } catch (SQLException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        }finally{
            try {
                conn.close();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
}
