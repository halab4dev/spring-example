package com.github.halab4dev.exception;

import com.github.halab4dev.constant.ResponseCode;

/*
 *
 * @author halab
 */
public class UnauthorizedException extends ApplicationException {

    public UnauthorizedException() {
        super(ResponseCode.UNAUTHORIZED);
    }
}
