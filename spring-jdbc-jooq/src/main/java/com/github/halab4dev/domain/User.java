package com.github.halab4dev.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
public class User extends BaseDomain<String> {

    private String id;
    private String name;
    private List<String> nickNames;
    private List<Role> roles;

    public void addRole(Role role) {
        if (roles  == null) {
            roles = new ArrayList<>();
        }
        roles.add(role);
    }
}
