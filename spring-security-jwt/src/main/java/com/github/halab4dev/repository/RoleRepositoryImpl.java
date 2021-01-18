package com.github.halab4dev.repository;

import com.github.halab4dev.constant.Privilege;
import com.github.halab4dev.constant.Role;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/*
 *
 * @author halab
 */
@Repository
public class RoleRepositoryImpl implements RoleRepository {

    private static final Map<String, Role> ROLES = new ConcurrentHashMap<>();

    static {
        ROLES.put("USER", new Role("USER", Collections.singletonList(Privilege.GET_USER_DETAIL)));
        ROLES.put("ADMIN", new Role("ADMIN", Arrays.asList(Privilege.GET_USER_DETAIL, Privilege.SEARCH_USERS)));
        ROLES.put("SUPER_ADMIN", new Role("SUPER_ADMIN", Arrays.asList(Privilege.GET_USER_DETAIL, Privilege.SEARCH_USERS, Privilege.DELETE_USERS)));
    }


    @Override
    public List<Role> find(List<String> rolesName) {
        List<Role> roles = new ArrayList<>();
        rolesName.forEach(roleName -> {
            Role role = ROLES.get(roleName);
            if (role != null) {
                roles.add(role);
            }
        });
        return roles;
    }
}
