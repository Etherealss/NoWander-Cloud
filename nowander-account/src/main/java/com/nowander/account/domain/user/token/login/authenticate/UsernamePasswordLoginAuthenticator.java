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
import com.nowander.common.core.pojo.Msg;
import lombok.RequiredArgsConstructor;
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

    @Override
    public SysUser authenticate(UserLoginCommand command) {
        String username = command.getUsername();
        Assert.hasText(username, "登录用户名不能为空");
        String password = command.getPassword();
        Assert.hasText(password, "登录密码不能为空");
        String captchaId = command.getCaptchaId();
        String captcha = command.getCaptcha();
        Assert.hasText(captchaId, "登录验证码不能为空");
        Assert.hasText(captcha, "登录验证码不能为空");

        Msg<Void> validateCaptcha = captchaFeign.validateCaptcha(captchaId, new ValidateCaptchaCommand(captcha));

        SysUser user = userMapper.selectByUsername(username)
                .orElseThrow(() -> new NotFoundException("用户 " + username + " 不存在"));
        if (!user.getPassword().equals(command.getPassword())) {
            throw new AuthenticationException(ApiInfo.PASSWORD_ERROR, "登录密码错误");
        }
        return user;
    }

    @Override
    public boolean supports(UserLoginCommand userLoginCommand) {
        return userLoginCommand.getLoginType() == LoginType.USERNAME;
    }
}
