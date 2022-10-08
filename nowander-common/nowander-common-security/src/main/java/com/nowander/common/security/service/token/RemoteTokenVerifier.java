package com.nowander.common.security.service.token;

import com.nowander.common.security.UserCredential;
import com.nowander.common.security.feign.UserTokenFeign;
import lombok.RequiredArgsConstructor;

/**
 * @author wtk
 * @date 2022-10-08
 */
@RequiredArgsConstructor
public class RemoteTokenVerifier implements ITokenVerifier {

    private final UserTokenFeign userTokenFeign;

    @Override
    public UserCredential verifyToken(String token) {
        return userTokenFeign.verifyToken(token);
    }
}
