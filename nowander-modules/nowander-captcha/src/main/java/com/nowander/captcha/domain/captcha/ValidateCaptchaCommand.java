package com.nowander.captcha.domain.captcha;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * @author wtk
 * @date 2022-09-02
 */
@Data
public class ValidateCaptchaCommand {
    @NotEmpty
    private String userInputCaptcha;
}
