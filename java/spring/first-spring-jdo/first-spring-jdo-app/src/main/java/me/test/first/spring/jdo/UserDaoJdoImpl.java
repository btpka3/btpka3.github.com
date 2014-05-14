package me.test.first.spring.jdo;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Query;

import me.test.first.spring.jdo.entity.User;

import org.springframework.transaction.annotation.Transactional;

public class UserDaoJdoImpl implements UserDao {

    private PersistenceManagerFactory pmFactory;

    @SuppressWarnings("unchecked")
    @Transactional(readOnly = true)
    public List<User> list() {
        PersistenceManager pm = this.pmFactory.getPersistenceManager();
        try {
            Query query = pm.newQuery(User.class);
            query.setFilter("id > query_id ");
            query.declareParameters("Long query_id");
            return (List<User>) query.execute(1);
        } finally {
            pm.close();
        }
    }

    @Transactional
    public void insert(User person) {
        PersistenceManager pm = this.pmFactory.getPersistenceManager();
        try {
            pm.makePersistent(person);
        } finally {
            pm.close();
        }
    }

    @Transactional(readOnly = true)
    public User select(long uid) {
        PersistenceManager pm = this.pmFactory.getPersistenceManager();
        try {
            pm.getFetchPlan().addGroup("imgData");
            return pm.getObjectById(User.class, Long.valueOf(uid));
        } finally {
            pm.close();
        }
    }

    @Transactional
    public void update(Map<String, Object> userUpdateMap) {
        PersistenceManager pm = this.pmFactory.getPersistenceManager();
        try {
            // 先查询出来，才能更新
            User user = pm.getObjectById(User.class, userUpdateMap.get("id"));
            if (userUpdateMap.containsKey("name")) {
                user.setName((String) userUpdateMap.get("name"));
            }
            if (userUpdateMap.containsKey("age")) {
                user.setAge((Integer) userUpdateMap.get("age"));
            }
            if (userUpdateMap.containsKey("male")) {
                user.setMale((Boolean) userUpdateMap.get("male"));
            }
            if (userUpdateMap.containsKey("imgData")) {
                user.setImgData((byte[]) userUpdateMap.get("imgData"));
            }
            if (userUpdateMap.containsKey("birthday")) {
                user.setBirthday((Date) userUpdateMap.get("birthday"));
            }
            // version 字段应当会自动更新

            pm.makePersistent(user);
        } finally {
            pm.close();
        }
    }

    @Transactional
    public void delete(long uid) {
        PersistenceManager pm = this.pmFactory.getPersistenceManager();
        try {
            User p = pm.getObjectById(User.class, uid);
            pm.deletePersistent(p);
        } finally {
            pm.close();
        }
    }

    public void setPersistenceManagerFactory(PersistenceManagerFactory pmf) {
        this.pmFactory = pmf;
    }
}
