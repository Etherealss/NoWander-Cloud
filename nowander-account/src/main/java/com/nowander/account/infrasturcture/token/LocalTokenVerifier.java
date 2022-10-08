package com.nowander.account.infrasturcture.token;

import com.nowander.common.security.UserCredential;
import com.nowander.common.security.service.ITokenVerifier;
import com.nowander.common.security.service.RemoteTokenVerifier;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * 取代 RemoteTokenVerifier，直接调用本地代码验证token
 * @author wtk
 * @date 2022-10-08
 * @see RemoteTokenVerifier
 */
@RequiredArgsConstructor
@Component
public class LocalTokenVerifier implements ITokenVerifier {
    private final ITokenHandler tokenHandler;

    @Override
    public UserCredential verifyToken(String token) {
        return tokenHandler.verifyToken(token);
    }
}
