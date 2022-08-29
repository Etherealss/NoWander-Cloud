package com.nowander.account.domain.user.token;

import cn.hutool.jwt.JWT;
import cn.hutool.jwt.JWTUtil;
import cn.hutool.jwt.signers.JWTSigner;
import cn.hutool.jwt.signers.JWTSignerUtil;
import com.nowander.account.domain.user.SysUser;
import com.nowander.common.security.UserCredential;
import com.nowander.common.security.token.AuthTokenService;
import com.nowander.common.security.token.JwtConfig;
import lombok.AllArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * JWT: https://blog.csdn.net/weixin_45070175/article/details/118559272
 * Header: alg 签名算法，默认为HMAC SHA256（写为HS256）; typ 令牌类型，JWT令牌统一写为JWT。
 * Payload:
 *      iss：发行人
 *      exp：到期时间
 *      sub：主题
 *      aud：用户
 *      nbf：在此之前不可用
 *      iat：发布时间
 * jti：JWT ID用于标识该JWT
 * @author wang tengkun
 * @date 2022/2/23
 */
@Component
@AllArgsConstructor
public class BuildTokenService {

    private JwtConfig jwtConfig;
    private RedisTemplate<String, String> redisTemplate;
    private AuthTokenService authTokenService;

    @NonNull
    public String refleshToken(HttpServletRequest request) {
        String refleshToken = request.getHeader(jwtConfig.getRefleshHeader());
        authTokenService.requireVaildToken(refleshToken);
        UserCredential userCredential = authTokenService.requireUserByToken(refleshToken);
        return this.createToken(userCredential);
    }

    public String createToken(UserCredential userCredential) {
        Map<String, Object> payload = new HashMap<>(8);
        payload.put("id", userCredential.getUserId());
        payload.put("username", userCredential.getUsername());
        payload.put("avatar", userCredential.getAvatar());
        return createToken(payload, false);
    }

    public String createRefleshToken(SysUser sysUser) {
        Map<String, Object> payload = new HashMap<>(8);
        payload.put("id", sysUser.getId());
        payload.put("username", sysUser.getUsername());
        payload.put("avatar", sysUser.getAvatar());
        return createToken(payload, true);
    }

    /**
     * 生成jwt
     * @param payload 数据主体
     * @param isReflesh
     * @return
     */
    public String createToken(Map<String, Object> payload, boolean isReflesh) {
        // 每个jwt都默认生成一个到期时间
        if (isReflesh) {
            payload.put(JWT.EXPIRES_AT, System.currentTimeMillis() + jwtConfig.getRefleshExpireMs());
        } else {
            payload.put(JWT.EXPIRES_AT, System.currentTimeMillis() + jwtConfig.getExpireMs());
        }
        payload.put(JWT.ISSUED_AT, System.currentTimeMillis());
        payload.put(JWT.ISSUER, jwtConfig.getIssuer());
        // 生成私钥
        JWTSigner jwtSigner = JWTSignerUtil.hs256(jwtConfig.getKeyBytes());
        // 生成token
        return JWTUtil.createToken(payload, jwtSigner);
    }
}
