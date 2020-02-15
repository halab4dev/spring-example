package com.github.halab4dev.exception;

import com.github.halab4dev.constant.ResponseCode;

/*
 *
 * @author halab
 */
public class ResourceNotFoundException extends ApplicationException {

    public ResourceNotFoundException() {
        super(ResponseCode.RESOURCE_NOT_FOUND);
    }
}
