package com.nowander.common.core.exception.rest;

import com.nowander.common.core.exception.ApiInfoGetter;
import com.nowander.common.core.exception.BaseException;
import org.springframework.http.HttpStatus;

/**
 * @author wtk
 * @date 2022-09-03
 */
public class RestException extends BaseException {

    public RestException(int code, HttpStatus httpStatus) {
        super(code, httpStatus);
    }

    public RestException(ApiInfoGetter apiInfo) {
        super(apiInfo);
    }

    public RestException(ApiInfoGetter apiInfo, String message) {
        super(apiInfo, message);
    }

    public RestException(ApiInfoGetter apiInfo, String message, Throwable e) {
        super(apiInfo, message, e);
    }
}
