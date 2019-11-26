package com.ywf.service;

import com.ywf.domain.User;

import java.util.List;

public interface UserService {
    List<User> listUser();

    User selectUserById(final Integer id);

    void delete(final Integer id);

    void update(final User user);
}
