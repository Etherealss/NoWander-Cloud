package com.nowander.common.core.exception.service;

import com.nowander.common.core.exception.ApiInfoGetter;
import com.nowander.common.core.exception.BaseException;
import org.springframework.http.HttpStatus;

/**
 * 普通的业务异常
 * @author wtk
 * @date 2022-09-03
 */
public class SimpleServiceException extends BaseException {
    public SimpleServiceException(int code, HttpStatus httpStatus) {
        super(code, httpStatus);
    }

    public SimpleServiceException(ApiInfoGetter apiInfo) {
        super(apiInfo);
    }

    public SimpleServiceException(ApiInfoGetter apiInfo, String message) {
        super(apiInfo, message);
    }

    public SimpleServiceException(ApiInfoGetter apiInfo, String message, Throwable e) {
        super(apiInfo, message, e);
    }
}
