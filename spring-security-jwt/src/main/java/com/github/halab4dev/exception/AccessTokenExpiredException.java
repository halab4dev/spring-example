package com.github.halab4dev.exception;

import com.github.halab4dev.constant.ResponseCode;

/*
 *
 * @author halab
 */
public class AccessTokenExpiredException extends ApplicationException {

    public AccessTokenExpiredException() {
        super(ResponseCode.ACCESS_TOKEN_EXPIRED);
    }
}
