package com.nowander.common.security.service.token.verify.cache;

import com.nowander.common.security.UserCredential;
import com.nowander.common.security.config.TokenConfig;
import com.nowander.common.security.feign.UserTokenFeign;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author wtk
 * @date 2022-10-09
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class UserCredentialCacheHandler {

    private final RedisTemplate<String, UserCredential> redisTemplate;
    private final UserTokenFeign userTokenFeign;
    private final TokenConfig tokenConfig;

    public UserCredential verifyAndGet(String token) {
        String key = tokenConfig.getCacheKey() + ":" + token;
        UserCredential userCredential = redisTemplate.opsForValue().get(key);
        if (userCredential == null) {
            userCredential = userTokenFeign.verifyToken(token);
        }
        cache(key, userCredential, userCredential.getTokenExpireAt());
        return userCredential;
    }

    private void cache(String key, UserCredential value, Date expireAt) {
        redisTemplate.opsForValue().set(key, value);
        redisTemplate.expireAt(key, expireAt);
    }
}
