package com.github.btpka3.first.springdoc.stub;

import com.github.btpka3.first.springdoc.api.UserApi;
import com.github.btpka3.first.springdoc.model.User;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserApiStub implements UserApi {

    @Override
    public List<User> list(int page, int size) {
        throw new UnsupportedOperationException();
    }

    @Override
    public User getById(Long id) {
        throw new UnsupportedOperationException();
    }

    @Override
    public User create(User user) {
        throw new UnsupportedOperationException();
    }

    @Override
    public User update(Long id, User user) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void delete(Long id) {
        throw new UnsupportedOperationException();
    }
}
