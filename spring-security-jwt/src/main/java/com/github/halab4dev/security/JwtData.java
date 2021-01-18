package com.github.halab4dev.security;

import lombok.Data;

import java.util.List;

/*
 *
 * @author halab
 */
@Data
public class JwtData {

    private Integer userId;
    private List<String> roles;
}
