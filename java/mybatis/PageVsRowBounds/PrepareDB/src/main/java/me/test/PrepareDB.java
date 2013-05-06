package me.test;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;

public class PrepareDB {

    public static void main(String[] args) throws ClassNotFoundException, SQLException {

        String driverClassName = null;
        String dbUrl = null;
        String dbUser = null;
        String dbPassword = null;
        int count = 100000;

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
            System.out.println("Usage : java me.test.PerpareDB mysql|postgre [count].");
            return;
        }
        if (args.length > 1) {
            try {
                count = Integer.valueOf(args[1]);
            } catch (NumberFormatException e) {
                System.err.println("Could not pars number, using default[" + count + "]");
            }
        }

        System.out.println("Starting prepare data for " + args[0] + ".");

        Class.forName(driverClassName);
        Connection conn = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
        conn.setAutoCommit(false);

        Calendar c = Calendar.getInstance();
        c.set(1985, 7, 1, 0, 0, 0);
        c.set(Calendar.MILLISECOND, 0);
        Date birthday = new Date(c.getTimeInMillis());
        PreparedStatement preStmt = conn.prepareStatement("INSERT INTO PERSON (ID, NAME, BIRTYDAY) VALUES (?,?,?)");

        for (int i = 0; i < count; i++) {
            preStmt.setLong(1, i);
            preStmt.setString(2, "zhang3");
            preStmt.setDate(3, birthday);
            preStmt.execute();
        }

        preStmt.close();
        conn.commit();
        conn.close();

        System.out.println("Done.");
    }
}
