package com.nowander.account.domain.user.token.login;

import com.nowander.account.domain.user.SysUser;
import com.nowander.account.domain.user.token.login.authenticate.LoginAuthenticator;
import com.nowander.common.core.enums.ApiInfo;
import com.nowander.common.core.exception.service.SimpleServiceException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author wtk
 * @date 2022-08-31
 */
@Component
@RequiredArgsConstructor
public class LoginAuthenticatorContext {

    private final List<LoginAuthenticator> authenticators;

    public SysUser doLogin(UserLoginCommand command) {
        LoginAuthenticator authenticator = dispatch(command);
        return authenticator.authenticate(command);
    }

    private LoginAuthenticator dispatch(UserLoginCommand command) {
        for (LoginAuthenticator authenticator : authenticators) {
            if (authenticator.supports(command)) {
                return authenticator;
            }
        }
        throw new SimpleServiceException(ApiInfo.LOGIN_TYPE_NOT_SUPPORT);
    }

}
