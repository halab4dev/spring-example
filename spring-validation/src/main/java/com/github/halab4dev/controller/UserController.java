package com.github.halab4dev.controller;

import com.github.halab4dev.constant.Constant;
import com.github.halab4dev.constant.Gender;
import com.github.halab4dev.request.RegisterRequestWithClassValidation;
import com.github.halab4dev.request.RegisterRequestWithFieldValidation;
import com.github.halab4dev.request.RegisterUserRequest;
import com.github.halab4dev.validation.NtqEmailValidation;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

/*
 *
 * @author halab
 */
@RestController
@RequestMapping("/v1/users")
public class UserController {


    @PostMapping("/withoutSpringValidation")
    public ResponseEntity<String> registerUser(@RequestBody RegisterUserRequest registerUserRequest) {
        String email = registerUserRequest.getEmail();
        String password = registerUserRequest.getPassword();
        Gender gender = registerUserRequest.getGender();
        Integer age = registerUserRequest.getAge();

        if (!NtqEmailValidation.isNtqEmail(email)) {
            return ResponseEntity.badRequest()
                    .body("Email must be like ***@ntq-solution.com.vn");
        }
        if (StringUtils.isEmpty(password)) {
            return ResponseEntity.badRequest()
                    .body("Password can not be blank");
        }
        if (password.length() < Constant.MIN_PASSWORD_LENGTH) {
            return ResponseEntity.badRequest()
                    .body("Password length must be greater or equal " + Constant.MIN_PASSWORD_LENGTH);
        }
        if (Objects.isNull(gender)) {
            return ResponseEntity.badRequest()
                    .body("Gender can not be null");
        }
        if (gender == Gender.MALE && age < Constant.MIN_MALE_AGE) {
            return ResponseEntity.badRequest()
                    .body("Age of male user must be greater than or equal " + Constant.MIN_MALE_AGE);
        }
        if (gender == Gender.FEMALE && age < Constant.MIN_FEMALE_AGE) {
            return ResponseEntity.badRequest()
                    .body("Age of female user must be greater than or equal " + Constant.MIN_FEMALE_AGE);
        }

        //Other logic

        return ResponseEntity.ok("OK");
    }


    @PostMapping("/withFieldValidation")
    public ResponseEntity<String> registerUserWithFieldValidation(
            @RequestBody @Validated RegisterRequestWithFieldValidation registerUserRequest
    ) {
        //Other logic
        return ResponseEntity.ok("OK");
    }


    @PostMapping("/withClassValidation")
    public ResponseEntity<String> registerUserWithClassValidation(
            @RequestBody @Validated RegisterRequestWithClassValidation registerUserRequest
    ) {
        //Other logic
        return ResponseEntity.ok("OK");
    }
}
