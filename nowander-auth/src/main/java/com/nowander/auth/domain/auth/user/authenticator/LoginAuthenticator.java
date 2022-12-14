package com.nowander.auth.domain.auth.user.authenticator;

import com.nowander.auth.domain.auth.user.UserAuthInfoEntity;
import com.nowander.auth.domain.auth.user.UserLoginCommand;

/**
 * 登录认证器
 * @author wtk
 * @date 2022-08-31
 */
public interface LoginAuthenticator {

    UserAuthInfoEntity authenticate(UserLoginCommand command);

    boolean supports(UserLoginCommand loginType);
}
