package com.nowander.common.core.exception.internal;

import com.nowander.common.core.enums.ApiInfo;
import com.nowander.common.core.exception.BaseException;

/**
 * @author wtk
 * @date 2022-09-06
 */
public class ConfigurationException extends BaseException {
    public ConfigurationException(String message) {
        super(ApiInfo.SERVER_ERROR, message);
    }
}