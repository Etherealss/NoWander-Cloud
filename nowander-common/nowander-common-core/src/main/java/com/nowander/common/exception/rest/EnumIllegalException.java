package com.nowander.common.exception.rest;

import com.nowander.common.enums.BaseEnum;

/**
 * @author wtk
 * @date 2022-04-24
 */
public class EnumIllegalException extends ErrorParamException {

    public EnumIllegalException(Class<? extends BaseEnum> clazz, Object param) {
        super("参数 '" + param.toString() + "'不匹配枚举类'" + clazz.getCanonicalName() + "'");
    }
}
