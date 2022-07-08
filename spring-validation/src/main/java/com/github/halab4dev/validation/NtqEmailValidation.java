package com.github.halab4dev.validation;

import lombok.experimental.UtilityClass;

import java.util.regex.Pattern;

/*
 *
 * @author halab
 */
@UtilityClass
public class NtqEmailValidation {

    public static final String NTQ_EMAIL_REGEX = "[a-zA-Z0-9.]+@ntq-solution.com.vn";
    private static final Pattern NTQ_EMAIL_PATTERN = Pattern.compile(NTQ_EMAIL_REGEX);

    public static boolean isNtqEmail(String email) {
        return NTQ_EMAIL_PATTERN.matcher(email).matches();
    }
}
