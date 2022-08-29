package com.nowander.common.core.exception.rest;


import com.nowander.common.core.enums.ApiInfo;
import com.nowander.common.core.exception.BaseException;

/**
 * @author wtk
 * @description
 * @date 2021-08-13
 */
public class ErrorParamException extends BaseException {
    public ErrorParamException() {
        super(ApiInfo.ERROR_PARAM);
    }

    public ErrorParamException(String message) {
        super(ApiInfo.ERROR_PARAM, message);
    }
}
