package com.nowander.auth.infrastructure.token;

import com.nowander.auth.infrastructure.config.ICredentialCacheConfig;
import com.nowander.auth.infrastructure.config.ServerCredentialCacheConfig;
import com.nowander.auth.infrastructure.config.UserCredentialCacheConfig;
import com.nowander.common.security.service.auth.Credential;
import com.nowander.common.security.service.auth.ICredentialVerify;
import com.nowander.common.security.service.auth.RemoteCredentialVerify;
import com.nowander.common.security.service.auth.server.ServerCredential;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * 取代 RemoteTokenVerifier，直接调用本地代码验证token
 * @author wtk
 * @date 2022-10-08
 * @see RemoteCredentialVerify
 */
@RequiredArgsConstructor
@Component
public class LocalCredentialVerify implements ICredentialVerify {
    private final com.nowander.auth.infrastructure.token.ITokenHandler tokenHandler;
    private final ServerCredentialCacheConfig serverCredentialCacheConfig;
    private final UserCredentialCacheConfig userCredentialCacheConfig;

    @Override
    public <T extends Credential> T verifyToken(String token, Class<T> credentialType) {
        ICredentialCacheConfig config =
                credentialType == ServerCredential.class ? serverCredentialCacheConfig : userCredentialCacheConfig;
        return tokenHandler.verifyToken(token, credentialType, config);
    }
}
