package com.nowander.account.infrasturcture.token;

import com.nowander.common.security.UserCredential;

/**
 * @author wtk
 * @date 2022-08-30
 */
public interface ITokenHandler {

    void createToken(UserCredential userCredential);

    // TODO updateUserCredential 在用户权限更新时修改token权限缓存

    UserCredential verifyToken(String token);

    UserCredential verifyRefreshToken(String refreshToken);

    UserCredential refreshToken(String refreshToken);
}
