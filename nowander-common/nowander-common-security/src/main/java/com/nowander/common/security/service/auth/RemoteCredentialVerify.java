package com.nowander.common.security.service.auth;

import lombok.RequiredArgsConstructor;

/**
 * @author wtk
 * @date 2022-10-08
 */
@RequiredArgsConstructor
public class RemoteCredentialVerify implements ICredentialVerify {

    private final CredentialCacheHandler credentialCacheHandler;

    @Override
    public <T extends Credential> T verifyToken(String token, Class<T> credentialType){
        return credentialCacheHandler.verifyAndGet(token, credentialType);
    }


}
