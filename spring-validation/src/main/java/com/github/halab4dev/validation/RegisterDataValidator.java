package com.github.halab4dev.validation;

import com.github.halab4dev.constant.Constant;
import com.github.halab4dev.constant.Gender;
import com.github.halab4dev.request.RegisterRequestWithClassValidation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/*
 *
 * @author halab
 */
public class RegisterDataValidator implements ConstraintValidator<RegisterData, RegisterRequestWithClassValidation> {

    @Override
    public boolean isValid(
            RegisterRequestWithClassValidation registerRequest,
            ConstraintValidatorContext constraintValidatorContext
    ) {
        Gender gender = registerRequest.getGender();
        Integer age = registerRequest.getAge();

        if (gender == Gender.MALE && age < Constant.MIN_MALE_AGE) {
            constraintValidatorContext
                    .buildConstraintViolationWithTemplate("Age of male user must be greater than or equal " + Constant.MIN_MALE_AGE)
                    .addConstraintViolation();
            return false;
        }

        if (gender == Gender.FEMALE && age < Constant.MIN_FEMALE_AGE) {
            constraintValidatorContext
                    .buildConstraintViolationWithTemplate("Age of female user must be greater than or equal " + Constant.MIN_FEMALE_AGE)
                    .addConstraintViolation();
            return false;
        }

        return true;
    }
}
