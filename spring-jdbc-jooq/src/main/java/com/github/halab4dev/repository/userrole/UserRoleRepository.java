package com.github.halab4dev.repository.userrole;

import com.github.halab4dev.domain.User;

public interface UserRoleRepository {

    void saveUserRole(User user);

    void deleteAll();
}
