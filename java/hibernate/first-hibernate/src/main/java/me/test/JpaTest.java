package me.test;

import java.util.List;
import java.util.Random;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;

import me.test.domain.User;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class JpaTest {
    private static Random random = new Random(System.currentTimeMillis());

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

    public static void add(EntityManagerFactory emf, int count) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction entityTransaction = em.getTransaction();
        entityTransaction.begin();
        for (int i = 0; i < count; i++) {
            User user = new User();
            user.setName("z-" + Math.abs(random.nextLong()));

            em.persist(user);
        }
        entityTransaction.commit();
        em.close();
        System.out.println("Added.");
    }

    public static void update(EntityManagerFactory emf) {
        EntityManager em = emf.createEntityManager();
        CriteriaBuilder cb = emf.getCriteriaBuilder();
        CriteriaQuery<User> cq = cb.createQuery(User.class);
        cq.from(User.class);
        List<User> userList = em.createQuery(cq).getResultList();

        EntityTransaction entityTransaction = em.getTransaction();
        entityTransaction.begin();
        for (User user : userList) {
            user.setName("z-" + Math.abs(random.nextLong()));
            em.persist(user);
        }
        entityTransaction.commit();
        em.close();
        System.out.println("Updated.");
    }

    public static void list(EntityManagerFactory emf) {

        EntityManager em = emf.createEntityManager();
        CriteriaBuilder cb = emf.getCriteriaBuilder();
        CriteriaQuery<User> cq = cb.createQuery(User.class);

        Root<User> r = cq.from(User.class);
        Path<Long> idPath = r.get("id");
        cq.where(
                cb.ge(idPath, 5L),
                cb.and(cb.ge(idPath, 5L))
                ).orderBy(cb.asc(r.get("id")));

        List<User> userList = em.createQuery(cq)
                .setFirstResult(1) // = offset
                .setMaxResults(10) // = limit
                // .setHint("org.hibernate.fetchSize", 1)
                .getResultList();
        for (User user : userList) {
            System.out.println(user);
        }
        em.close();
        System.out.println("List finished.");
    }
}
