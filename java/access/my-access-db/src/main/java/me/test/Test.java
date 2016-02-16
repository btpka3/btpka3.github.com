package me.test;

import com.healthmarketscience.jackcess.CryptCodecProvider;
import com.healthmarketscience.jackcess.Database;
import com.healthmarketscience.jackcess.DatabaseBuilder;
import net.ucanaccess.jdbc.JackcessOpenerInterface;

import java.io.File;
import java.io.IOException;
import java.sql.*;

/**
 * 测试Linux下JDK8访问access 数据库。
 */
public class Test {

    public static void main(String[] args) throws ClassNotFoundException, SQLException {

        String dbFile = "/home/zll/work/git-repo/github/btpka3/btpka3.github.com/java/access/my-access-db/src/main/resources/test.accdb";
        Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
        Connection conn = DriverManager.getConnection("jdbc:ucanaccess://"
                + dbFile
                + ";jackcessOpener=me.test.Test$CryptCodecOpener", null, "test");

        // list tables
        DatabaseMetaData md = conn.getMetaData();
        ResultSet rs = md.getTables(null, null, "%", null);

        System.out.println("There tables : ");

        int i = 0;
        String firstTable = null;
        while (rs.next()) {
            if (i == 0) {
                firstTable = rs.getString(3);
            }
            System.out.printf("%5d: %s%n", i, rs.getString(3));
            i++;
        }
        rs.close();

        System.out.printf("");
        System.out.printf("first 3 row in table %s :%n", firstTable);

        PreparedStatement ps = conn.prepareStatement("select * from " + firstTable);

        // print collumn names
        rs = ps.executeQuery();
        ResultSetMetaData rsm = rs.getMetaData();
        System.out.print("     : ");
        for (i = 1; i <= rsm.getColumnCount(); i++) {
            System.out.print(rsm.getColumnLabel(i));
            System.out.print(", ");
        }
        System.out.println();

        // print rows
        i = 0;
        while (rs.next()) {
            System.out.printf("%5d: ", i);

            int j = 0;
            for (j = 1; j <= rsm.getColumnCount(); j++) {
                System.out.print(rs.getObject(j));
                System.out.print(", ");
            }
            System.out.println();

            i++;
            if (i >= 10) {
                break;
            }
        }

        rs.close();
        conn.close();
    }

    public static class CryptCodecOpener implements JackcessOpenerInterface {
        public Database open(File fl, String pwd) throws IOException {
            DatabaseBuilder dbd = new DatabaseBuilder(fl);
            dbd.setAutoSync(false);
            dbd.setCodecProvider(new CryptCodecProvider(pwd));
            dbd.setReadOnly(false);
            return dbd.open();

        }
        //Notice that the parameter setting autosync =true is recommended with UCanAccess for performance reasons.
        //UCanAccess flushes the updates to disk at transaction end.
        //For more details about autosync parameter (and related tradeoff), see the Jackcess documentation.
    }

}
