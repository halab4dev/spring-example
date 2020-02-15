package com.github.halab4dev.constant;

import lombok.Getter;

/*
 *
 * @author halab
 */
@Getter
public enum ResponseCode {

    SUCCESS(200),
    UNAUTHORIZED(401),
    PERMISSION_DENIED(403),
    RESOURCE_NOT_FOUND(404),
    INCORRECT_USERNAME_OR_PASSWORD(410),
    INVALID_ACCESS_TOKEN(411),
    ACCESS_TOKEN_EXPIRED(412),
    INTERNAL_SERVER_ERROR(500);

    private int code;

    ResponseCode(int code) {
        this.code = code;
    }
}
