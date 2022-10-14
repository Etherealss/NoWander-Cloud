package com.nowander.auth.infrastructure.token;

import com.nowander.common.security.service.auth.Credential;
import com.nowander.common.security.service.auth.ITokenVerifier;
import com.nowander.common.security.service.auth.RemoteTokenVerifier;
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
    public <T extends Credential> T verifyToken(String token, Class<T> credentialType) {
        return tokenHandler.verifyToken(token, credentialType);
    }
}
