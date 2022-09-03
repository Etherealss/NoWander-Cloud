package com.nowander.common.core.exception.rest;


import com.nowander.common.core.enums.ApiInfo;

/**
 * @author wtk
 * @description
 * @date 2021-08-13
 */
public class MissingParamException extends RestException {
    public MissingParamException() {
        super(ApiInfo.MISSING_PARAM);
    }

    public MissingParamException(String paramName) {
        super(ApiInfo.MISSING_PARAM, paramName + "参数缺失");
    }
}
