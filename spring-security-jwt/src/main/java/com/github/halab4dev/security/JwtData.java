package com.github.halab4dev.security;

import com.github.halab4dev.constant.Role;
import lombok.Data;

/*
 *
 * @author halab
 */
@Data
public class JwtData {

    private Integer userId;
    private Role role;
}
