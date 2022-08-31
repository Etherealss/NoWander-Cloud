package com.nowander.common.core.exception.rest;


import com.nowander.common.core.enums.ApiInfo;
import com.nowander.common.core.exception.BaseException;

/**
 * @author wtk
 * @description
 * @date 2021-08-13
 */
public class ParamErrorException extends BaseException {
    public ParamErrorException() {
        super(ApiInfo.ERROR_PARAM);
    }

    public ParamErrorException(String message) {
        super(ApiInfo.ERROR_PARAM, message);
    }
}
