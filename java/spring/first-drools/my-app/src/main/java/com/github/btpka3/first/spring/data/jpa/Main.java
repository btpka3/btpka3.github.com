package com.github.btpka3.first.spring.data.jpa;

import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.SQLDialect;
import org.jooq.conf.Settings;
import org.jooq.impl.DSL;
import test.generated.tables.records.UfDailyRiskRecord;

import java.sql.Connection;
import java.sql.DriverManager;

import static test.generated.Tables.UF_DAILY_RISK;

public class Main {
    public static void main(String[] args) {
//        String userName = "root";
//        String password = "123456";
//        String url = "jdbc:mysql://localhost:3306/library";

        String userName = "SMETA_APP";
        String password = "123456";
        String url = "jdbc:mysql://tddl.daily.alibaba.net:3306";
        Settings settings = new Settings()
                .withRenderSchema(false)
                .withRenderCatalog(false);
        // Connection is the only JDBC resource that we need
        // PreparedStatement and ResultSet are handled by jOOQ, internally

        try (Connection conn = DriverManager.getConnection(url, userName, password)) {
            DSLContext create;
            create = DSL.using(conn, SQLDialect.MYSQL, settings);
            Result<Record> result = create
                    .select()
                    .from(UF_DAILY_RISK)
                    .where(UF_DAILY_RISK.TENANT_CODE.eq("ali_taobao"))
                    .fetch();
            for (Record r : result) {
                UfDailyRiskRecord rec = (UfDailyRiskRecord) r;
                Number id = r.getValue(UF_DAILY_RISK.ID);
                java.util.Date a = r.getValue(UF_DAILY_RISK.GMT_CREATE);
                String b = r.getValue(UF_DAILY_RISK.TENANT_CODE);

                System.out.println("ID: " + id
                        + " a: " + rec.getId()
                        + " b: " + b
                        + ", " + r.getClass()
                        + " :\n" + rec);
            }


        }

        // For the sake of this tutorial, let's keep exception handling simple
        catch (Exception e) {
            e.printStackTrace();
        }
    }


}