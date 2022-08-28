package com.nowander.common.exception.service;


import com.nowander.common.enums.ApiInfo;
import com.nowander.common.exception.BaseException;

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
