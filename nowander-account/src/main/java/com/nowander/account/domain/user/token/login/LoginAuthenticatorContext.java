package com.nowander.account.domain.user.token.login;

import com.nowander.account.domain.user.token.login.authenticate.LoginAuthenticator;
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

}
