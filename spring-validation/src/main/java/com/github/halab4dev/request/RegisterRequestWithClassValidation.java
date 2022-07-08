package com.github.halab4dev.request;

import com.github.halab4dev.constant.Constant;
import com.github.halab4dev.constant.Gender;
import com.github.halab4dev.validation.NtqEmail;
import com.github.halab4dev.validation.RegisterData;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/*
 *
 * @author halab
 */
@Data
@RegisterData
public class RegisterRequestWithClassValidation {

    @NtqEmail(message = "Email must be like ***@ntq-solution.com.vn")
    private String email;

    @NotBlank(message = "Password can not be blank")
    @Size(min = Constant.MIN_PASSWORD_LENGTH,
            message = "Password length must be greater or equal " + Constant.MIN_PASSWORD_LENGTH)
    private String password;

    @NotNull(message = "Gender can not be null")
    private Gender gender;

    private Integer age;
}
