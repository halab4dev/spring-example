package com.github.halab4dev.repository;

import com.github.halab4dev.domain.User;

import java.util.List;
import java.util.Optional;

/*
 *
 * @author halab
 */
public interface UserRepository {

    List<User> findAll();

    User findById(Integer id);

    Optional<User> findByUsernameAndPassword(String username, String password);
}
