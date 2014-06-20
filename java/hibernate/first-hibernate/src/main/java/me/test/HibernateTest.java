package me.test;

import static org.hibernate.criterion.Order.asc;
import static org.hibernate.criterion.Restrictions.ge;

import java.util.List;
import java.util.Random;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import me.test.domain.User;

import org.hibernate.ScrollMode;
import org.hibernate.ScrollableResults;
import org.hibernate.Session;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class HibernateTest {
    private static Random random = new Random(System.currentTimeMillis());

    public static void main(String[] args) {
        ClassPathXmlApplicationContext appCtx = new ClassPathXmlApplicationContext("applicationContext.xml");
        // SessionFactory sessionFactory = appCtx.getBean(SessionFactory.class);
        EntityManagerFactory emf = appCtx.getBean(EntityManagerFactory.class);
        EntityManager em = emf.createEntityManager();

        Session session = em.unwrap(Session.class);
        session.beginTransaction();
        if (args.length > 0 && "add".equals(args[0])) {
            int count = 1;
            if (args.length > 1) {
                count = Integer.valueOf(args[1]);
            }
            add(session, count);
        } else if (args.length > 0 && "update".equals(args[0])) {
            update(session);
        } else if (args.length > 0 && "list".equals(args[0])) {
            list(session);
        } else {
            System.err.println("Usage : java me.test.HibernateTest [add|update|list]");
        }
        session.getTransaction().commit();
        appCtx.close();
    }

    public static void add(Session session, int count) {
        for (int i = 0; i < count; i++) {
            User user = new User();
            user.setName("z-" + Math.abs(random.nextLong()));

            session.save(user);
        }
        System.out.println("Added.");
    }

    @SuppressWarnings("unchecked")
    public static void update(Session session) {
        List<User> userList = session.createCriteria(User.class).list();
        for (User user : userList) {
            user.setName("z-" + Math.abs(random.nextLong()));
            session.save(user);
        }
        System.out.println("Updated.");
    }

    // http://docs.jboss.org/hibernate/orm/4.3/manual/en-US/html_single/#querycriteria
    /**
     * 演示如何遍历处理大数据集。
     */
    public static void list(Session session) {

        ScrollableResults userResults = session.createCriteria(User.class)
                .add(ge("id", 5L))
                .addOrder(asc("id"))
                .setFirstResult(1) // = offset
                .setMaxResults(10) // = limit
                .setFetchSize(1)
                .scroll(ScrollMode.FORWARD_ONLY);
        while (userResults.next()) {
            User user = (User) userResults.get(0);
            System.out.println(user);
        }
        System.out.println("List finished.");
    }
}
