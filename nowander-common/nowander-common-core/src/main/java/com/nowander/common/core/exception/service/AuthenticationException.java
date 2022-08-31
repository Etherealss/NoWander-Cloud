package com.nowander.common.core.exception.service;

import com.nowander.common.core.exception.ApiInfoGetter;
import com.nowander.common.core.exception.BaseException;
import org.springframework.http.HttpStatus;

/**
 * @author wtk
 * @date 2022-08-30
 */
public class AuthenticationException extends BaseException {
    public AuthenticationException(ApiInfoGetter apiInfo) {
        super(apiInfo);
    }

    public AuthenticationException(ApiInfoGetter apiInfo, String message) {
        super(apiInfo, message);
    }

    public AuthenticationException(ApiInfoGetter apiInfo, String message, Throwable e) {
        super(apiInfo, message, e);
    }

    public AuthenticationException(int code, HttpStatus httpStatus) {
        super(code, httpStatus);
    }
}
