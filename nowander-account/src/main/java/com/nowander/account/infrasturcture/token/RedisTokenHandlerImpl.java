package com.nowander.account.infrasturcture.token;

import com.nowander.account.infrasturcture.config.UserRefreshTokenCacheConfig;
import com.nowander.common.core.enums.ApiInfo;
import com.nowander.common.core.utils.UUIDUtil;
import com.nowander.common.security.UserCredential;
import com.nowander.account.infrasturcture.config.UserTokenCacheConfig;
import com.nowander.common.security.exception.TokenException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.UUID;

/**
 * @author wtk
 * @date 2022-08-30
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class RedisTokenHandlerImpl implements ITokenHandler {

    private final RedisTemplate<String, UserCredential> redisTemplate;
    private final UserTokenCacheConfig tokenCacheConfig;
    private final UserRefreshTokenCacheConfig refreshTokenCacheConfig;

    @Override
    public void createToken(UserCredential userCredential) {
        UUID token = UUIDUtil.getUuid();
        userCredential.setToken(token.toString());
        Date tokenExpireAt = new Date(tokenCacheConfig.getExpireMs() + System.currentTimeMillis());
        userCredential.setTokenExpireAt(tokenExpireAt);

        UUID refreshToken = UUIDUtil.getUuid();
        userCredential.setRefreshToken(refreshToken.toString());
        Date refreshExpireAt = new Date(refreshTokenCacheConfig.getExpireMs() + System.currentTimeMillis());
        userCredential.setRefreshTokenExpireAt(refreshExpireAt);

        String redisKey = tokenKey(userCredential.getToken());
        redisTemplate.opsForValue().set(redisKey, userCredential);
        redisTemplate.expireAt(redisKey, tokenExpireAt);

        redisKey = refreshTokenKey(userCredential.getRefreshToken());
        redisTemplate.opsForValue().set(redisKey, userCredential);
        redisTemplate.expireAt(redisKey, refreshExpireAt);
    }

    @Override
    public UserCredential verifyToken(String token) {
        UserCredential credential = redisTemplate.opsForValue().get(tokenKey(token));
        if (credential == null) {
            throw new TokenException(ApiInfo.TOKEN_INVALID);
        }
        return credential;
    }

    @Override
    public UserCredential verifyRefreshToken(String refreshToken) {
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
        UserCredential credential = verifyRefreshToken(refreshToken);
        redisTemplate.delete(tokenKey(credential.getToken()));
        redisTemplate.delete(refreshTokenKey(credential.getRefreshToken()));
        createToken(credential);
        return credential;
    }

    private String tokenKey(String token) {
        return tokenCacheConfig.getCacheKey() + ":" + token;
    }

    private String refreshTokenKey(String refreshToken) {
        return refreshTokenCacheConfig.getCacheKey() + ":" + refreshToken;
    }
}
