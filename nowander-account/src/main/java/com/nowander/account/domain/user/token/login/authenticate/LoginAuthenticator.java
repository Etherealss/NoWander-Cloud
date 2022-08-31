package com.nowander.account.domain.user.token.login.authenticate;

import com.nowander.account.domain.user.SysUser;
import com.nowander.account.domain.user.token.login.UserLoginCommand;

/**
 * 登录认证器
 * @author wtk
 * @date 2022-08-31
 */
public interface LoginAuthenticator {

    SysUser authenticate(UserLoginCommand command);

    boolean supports(UserLoginCommand loginType);
}
