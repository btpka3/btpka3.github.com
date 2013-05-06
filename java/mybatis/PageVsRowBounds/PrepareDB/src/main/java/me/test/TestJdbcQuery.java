package me.test;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;

public class TestJdbcQuery {

    public static void main(String[] args) throws ClassNotFoundException, SQLException {

        String driverClassName = null;
        String dbUrl = null;
        String dbUser = null;
        String dbPassword = null;
        int count = 1000;

        if (args.length > 0 && args[0].equalsIgnoreCase("MySql")) {
            driverClassName = "com.mysql.jdbc.Driver";
            dbUrl = "jdbc:mysql://localhost:3306/test";
            dbUser = "root";
            dbPassword = "123456";
        } else if (args.length > 0 && args[0].equalsIgnoreCase("PostgreSql")) {
            driverClassName = "org.postgresql.Driver";
            dbUrl = "jdbc:postgresql://localhost:5432/postgres";
            dbUser = "postgres";
            dbPassword = "postgres";
        } else {
            System.out.println("Usage : java me.test.TestJdbcQuery mysql|postgre [count].");
            return;
        }
        if (args.length > 1) {
            try {
                count = Integer.valueOf(args[1]);
            } catch (NumberFormatException e) {
                System.err.println("Could not pars number, using default[" + count + "]");
            }
        }

        Class.forName(driverClassName);
        Connection conn = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
        conn.setAutoCommit(false);
        PreparedStatement preStmt = conn.prepareStatement("SELECT * FROM PERSON WHERE ID > 100",
                ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

        System.out.println("=================== Starting jdbc query data for " + args[0] + ".");

        Runtime rt = Runtime.getRuntime();
        long total = rt.totalMemory();
        long free = rt.freeMemory();
        long used1 = total - free;
        long beginTime = System.currentTimeMillis();
        System.out.println("Start millisecond : " + beginTime);
        System.out.println("Start memroy : " + used1 + "/" + total);

        ResultSet rs = preStmt.executeQuery();
        int fetchSize = rs.getFetchSize();

        rs.absolute(100);
        int i = 0;
        for (; i < count && rs.next(); i++) {

        }
        if (i != count) {
            throw new RuntimeException("expected i=100, but actually was " + i);
        }

        long endTime = System.currentTimeMillis();
        total = rt.totalMemory();
        free = rt.freeMemory();
        long used2 = total - free;
        System.out.println("=================== Done. ");
        System.out.println("End millisecond : " + endTime);
        System.out.println("End memroy : " + used2 + "/" + total);
        System.out.println("Cost time : " + (endTime - beginTime));
        System.out.println("Added memory : " + (used2 - used1));
        System.out.println("fetchSize : " + fetchSize);

        preStmt.close();
        conn.commit();
        conn.close();
    }
}
