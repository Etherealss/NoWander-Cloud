package com.nowander.auth.domain.auth.user;

import com.nowander.auth.infrastructure.token.ITokenHandler;
import com.nowander.common.security.service.auth.user.UserCredential;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author wtk
 * @date 2022-08-30
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class UserTokenService {

    private final ITokenHandler tokenHandler;

    public void buildToken(UserCredential userCredential) {
        tokenHandler.createToken(userCredential);
    }

    public UserCredential verify(String token) {
        return tokenHandler.verifyToken(token, UserCredential.class);
    }
}
