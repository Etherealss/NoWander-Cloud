package com.nowander.common.security.exception;


import com.nowander.common.core.exception.ApiInfoGetter;
import com.nowander.common.core.exception.service.AuthenticationException;

/**
 * @author wtk
 * @description token异常
 * @date 2021-10-05
 */
public class TokenException extends AuthenticationException {
    public TokenException(ApiInfoGetter apiInfo) {
        super(apiInfo);
    }

    public TokenException(ApiInfoGetter apiInfo, String message) {
        super(apiInfo, message);
    }
}
