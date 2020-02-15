package com.github.halab4dev.request;

import lombok.Data;
import lombok.NoArgsConstructor;

/*
 *
 * @author halab
 */
@Data
@NoArgsConstructor
public class LoginRequest {

    private String username;
    private String password;
}
