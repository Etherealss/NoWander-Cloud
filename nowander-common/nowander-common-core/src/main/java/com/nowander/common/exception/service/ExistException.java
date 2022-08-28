package com.nowander.common.exception.service;


import com.nowander.common.enums.ApiInfo;
import com.nowander.common.exception.BaseException;

/**
 * @author wtk
 * @description
 * @date 2021-08-12
 */
public class ExistException extends BaseException {
    public ExistException(Class<?> clazz, String message) {
        super(ApiInfo.EXIST, message);
    }

    public ExistException(Class<?> clazz) {
        super(ApiInfo.EXIST, "对应的" + clazz.getSimpleName() + "已存在");
    }
}
