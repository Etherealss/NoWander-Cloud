package com.nowander.common.security.service;

import com.nowander.common.security.UserCredential;

/**
 * @author wtk
 * @date 2022-08-30
 */
public interface ITokenService {

    void createToken(UserCredential userCredential);

    // TODO updateUserCredential 在用户权限更新时修改token权限缓存

    UserCredential versifyToken(String token);

    UserCredential versifyRefreshToken(String refreshToken);

    UserCredential refreshToken(String refreshToken);
}
