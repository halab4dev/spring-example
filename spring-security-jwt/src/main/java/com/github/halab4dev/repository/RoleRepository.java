package com.github.halab4dev.repository;

import com.github.halab4dev.constant.Role;

import java.util.List;

/*
 *
 * @author halab
 */
public interface RoleRepository {

    List<Role> find(List<String> rolesName);
}
