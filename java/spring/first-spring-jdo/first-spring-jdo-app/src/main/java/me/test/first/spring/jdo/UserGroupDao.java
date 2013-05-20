package me.test.first.spring.jdo;

import java.util.List;
import java.util.Map;

import me.test.first.spring.jdo.entity.User;
import me.test.first.spring.jdo.entity.UserGroup;

public interface UserGroupDao {

    List<User> list();

    User select(UserGroup id);

    void insert(UserGroup userGroup);

    void update(Map<String, Object> userGroup);

    void delete(long gid);

}
