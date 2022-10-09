package com.nowander.common.security.service.token.verify;

import com.nowander.common.security.UserCredential;
import com.nowander.common.security.service.token.verify.cache.UserCredentialCacheHandler;
import lombok.RequiredArgsConstructor;

/**
 * @author wtk
 * @date 2022-10-08
 */
@RequiredArgsConstructor
public class RemoteTokenVerifier implements ITokenVerifier {

    private final UserCredentialCacheHandler userCredentialCacheHandler;

    @Override
    public UserCredential verifyToken(String token) {
        return userCredentialCacheHandler.verifyAndGet(token);
    }
}
