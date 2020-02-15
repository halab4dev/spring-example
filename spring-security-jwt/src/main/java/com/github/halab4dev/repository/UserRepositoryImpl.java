package com.github.halab4dev.repository;

import com.github.halab4dev.constant.Role;
import com.github.halab4dev.domain.User;
import com.github.halab4dev.exception.ResourceNotFoundException;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/*
 *
 * @author halab
 */
@Repository
public class UserRepositoryImpl implements UserRepository {

    private static final List<User> USERS = Arrays.asList(
            new User(0, Role.ADMIN, "halab", "123abc", 5),
            new User(1, Role.USER, "tom", "123", 3),
            new User(2, Role.USER, "jerry", "abc", 2)
    );


    @Override
    public List<User> findAll() {
        return USERS;
    }

    @Override
    public User findById(Integer id) {
        return USERS.stream().filter(user -> user.getId() == id).findFirst().orElseThrow(() -> new ResourceNotFoundException());
    }

    @Override
    public Optional<User> findByUsernameAndPassword(String username, String password) {
        return USERS.stream()
                .filter(user -> user.getUsername().equals(username)
                        && user.getPassword().equals(password))
                .findFirst();
    }
}
