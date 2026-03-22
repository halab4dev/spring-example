package com.github.halab4dev.repository.user;

import com.github.halab4dev.domain.User;

import java.util.List;

public interface UserRepository {

    void insert(User user);

    List<User> findAll();

    User findByIdWithRoles(String id);

    void update(User user);

    void deleteAll();

    void delete(User user);
}
