package com.nowander.common.exception.service;


import com.nowander.common.enums.ApiInfo;
import com.nowander.common.exception.BaseException;

/**
 * @author wtk
 * @description
 * @date 2021-08-12
 */
public class NotFoundException extends BaseException {
    public NotFoundException(String msg) {
        super(ApiInfo.NOT_FOUND, msg);
    }
    public NotFoundException(Class<?> clazz, String identification) {
        super(ApiInfo.NOT_FOUND, "属性'" + identification + "'对应的" + clazz.getSimpleName() + "不存在");
    }
}
