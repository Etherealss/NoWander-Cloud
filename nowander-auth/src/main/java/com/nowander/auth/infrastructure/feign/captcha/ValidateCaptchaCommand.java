package com.nowander.auth.infrastructure.feign.captcha;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author wtk
 * @date 2022-09-02
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ValidateCaptchaCommand {
    private String userInputCaptcha;
}
