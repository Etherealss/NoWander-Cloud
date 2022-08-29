package com.nowander.common.core.exception.service;


import com.nowander.common.core.enums.ApiInfo;
import com.nowander.common.core.exception.BaseException;

/**
 * @author wtk
 * @description 验证码异常
 * @date 2021-10-05
 */
public class CaptchaException extends BaseException {
    public CaptchaException(ApiInfo apiInfo, String message) {
        super(apiInfo, message);
    }

    public CaptchaException(ApiInfo apiInfo) {
        super(apiInfo);
    }
}
