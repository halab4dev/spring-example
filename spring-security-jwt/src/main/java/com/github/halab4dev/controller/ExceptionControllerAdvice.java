package com.github.halab4dev.controller;

import com.github.halab4dev.constant.ResponseCode;
import com.github.halab4dev.exception.ApplicationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/*
 *
 * @author halab
 */
@Slf4j
@RestControllerAdvice
public class ExceptionControllerAdvice {

    @ExceptionHandler(ApplicationException.class)
    public String handleApplicationException(ApplicationException ex) {
        return ex.getErrorCode().toString();
    }

    @ExceptionHandler(AccessDeniedException.class)
    public String handleAccessDeniedException() {
        return ResponseCode.PERMISSION_DENIED.toString();
    }

    @ExceptionHandler(Exception.class)
    public String handleException(Exception ex) {
        return ResponseCode.INTERNAL_SERVER_ERROR.toString();
    }
}
