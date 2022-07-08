package com.github.halab4dev.request;

import com.github.halab4dev.constant.Gender;
import lombok.Data;

/*
 *
 * @author halab
 */
@Data
public class RegisterUserRequest {

    private String email;
    private String password;
    private Gender gender;
    private Integer age;
}
