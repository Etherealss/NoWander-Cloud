package com.nowander.common.security.token;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.jwt.JWTUtil;
import com.nowander.common.core.enums.ApiInfo;
import com.nowander.common.core.exception.service.TokenException;
import com.nowander.common.security.RedisKeyPrefix;
import com.nowander.common.security.UserCredential;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

/**
 * @author wtk
 * @date 2022-08-29
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class AuthTokenService {

    private JwtConfig jwtConfig;
    private RedisTemplate<String, String> redisTemplate;

    /**
     * 校验token是否有效，但不检验是否过期
     * @param token
     * @return
     */
    public boolean verifyToken(String token) {
        return JWTUtil.verify(token, jwtConfig.getKeyBytes());
    }


    /**
     * 检查token是否有效、过期
     * @param token
     */
    public void requireVaildToken(String token) {
        if (StrUtil.isBlank(token)) {
            throw new TokenException(ApiInfo.TOKEN_MISSING);
        }
        if (!verifyToken(token)) {
            throw new TokenException(ApiInfo.TOKEN_INVALID);
        }
        if (TokenUtil.expiredToken(token)) {
            // 过期
            throw new TokenException(ApiInfo.TOKEN_EXP);
        }
        String username = (String) TokenUtil.parseAndGet(token, "username");
        if (redisTemplate.opsForValue().get(RedisKeyPrefix.USER_TOKEN_BLACKLIST + username) != null) {
            // 虽然token还没过期，但username在黑名单中，说明用户已登出，不能使用token，必须重新登录
            throw new TokenException(ApiInfo.TOKEN_INVALID);
        }
    }

    /**
     * 通过req获取JWT中包含的User信息
     * @param token
     * @return
     * @throws TokenException
     */
    public UserCredential requireUserByToken(String token) throws TokenException {
        requireVaildToken(token);
        JSONObject tokenClaims = TokenUtil.parse(token);
        return new UserCredential(token, tokenClaims);
    }

}
