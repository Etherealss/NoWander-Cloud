package com.nowander.common.exception.service;


import com.nowander.common.exception.ApiInfoGetter;
import com.nowander.common.exception.BaseException;

/**
 * @author wtk
 * @description token异常
 * @date 2021-10-05
 */
public class TokenException extends BaseException {
    public TokenException(ApiInfoGetter apiInfo) {
        super(apiInfo);
    }

    public TokenException(ApiInfoGetter apiInfo, String message) {
        super(apiInfo, message);
    }
}
