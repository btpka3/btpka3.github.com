package me.test.spring.data;

import static com.datastax.driver.core.querybuilder.QueryBuilder.desc;
import static com.datastax.driver.core.querybuilder.QueryBuilder.eq;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import me.test.spring.data.domain.User;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.cassandra.core.CassandraOperations;

import com.datastax.driver.core.querybuilder.QueryBuilder;
import com.datastax.driver.core.querybuilder.Select;

public class TestSpringData {

    public static void main(String[] args) {

        ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
        CassandraOperations cassandraOps = ctx.getBean("cassandraTemplate", CassandraOperations.class);

        // insert
        int num = 0;
        Set<String> tags = new HashSet<String>(2);
        tags.add("tag" + num + ".3");
        tags.add("tag" + num + ".4");

        List<String> addrs = new ArrayList<String>(2);
        addrs.add("addr" + num + ".3");
        addrs.add("addr" + num + ".4");

        Map<String, String> extra = new HashMap<String, String>(2);
        extra.put("key" + num + ".3", "value" + num + ".3");
        extra.put("key" + num + ".4", "value" + num + ".4");

        User u1 = new User();
        final User.Pk pk = new User.Pk("1", "sid0");
        u1.setPk(pk);
        u1.setTags(tags);
        u1.setAddrs(addrs);
        u1.setExtra(extra);
        u1.setMemo("memo" + num);

        cassandraOps.insert(u1);
        System.out.println("insert : " + u1);

        // update
        User u2 = new User();
        u2.setPk(pk);
        u2.setMemo("memo0_111");
        cassandraOps.update(u2);
        System.out.println("update : " + u2);

        // query
        Select select = QueryBuilder
                .select()
                .all()
                .from("xxx")
                .where(eq("id", "1"))
                .orderBy(desc("sid"))
                .limit(1);

        User u3 = cassandraOps.selectOne(select, User.class);
        System.out.println("select : " + u3);

        // delete
        cassandraOps.deleteById(User.class, pk);
        System.out.println("delete : " + pk);

        cassandraOps.getSession().getCluster().close();
        ctx.close();
    }

}
