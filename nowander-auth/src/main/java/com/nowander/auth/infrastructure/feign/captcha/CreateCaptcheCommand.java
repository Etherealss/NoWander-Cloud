package com.nowander.auth.infrastructure.feign.captcha;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotNull;

/**
 * @author wtk
 * @date 2022-08-31
 */
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateCaptcheCommand {
    /**
     * 使用全局唯一的值作为key来判断用户
     */
    @NotNull
    String captchaId;
}
