package com.github.halab4dev.repository.role;

import com.github.halab4dev.domain.Role;

import java.util.Collection;

public interface RoleRepository {

    void insertMany(Iterable<Role> roles);

    void deleteAll();
}
