package com.github.btpka3.firstopenapi1st.controller;

import com.github.btpka3.firstopenapi1st.api.UserApi;
import com.github.btpka3.firstopenapi1st.model.CreateUserRequest;
import com.github.btpka3.firstopenapi1st.model.User;
import com.github.btpka3.firstopenapi1st.model.UserListResponse;
import com.github.btpka3.firstopenapi1st.model.UserStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController implements UserApi {

    @Override
    public User createUser(CreateUserRequest createUserRequest) {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public User getUserById(Long userId) {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public UserListResponse listUsers(Integer pageNum, Integer pageSize, UserStatus status) {
        throw new UnsupportedOperationException("Not implemented");
    }
}
