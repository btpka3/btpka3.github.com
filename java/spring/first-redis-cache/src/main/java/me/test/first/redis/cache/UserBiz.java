
package me.test.first.redis.cache;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jdbc.query.SqlUpdateCallback;
import org.springframework.stereotype.Service;

import com.mysema.query.sql.dml.SQLUpdateClause;
import com.mysema.query.types.Predicate;

@Service
public class UserBiz {

    private final Logger logger = LoggerFactory.getLogger(UserBiz.class);

    @Autowired
    private QueryDslJdbcTemplate template;

    public List<User> list(
            String name,
            Boolean gender) {

        List<Predicate> conds = new ArrayList<Predicate>();
        if (StringUtils.isNotEmpty(name)) {
            conds.add(QUser.user.name.like("%" + StringUtils.trimToEmpty(name) + "%"));
        }
        if (gender != null) {
            conds.add(QUser.user.gender.eq(gender));
        }

        return template.newSqlQuery().from(QUser.user).where(conds.toArray(new Predicate[0])).list(QUser.user);
    }

    @Cacheable(value = "user", key = "#id")
    public User selectById(Long id) {

        logger.debug("$$$$$$$$$$$$$$$$$$$$$$$ selectById()");

        return template.newSqlQuery().from(QUser.user).where(QUser.user.id.eq(id)).uniqueResult(QUser.user);
    }

    @CacheEvict(value = "user", key = "#user.id")
    public void update(final User user) {

        logger.debug("$$$$$$$$$$$$$$$$$$$$$$$ update(User)");

        template.update(QUser.user, new SqlUpdateCallback() {

            @Override
            public long doInSqlUpdateClause(SQLUpdateClause update) {

                return update.populate(user).where(QUser.user.id.eq(user.getId())).execute();
            }

        });

    }
}
