package com.nowander.auth.infrastructure.token;

import com.nowander.auth.infrastructure.config.ICredentialCacheConfig;
import com.nowander.common.security.service.auth.Credential;

/**
 * @author wtk
 * @date 2022-08-30
 */
public interface ITokenHandler {

    void createToken(Credential credential, ICredentialCacheConfig config);

    // TODO updateUserCredential 在用户权限更新时修改token权限缓存

    <T extends Credential> T verifyToken(String token, Class<T> credentialType, ICredentialCacheConfig config);

    <T extends Credential> T verifyRefreshToken(String refreshToken, Class<T> credentialType, ICredentialCacheConfig config);

    <T extends Credential> T refreshToken(String refreshToken, Class<T> credentialType, ICredentialCacheConfig config);
}
