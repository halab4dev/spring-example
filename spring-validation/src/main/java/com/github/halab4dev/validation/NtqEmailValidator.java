package com.github.halab4dev.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/*
 *
 * @author halab
 */
public class NtqEmailValidator implements ConstraintValidator<NtqEmail, String> {

    @Override
    public boolean isValid(String email, ConstraintValidatorContext constraintValidatorContext) {
        return NtqEmailValidation.isNtqEmail(email);
    }
}
