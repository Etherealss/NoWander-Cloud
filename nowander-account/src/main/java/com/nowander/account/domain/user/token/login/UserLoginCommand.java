package com.nowander.account.domain.user.token.login;

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
    @NotEmpty
    String username;

    @NotNull
    LoginType loginType;

    String phone;

    String password;

    String captcha;

    String captchaId;

    String email;
}
