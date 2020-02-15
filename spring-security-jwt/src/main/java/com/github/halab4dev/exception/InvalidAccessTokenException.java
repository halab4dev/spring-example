package com.github.halab4dev.exception;

import com.github.halab4dev.constant.ResponseCode;

/*
 *
 * @author halab
 */
public class InvalidAccessTokenException extends ApplicationException {

    public InvalidAccessTokenException() {
        super(ResponseCode.INVALID_ACCESS_TOKEN);
    }
}
