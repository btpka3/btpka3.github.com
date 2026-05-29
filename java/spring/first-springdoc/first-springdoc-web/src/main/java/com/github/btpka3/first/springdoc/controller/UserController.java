package com.github.btpka3.first.springdoc.controller;

import com.github.btpka3.first.springdoc.api.UserApi;
import com.github.btpka3.first.springdoc.model.User;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@RestController
public class UserController implements UserApi {

    private final Map<Long, User> store = new ConcurrentHashMap<>();
    private final AtomicLong idGen = new AtomicLong(1);

    @Override
    public List<User> list(int page, int size) {
        return new ArrayList<>(store.values()).stream()
                .skip((long) page * size)
                .limit(size)
                .toList();
    }

    @Override
    public User getById(Long id) {
        User user = store.get(id);
        if (user == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "用户不存在");
        }
        return user;
    }

    @Override
    public User create(User user) {
        long id = idGen.getAndIncrement();
        user.setId(id);
        store.put(id, user);
        return user;
    }

    @Override
    public User update(Long id, User user) {
        if (!store.containsKey(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "用户不存在");
        }
        user.setId(id);
        store.put(id, user);
        return user;
    }

    @Override
    public void delete(Long id) {
        if (store.remove(id) == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "用户不存在");
        }
    }
}
