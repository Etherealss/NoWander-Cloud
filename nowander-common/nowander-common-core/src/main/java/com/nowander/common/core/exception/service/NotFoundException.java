package com.nowander.common.core.exception.service;


import com.nowander.common.core.enums.ApiInfo;

/**
 * @author wtk
 * @description
 * @date 2021-08-12
 */
public class NotFoundException extends SimpleServiceException {
    public NotFoundException(String msg) {
        super(ApiInfo.NOT_FOUND, msg);
    }

    public NotFoundException(Class<?> clazz, String identification) {
        super(ApiInfo.NOT_FOUND, "标识符为'" + identification + "'对应的" + clazz.getSimpleName() + "不存在");
    }
}
