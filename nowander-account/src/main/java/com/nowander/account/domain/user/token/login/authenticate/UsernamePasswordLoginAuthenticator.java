package com.nowander.account.domain.user.token.login.authenticate;

import com.nowander.account.domain.user.SysUser;
import com.nowander.account.domain.user.UserMapper;
import com.nowander.account.domain.user.token.login.LoginType;
import com.nowander.account.domain.user.token.login.UserLoginCommand;
import com.nowander.account.infrasturcture.feign.captcha.CaptchaFeign;
import com.nowander.account.infrasturcture.feign.captcha.ValidateCaptchaCommand;
import com.nowander.common.core.enums.ApiInfo;
import com.nowander.common.core.exception.service.AuthenticationException;
import com.nowander.common.core.exception.service.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

/**
 * @author wtk
 * @date 2022-08-31
 */
@Component
@RequiredArgsConstructor
public class UsernamePasswordLoginAuthenticator implements LoginAuthenticator {

    private final UserMapper userMapper;
    private final CaptchaFeign captchaFeign;
    private final PasswordEncoder passwordEncoder;

    @Override
    public SysUser authenticate(UserLoginCommand command) {
        String username = command.getUsername();
        String password = command.getPassword();
        String captchaId = command.getCaptchaId();
        String captcha = command.getCaptcha();
        Assert.hasText(username, "登录用户名不能为空");
        Assert.hasText(password, "登录密码不能为空");
        Assert.hasText(captchaId, "登录验证码不能为空");
        Assert.hasText(captcha, "登录验证码不能为空");

        captchaFeign.validateCaptcha(captchaId, new ValidateCaptchaCommand(captcha));
        SysUser user = userMapper.selectByUsername(username)
                .orElseThrow(() -> new NotFoundException("用户 " + username + " 不存在"));
        validatePassword(command.getPassword(), user.getPassword());
        return user;
    }

    private void validatePassword(String userInput, String password) {
        if (!passwordEncoder.matches(userInput, password)) {
            throw new AuthenticationException(ApiInfo.PASSWORD_ERROR, "登录密码错误");
        }
    }

    @Override
    public boolean supports(UserLoginCommand userLoginCommand) {
        return userLoginCommand.getLoginType() == LoginType.USERNAME;
    }
}
