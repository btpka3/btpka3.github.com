package me.test.first.spring.jdo;

import java.util.List;
import java.util.Map;

import me.test.first.spring.jdo.entity.User;

public interface UserDao {

    List<User> list();

    User select(long uid);

    void insert(User user);

    void update(Map<String, Object> userUpdateMap);

    void delete(long uid);

}
