package com.nowander.auth.infrastructure.token;

import com.nowander.auth.infrastructure.config.ICredentialCacheConfig;
import com.nowander.auth.infrastructure.config.ServerCredentialCacheConfig;
import com.nowander.auth.infrastructure.config.UserCredentialCacheConfig;
import com.nowander.common.security.service.auth.Credential;
import com.nowander.common.security.service.auth.ITokenVerifier;
import com.nowander.common.security.service.auth.RemoteTokenVerifier;
import com.nowander.common.security.service.auth.server.ServerCredential;
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
    private final ServerCredentialCacheConfig serverConfig;
    private final UserCredentialCacheConfig userConfig;

    @Override
    public <T extends Credential> T verifyToken(String token, Class<T> credentialType) {
        ICredentialCacheConfig config =
                credentialType == ServerCredential.class ? serverConfig : userConfig;
        return tokenHandler.verifyToken(token, credentialType, config);
    }
}
