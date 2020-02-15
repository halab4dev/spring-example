package com.github.halab4dev.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.halab4dev.constant.Role;
import lombok.AllArgsConstructor;
import lombok.Data;

/*
 *
 * @author halab
 */
@Data
@AllArgsConstructor
public class User {

    private int id;
    @JsonIgnore
    private Role role;
    private String username;
    @JsonIgnore
    private String password;
    private int age;
}
