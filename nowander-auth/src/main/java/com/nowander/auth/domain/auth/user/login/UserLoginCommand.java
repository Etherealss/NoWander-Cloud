package com.nowander.auth.domain.auth.user.login;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @author wtk
 * @date 2022-08-30
 */
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserLoginCommand {
    @NotNull
    LoginType loginType;

    @NotEmpty
    String captcha;

    @NotEmpty
    String captchaId;

    String username;

    String phone;

    String password;

    String email;
}
