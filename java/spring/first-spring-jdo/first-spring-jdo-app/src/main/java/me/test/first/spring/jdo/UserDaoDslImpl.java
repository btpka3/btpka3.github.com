package me.test.first.spring.jdo;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;

import org.springframework.transaction.annotation.Transactional;

import me.test.first.spring.jdo.entity.QUser;
import me.test.first.spring.jdo.entity.User;

import com.mysema.query.jdo.JDOQLQuery;
import com.mysema.query.jdo.JDOQuery;
import com.mysema.query.jdo.dml.JDOUpdateClause;

public class UserDaoDslImpl implements UserDao {

    private PersistenceManagerFactory pmFactory;

    @Transactional(readOnly = true)
    public List<User> list() {
        final PersistenceManager pm = this.pmFactory.getPersistenceManager();
        final JDOQLQuery query = new JDOQuery(pm);

        try {
            final QUser qUser = QUser.user;
            return query
                    .from(qUser)
                    .where(qUser.id.gt(1L))
                    .list(qUser);
        } finally {
            query.close();
            pm.close();
        }
    }

    @Transactional
    public void insert(final User person) {
        final PersistenceManager pm = this.pmFactory.getPersistenceManager();
        try {
            pm.makePersistent(person);
        } finally {
            pm.close();
        }
    }

    @Transactional(readOnly = true)
    public User select(final Long id) {
        final PersistenceManager pm = this.pmFactory.getPersistenceManager();
        final JDOQLQuery query = new JDOQuery(pm);
        try {
            final QUser qUser = QUser.user;
            return query
                    .from(qUser)
                    .where(qUser.id.eq(id))
                    .singleResult(qUser);

        } finally {
            query.close();
        }
    }

    @Transactional
    public void update(final Map<String, Object> person) {
        final PersistenceManager pm = this.pmFactory.getPersistenceManager();
        final QUser qUser = QUser.user;
        JDOUpdateClause updateClause = new JDOUpdateClause();

        try {
            final User p = pm.getObjectById(User.class, person.get("id"));
            if (person.containsKey("name")) {
                updateClause.set(qUser.name, (String) person.get("name"));
            }
            if (person.containsKey("age")) {
                updateClause.set(qUser.age, (Integer) person.get("age"));
            }
            if (person.containsKey("male")) {
                updateClause.set(qUser.male, (Boolean) person.get("male"));
            }
            if (person.containsKey("birthday")) {
                updateClause.set(qUser.birthday, (Date) person.get("birthday"));
            }
            if (person.containsKey("imgData")) {
                updateClause.set(qUser.imgData, (byte[]) person.get("imgData"));
                p.setImgData((byte[]) person.get("imgData"));
            }
            updateClause.where(qUser.id.eq((Long) person.get("id")));
            updateClause.execute();
        } finally {
            pm.close();
        }
    }

    @Transactional
    public void delete(final long id) {
        final PersistenceManager pm = this.pmFactory.getPersistenceManager();
        try {
            final User p = pm.getObjectById(User.class, id);
            pm.deletePersistent(p);
        } finally {
            pm.close();
        }
    }

    public void setPersistenceManagerFactory(final PersistenceManagerFactory pmf) {
        this.pmFactory = pmf;
    }

    public void destroy() {
        System.out.println("dddddddddddddddddddddddddddddddd");
    }

    public User select(final long uid) {
        // TODO Auto-generated method stub
        return null;
    }

}
