package me.test;

import static com.datastax.driver.core.querybuilder.QueryBuilder.add;
import static com.datastax.driver.core.querybuilder.QueryBuilder.batch;
import static com.datastax.driver.core.querybuilder.QueryBuilder.bindMarker;
import static com.datastax.driver.core.querybuilder.QueryBuilder.desc;
import static com.datastax.driver.core.querybuilder.QueryBuilder.eq;
import static com.datastax.driver.core.querybuilder.QueryBuilder.prepend;
import static com.datastax.driver.core.querybuilder.QueryBuilder.put;
import static com.datastax.driver.core.querybuilder.QueryBuilder.set;
import static com.datastax.driver.core.querybuilder.QueryBuilder.ttl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.datastax.driver.core.BatchStatement;
import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Host;
import com.datastax.driver.core.Metadata;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.RegularStatement;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.Statement;
import com.datastax.driver.core.policies.ConstantReconnectionPolicy;
import com.datastax.driver.core.policies.DowngradingConsistencyRetryPolicy;
import com.datastax.driver.core.querybuilder.Insert;
import com.datastax.driver.core.querybuilder.QueryBuilder;

public class Test {

    public static void main(String[] args) {
        Cluster cluster = Cluster.builder()
                .addContactPoint("localhost")
                .withPort(9042)
                .withRetryPolicy(DowngradingConsistencyRetryPolicy.INSTANCE)
                .withReconnectionPolicy(new ConstantReconnectionPolicy(100L))
                .withCredentials("cassandra", "cassandra")
                .build();
        Metadata metadata = cluster.getMetadata();
        System.out.printf("Connected to cluster: %s\n",
                metadata.getClusterName());
        for (Host host : metadata.getAllHosts()) {
            System.out.printf("Datacenter: %s; Host: %s; Rack: %s\n",
                    host.getDatacenter(),
                    host.getAddress(),
                    host.getRack());
        }

        Session session = cluster.connect();

        recreateKeyspace(session); // 3.948s, 4.364s
        recreateTable(session); // 3.091s, 4.364s, 3.014s
        insertData0(session); // 5.323s, 5.495s
        // insertData1(session); // 8.034s, 5.698s, 5.77s
        // insertData2(session); // 18.11s
        query(session); // 1.035s, 0.761s

        session.close();
        cluster.close();
    }

    private static void recreateKeyspace(Session session) {
        System.out.println("recreateKeyspace begin");
        long beginTime = System.currentTimeMillis();

        String cql = "drop keyspace if exists test";
        session.execute(cql);

        cql = "create keyspace if not exists test "
                + "with replication = { 'class' : 'SimpleStrategy', 'replication_factor' : 1 } "
                + "and durable_writes = true";
        session.execute(cql);

        cql = "use test";
        session.execute(cql);

        long endTime = System.currentTimeMillis();
        double timeConsume = (endTime - beginTime) / 1000.0;
        System.out.println("recreateKeyspace end : " + timeConsume + "s");
    }

    private static void recreateTable(Session session) {
        System.out.println("recreateTable begin");
        long beginTime = System.currentTimeMillis();

        String cql = "drop table if exists xxx";
        session.execute(cql);

        cql = "create table if not exists xxx ( "
                + "id text,"
                + "sid text,"
                + "name text,"
                + "tags set<text>,"
                + "addrs list<text>,"
                + "extra map<text,text>,"
                + "memo text,"
                + "primary key(id, sid)"
                + ")";
        session.execute(cql);

        cql = "create index on xxx(name)";
        session.execute(cql);

        cql = "create index on xxx(tags)";
        session.execute(cql);

        cql = "create index on xxx(addrs)";
        session.execute(cql);

        cql = "create index on xxx(extra)";
        session.execute(cql);

        long endTime = System.currentTimeMillis();
        double timeConsume = (endTime - beginTime) / 1000.0;
        System.out.println("recreateTable end : " + timeConsume + "s");
    }

    // batch + prepared statement
    private static void insertData0(Session session) {

        System.out.println("insertData0 begin");
        showMemory();
        long beginTime = System.currentTimeMillis();

        Insert insert = QueryBuilder
                .insertInto("xxx")
                // .ifNotExists()
                .value("id", bindMarker())
                .value("sid", bindMarker())
                .value("name", bindMarker())
                .value("tags", bindMarker())
                .value("addrs", bindMarker())
                .value("extra", bindMarker())
                .value("memo", bindMarker());
        PreparedStatement preStmt = session.prepare(insert.toString());
        BatchStatement batchStmt = new BatchStatement();

        // batch loop
        for (int i = 0; i < 1000; i++) {

            // inserts loop in one batch
            for (int j = 0; j < 10; j++) {
                int num = i * 10 + j;

                String id = "0";
                String sid = "sid" + num;
                String name = "name" + num;

                Set<String> tags = new HashSet<String>(2);
                tags.add("tag" + num + ".3");
                tags.add("tag" + num + ".4");

                List<String> addrs = new ArrayList<String>(2);
                addrs.add("addr" + num + ".3");
                addrs.add("addr" + num + ".4");

                Map<String, String> extra = new HashMap<String, String>(2);
                extra.put("key" + num + ".3", "value" + num + ".3");
                extra.put("key" + num + ".4", "value" + num + ".4");

                String memo = "memo" + num;

                BoundStatement boundStmt = preStmt.bind(id, sid, name, tags, addrs, extra, memo);
                batchStmt.add(boundStmt);
            }
            session.execute(batchStmt);
            batchStmt.clear();
        }
        long endTime = System.currentTimeMillis();
        double timeConsume = (endTime - beginTime) / 1000.0;
        showMemory();
        System.out.println("insertData0 end : " + timeConsume + "s");
    }

    // batch
    private static void insertData1(Session session) {

        System.out.println("insertData1 begin");
        showMemory();
        long beginTime = System.currentTimeMillis();

        // batch loop
        for (int i = 0; i < 1000; i++) {

            // inserts loop in one batch
            List<RegularStatement> stmtList = new ArrayList<RegularStatement>();
            RegularStatement[] arr = new RegularStatement[0];
            for (int j = 0; j < 10; j++) {
                int num = i * 10 + j;

                String id = "0";
                String sid = "sid" + num;
                String name = "name" + num;

                Set<String> tags = new HashSet<String>(2);
                tags.add("tag" + num + ".3");
                tags.add("tag" + num + ".4");

                List<String> addrs = new ArrayList<String>(2);
                addrs.add("addr" + num + ".3");
                addrs.add("addr" + num + ".4");

                Map<String, String> extra = new HashMap<String, String>(2);
                extra.put("key" + num + ".3", "value" + num + ".3");
                extra.put("key" + num + ".4", "value" + num + ".4");

                String memo = "memo" + num;

                Insert insert = QueryBuilder.insertInto("xxx")
                        .value("id", id)
                        .value("sid", sid)
                        .value("name", name)
                        .value("tags", tags)
                        .value("addrs", addrs)
                        .value("extra", extra)
                        .value("memo", memo);
                stmtList.add(insert);
            }
            session.execute(batch(stmtList.toArray(arr)));
        }
        long endTime = System.currentTimeMillis();
        double timeConsume = (endTime - beginTime) / 1000.0;
        showMemory();
        System.out.println("insertData1 end : " + timeConsume + "s");
    }

    // prepared statement
    private static void insertData2(Session session) {

        System.out.println("insertData2 begin");
        showMemory();
        long beginTime = System.currentTimeMillis();

        // String cql = "insert into xxx ("
        // + "id,"
        // + "sid,"
        // + "name,"
        // + "tags,"
        // + "addrs,"
        // + "extra,"
        // + "memo"
        // + ") values ( ?,?,?,?,?,?,?)";

        Insert insert = QueryBuilder
                .insertInto("xxx")
                .value("id", bindMarker())
                .value("sid", bindMarker())
                .value("name", bindMarker())
                .value("tags", bindMarker())
                .value("addrs", bindMarker())
                .value("extra", bindMarker())
                .value("memo", bindMarker());
        PreparedStatement preStmt = session.prepare(insert.toString());

        for (int i = 0; i < 10000; i++) {
            String id = "0";
            String sid = "sid" + i;
            String name = "name" + i;

            Set<String> tags = new HashSet<String>(2);
            tags.add("tag" + i + ".3");
            tags.add("tag" + i + ".4");

            List<String> addrs = new ArrayList<String>(2);
            addrs.add("addr" + i + ".3");
            addrs.add("addr" + i + ".4");

            Map<String, String> extra = new HashMap<String, String>(2);
            extra.put("key" + i + ".3", "value" + i + ".3");
            extra.put("key" + i + ".4", "value" + i + ".4");

            String memo = "memo" + i;

            BoundStatement boundStmt = preStmt.bind(id, sid, name, tags, addrs, extra, memo);
            session.execute(boundStmt);
        }

        long endTime = System.currentTimeMillis();
        double timeConsume = (endTime - beginTime) / 1000.0;
        showMemory();
        System.out.println("insertData2 end : " + timeConsume + "s");
    }

    private static void query(Session session) {
        System.out.println("query begin");
        showMemory();
        long beginTime = System.currentTimeMillis();

        Statement stmt = QueryBuilder
                .select()
                .all()
                .from("xxx")
                .where(eq("id", "0"))
                .orderBy(desc("sid"))
                // .limit(10)
                .setFetchSize(100);
        ResultSet rs = session.execute(stmt);
        int i = 0;
        while (!rs.isExhausted()) {
            if (i < 5000) {
                rs.one(); // skip first 5000
                i++;
                continue;
            }
            Row row = rs.one();
            System.out.println(i + " : " + row);

            if (i > 5010) {
                break;
            }
            i++;
        }

        long endTime = System.currentTimeMillis();
        double timeConsume = (endTime - beginTime) / 1000.0;
        showMemory();
        System.out.println("query end : " + timeConsume + "s");
    }

    private static void showMemory() {
        Runtime runtime = Runtime.getRuntime();
        long maxMemory = runtime.maxMemory();
        long allocatedMemory = runtime.totalMemory();
        long freeMemory = runtime.freeMemory();

        StringBuilder sb = new StringBuilder();
        sb.append("jvm memory (M) : max/allocated/free = "
                + (maxMemory / 1024 / 1024)
                + "/"
                + (allocatedMemory / 1024 / 1024)
                + "/"
                + (freeMemory / 1024 / 1024));
        System.out.println(sb.toString());
    }

    // update demo
    private static void update() {

        QueryBuilder
                .update("xxx")
                .with(set("memo", "memo2"))
                .and(add("tags", "tag1"))
                .and(prepend("addrs", "addr1"))
                .and(put("extra", "key1", "value1"))
                .where(eq("id", "99"))
                .and(eq("sid", ""))
                .onlyIf(eq("memo", "memo1"))
                .using(ttl(10000));
    }
}
