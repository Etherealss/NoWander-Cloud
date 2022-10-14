package com.nowander.auth.infrastructure.token;

import com.nowander.common.security.service.auth.Credential;

/**
 * @author wtk
 * @date 2022-08-30
 */
public interface ITokenHandler {

    void createToken(Credential credential);

    // TODO updateUserCredential 在用户权限更新时修改token权限缓存

    <T extends Credential> T verifyToken(String token, Class<T> credentialType);

    <T extends Credential> T verifyRefreshToken(String refreshToken, Class<T> credentialType);

    <T extends Credential> T  refreshToken(String refreshToken, Class<T> credentialType);
}
