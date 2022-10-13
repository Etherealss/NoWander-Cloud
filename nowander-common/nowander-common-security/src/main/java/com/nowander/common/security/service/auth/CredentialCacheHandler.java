package com.nowander.common.security.service.auth;

import com.nowander.common.security.config.UserTokenConfig;
import com.nowander.common.security.feign.ServerTokenFeign;
import com.nowander.common.security.feign.UserTokenFeign;
import com.nowander.common.security.service.auth.user.UserCredential;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author wtk
 * @date 2022-10-09
 */
@SuppressWarnings("unchecked")
@Component
@RequiredArgsConstructor
@Slf4j
public class CredentialCacheHandler {

    private final RedisTemplate<String, Object> redisTemplate;
    private final UserTokenFeign userTokenFeign;
    private final ServerTokenFeign serverTokenFeign;
    private final UserTokenConfig userTokenConfig;

    public <T extends Credential> T verifyAndGet(String token, Class<T> credentialType) {
        String key = userTokenConfig.getCacheKey() + ":" + token;
        Credential credential = (Credential) redisTemplate.opsForValue().get(key);
        if (credential != null && credential.getClass() == credentialType) {
            return (T) credential;
        }
        if (credentialType == UserCredential.class) {
            credential = userTokenFeign.verifyToken(token);
        } else {
            credential = serverTokenFeign.verifyToken(token);
        }
        cache(key, credential, credential.getTokenExpireAt());
        return (T) credential;
    }

    private void cache(String key, Credential value, Date expireAt) {
        redisTemplate.opsForValue().set(key, value);
        redisTemplate.expireAt(key, expireAt);
    }
}