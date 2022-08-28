package com.nowander.common.exception.rest;


import com.nowander.common.enums.ApiInfo;
import com.nowander.common.exception.BaseException;

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
