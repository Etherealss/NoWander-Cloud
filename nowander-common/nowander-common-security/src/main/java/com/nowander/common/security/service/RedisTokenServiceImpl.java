package com.nowander.common.security.service;

import com.nowander.common.core.enums.ApiInfo;
import com.nowander.common.core.utils.UUIDUtil;
import com.nowander.common.security.UserCredential;
import com.nowander.common.security.config.TokenConfig;
import com.nowander.common.security.exception.TokenException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Date;
import java.util.UUID;

/**
 * @author wtk
 * @date 2022-08-30
 */
@Component
@Slf4j(topic = "nowander.common.security.service.redis-token-service")
@RequiredArgsConstructor
public class RedisTokenServiceImpl implements ITokenService {

    private final RedisTemplate<String, UserCredential> redisTemplate;

    @Override
    public void createToken(UserCredential userCredential) {
        // token
        UUID token = UUIDUtil.getUuid();
        userCredential.setToken(token.toString());
        long tokenExpireAt = TokenConfig.EXPIRE_MS_TOKEN + System.currentTimeMillis();
        userCredential.setTokenExpireAt(tokenExpireAt);
        String tokenKey = tokenKey(userCredential.getToken());
        redisTemplate.opsForValue().set(
                tokenKey,
                userCredential
        );
        redisTemplate.expireAt(tokenKey, new Date(tokenExpireAt));

        // refreshToken
        UUID refreshToken = UUIDUtil.getUuid();
        userCredential.setRefreshToken(refreshToken.toString());
        redisTemplate.opsForValue().set(
                refreshTokenKey(userCredential.getRefreshToken()),
                userCredential,
                Duration.ofMillis(TokenConfig.EXPIRE_MS_REFRESH_TOKEN)
        );
    }

    @Override
    public UserCredential versifyToken(String token) {
        UserCredential credential = redisTemplate.opsForValue().get(tokenKey(token));
        if (credential == null) {
            throw new TokenException(ApiInfo.TOKEN_INVALID);
        }
        return credential;
    }

    @Override
    public UserCredential versifyRefreshToken(String refreshToken) {
        String key = refreshTokenKey(refreshToken);
        UserCredential credential = redisTemplate.opsForValue().get(key);
        if (credential == null) {
            throw new TokenException(ApiInfo.TOKEN_INVALID);
        }
        return credential;
    }

    @Override
    public UserCredential refreshToken(String refreshToken) {
        String key = refreshTokenKey(refreshToken);
        UserCredential credential = versifyRefreshToken(refreshToken);
        redisTemplate.delete(tokenKey(credential.getToken()));
        redisTemplate.delete(refreshTokenKey(credential.getRefreshToken()));
        createToken(credential);
        return credential;
    }

    private String tokenKey(String token) {
        return TokenConfig.REDIS_PREFIX_TOKEN + token;
    }

    private String refreshTokenKey(String refreshToken) {
        return TokenConfig.REDIS_PREFIX_REFRESH_TOKEN + refreshToken;
    }
}