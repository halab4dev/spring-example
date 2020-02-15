package com.github.halab4dev.exception;

import com.github.halab4dev.constant.ResponseCode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

/*
 *
 * @author halab
 */
@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public abstract class ApplicationException extends RuntimeException {

    private ResponseCode errorCode;
}
