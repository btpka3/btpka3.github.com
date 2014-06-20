package me.test;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import me.test.domain.QUser;
import me.test.domain.User;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.mysema.query.jpa.impl.JPAQuery;

public class JpaAndQueryDslTest extends JpaTest {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext appCtx = new ClassPathXmlApplicationContext("applicationContext.xml");
        EntityManagerFactory emf = appCtx.getBean(EntityManagerFactory.class);

        if (args.length > 0 && "add".equals(args[0])) {
            int count = 1;
            if (args.length > 1) {
                count = Integer.valueOf(args[1]);
            }
            add(emf, count);
        } else if (args.length > 0 && "update".equals(args[0])) {
            update(emf);
        } else if (args.length > 0 && "list".equals(args[0])) {
            list(emf);
        } else {
            System.err.println("Usage : java me.test.HibernateTest [add|update|list]");
        }
        emf.close();
        appCtx.close();
    }

    public static void list(EntityManagerFactory emf) {

        EntityManager em = emf.createEntityManager();
        JPAQuery query = new JPAQuery(em);
        QUser qUser = QUser.user;
        List<User> userList = query.from(qUser)
                .where(
                        qUser.id.goe(5L),
                        qUser.id.goe(5L))
                .orderBy(qUser.id.asc())
                .offset(1)
                .limit(10)
                .list(qUser);

        for (User user : userList) {
            System.out.println(user);
        }
        System.out.println("List finished.");
    }
}
