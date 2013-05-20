package me.test.first.spring.jdo.dsl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Query;

import me.test.first.spring.jdo.UserDao;
import me.test.first.spring.jdo.entity.QUser;
import me.test.first.spring.jdo.entity.User;
import me.test.first.spring.jdo.entity.UserGroup;

import com.mysema.query.jdo.JDOQLQuery;
import com.mysema.query.jdo.JDOQLQueryImpl;
import com.mysema.query.jdo.dml.JDOQLDeleteClause;
import com.mysema.query.jdo.dml.JDOQLUpdateClause;

public class UserDaoImpl implements UserDao {

    private PersistenceManagerFactory pmFactory;

    @SuppressWarnings("unchecked")
    public List<User> list() {
        PersistenceManager pm = this.pmFactory.getPersistenceManager();
        try {
            Query query = pm.newQuery(User.class, "id > query_id ");
            query.declareParameters("Long query_id");
            return (List<User>) query.execute(1);
        } finally {
            pm.close();
        }
    }

    public void insert(User person) {
        PersistenceManager pm = this.pmFactory.getPersistenceManager();
        try {
            pm.makePersistent(person);
        } finally {
            pm.close();
        }
    }

    public User select(String id) {
        PersistenceManager pm = this.pmFactory.getPersistenceManager();
        try {
            pm.getFetchPlan().addGroup("imgData");
            return pm.getObjectById(User.class, Long.valueOf(id));
        } finally {
            pm.close();
        }
    }

    public void update(Map<String, Object> person) {
        PersistenceManager pm = this.pmFactory.getPersistenceManager();
        try {
            User p = pm.getObjectById(User.class, person.get("id"));
            if (person.containsKey("name")) {
                p.setName((String) person.get("name"));
            }
            if (person.containsKey("age")) {
                p.setAge((Integer) person.get("age"));
            }
            if (person.containsKey("male")) {
                p.setMale((Boolean) person.get("male"));
            }
            if (person.containsKey("birthday")) {
                p.setBirthday((Date) person.get("birthday"));
            }
            if (person.containsKey("imgData")) {
                p.setImgData((byte[]) person.get("imgData"));
            }
        } finally {
            pm.close();
        }
    }

    public void delete(long id) {
        PersistenceManager pm = this.pmFactory.getPersistenceManager();
        try {
            User p = pm.getObjectById(User.class, id);
            pm.deletePersistent(p);
        } finally {
            pm.close();
        }
    }

    public List<User> dslQuery() {
        PersistenceManager pm = this.pmFactory.getPersistenceManager();
        JDOQLQuery query = new JDOQLQueryImpl(pm);
        try {
            QUser person = QUser.user;
            List<User> list = query.from(person)
                    .where(person.age.goe(22), person.name.startsWith("zhao"))
                    .list(person);
            return new ArrayList<User>(list);
        } finally {
            query.close();
            pm.close();
        }

        JDOQLDeleteClause c = new JDOQLDeleteClause();
        QUser person = QUser.user;
        c.where(person.age.goe(22)).execute();

        JDOQLUpdateClause d = new JDOQLUpdateClause();
        d.where(o)
    }

    public void setPersistenceManagerFactory(PersistenceManagerFactory pmf) {
        this.pmFactory = pmf;
    }

    public void destroy() {
        System.out.println("dddddddddddddddddddddddddddddddd");
    }

    public User select(long uid) {
        // TODO Auto-generated method stub
        return null;
    }

    public List<UserGroup> getUserGroups(long uid) {
        // TODO Auto-generated method stub
        return null;
    }

    public void setUserGroups(long uid, long... gids) {
        // TODO Auto-generated method stub

    }

    public void addUserGroup(long uid, long gid) {
        // TODO Auto-generated method stub

    }

    public void removeUserGroup(long uid, long gid) {
        // TODO Auto-generated method stub

    }
}
