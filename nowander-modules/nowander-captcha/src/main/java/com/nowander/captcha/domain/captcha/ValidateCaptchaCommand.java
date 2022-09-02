package com.nowander.captcha.domain.captcha;

import lombok.Data;

/**
 * @author wtk
 * @date 2022-09-02
 */
@Data
public class ValidateCaptchaCommand {
    private String userInputCaptcha;
}
