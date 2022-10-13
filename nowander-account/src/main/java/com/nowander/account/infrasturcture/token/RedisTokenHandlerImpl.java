package com.nowander.account.infrasturcture.token;

import com.nowander.account.infrasturcture.config.UserRefreshTokenCacheConfig;
import com.nowander.account.infrasturcture.config.UserTokenCacheConfig;
import com.nowander.common.core.enums.ApiInfo;
import com.nowander.common.core.utils.UUIDUtil;
import com.nowander.common.security.exception.TokenException;
import com.nowander.common.security.service.auth.Credential;
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
@SuppressWarnings("unchecked")
@Component
@Slf4j
@RequiredArgsConstructor
public class RedisTokenHandlerImpl implements ITokenHandler {

    private final RedisTemplate<String, Credential> redisTemplate;
    private final UserTokenCacheConfig tokenCacheConfig;
    private final UserRefreshTokenCacheConfig refreshTokenCacheConfig;

    @Override
    public void createToken(Credential userCredential) {
        UUID token = UUIDUtil.get();
        userCredential.setToken(token.toString());
        Date tokenExpireAt = new Date(tokenCacheConfig.getExpireMs() + System.currentTimeMillis());
        userCredential.setTokenExpireAt(tokenExpireAt);

        UUID refreshToken = UUIDUtil.get();
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
    public <T extends Credential> T verifyToken(String token, Class<T> credentialType) {
        Credential credential = redisTemplate.opsForValue().get(tokenKey(token));
        if (credential == null || credential.getClass() != credentialType) {
            throw new TokenException(ApiInfo.USER_TOKEN_INVALID);
        }
        return (T) credential;
    }

    @Override
    public <T extends Credential> T verifyRefreshToken(String refreshToken, Class<T> credentialType) {
        String key = refreshTokenKey(refreshToken);
        Credential credential = redisTemplate.opsForValue().get(key);
        if (credential == null || credential.getClass() != credentialType) {
            throw new TokenException(ApiInfo.USER_TOKEN_INVALID);
        }
        return (T) credential;
    }

    @Override
    public <T extends Credential> T refreshToken(String refreshToken, Class<T> credentialType) {
        String key = refreshTokenKey(refreshToken);
        Credential credential = this.verifyRefreshToken(refreshToken, credentialType);
        redisTemplate.delete(tokenKey(credential.getToken()));
        redisTemplate.delete(refreshTokenKey(credential.getRefreshToken()));
        createToken(credential);
        return (T) credential;
    }

    private String tokenKey(String token) {
        return tokenCacheConfig.getCacheKey() + ":" + token;
    }

    private String refreshTokenKey(String refreshToken) {
        return refreshTokenCacheConfig.getCacheKey() + ":" + refreshToken;
    }
}
