package com.nowander.auth.domain.auth.user;

import com.nowander.auth.infrastructure.config.UserCredentialCacheConfig;
import com.nowander.auth.infrastructure.config.UserCredentialMqConfig;
import com.nowander.auth.infrastructure.token.ITokenHandler;
import com.nowander.common.core.mq.RocketMQProvider;
import com.nowander.common.security.service.auth.TokenInvalidMQData;
import com.nowander.common.security.service.auth.user.UserCredential;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * @author wtk
 * @date 2022-08-30
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class UserAuthService {

    private final ITokenHandler tokenHandler;
    private final PasswordEncoder passwordEncoder;
    private final UserAuthInfoMapper userAuthInfoMapper;
    private final LoginAuthenticatorContext loginAuthenticatorContext;
    private final UserCredentialCacheConfig cacheConfig;
    private final UserCredentialMqConfig mqConfig;
    private final RocketMQProvider rocketMQProvider;

    /**
     * 登录，创建 token
     * @param userLoginCommand
     * @return
     */
    public UserCredential loginAndCreateCredential(UserLoginCommand userLoginCommand) {
        String username = userLoginCommand.getUsername();
        UserAuthInfoEntity user = loginAuthenticatorContext.doLogin(userLoginCommand);
        // TODO 用户权限
        Set<String> permissions = new HashSet<>();
        Set<String> roles = new HashSet<>();
        UserCredential userCredential = new UserCredential(
                user.getId(),
                user.getUsername(),
                permissions,
                roles
        );
        userCredential.setLoginTime(new Date());
        tokenHandler.createToken(userCredential, cacheConfig);
        return userCredential;
    }

    /**
     * 验证 token
     * @param token
     * @return
     */
    public UserCredential verifyAndGet(String token) {
        return tokenHandler.verifyToken(token, UserCredential.class, cacheConfig);
    }

    public void logoutAndDeleteCredential(String token) {
        tokenHandler.invalidateToken(token, UserCredential.class, cacheConfig);
        TokenInvalidMQData tokenInvalidMQData = new TokenInvalidMQData(token);
        rocketMQProvider.convertAndSend(
                mqConfig.getTokenTopic(),
                mqConfig.getTokenInvalidTag(),
                tokenInvalidMQData
        );
    }
}
