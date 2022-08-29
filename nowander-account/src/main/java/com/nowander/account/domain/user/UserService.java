package com.nowander.account.domain.user;

import cn.hutool.core.lang.Assert;
import cn.hutool.json.JSONObject;
import cn.hutool.jwt.JWT;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nowander.common.security.RedisKeyPrefix;
import com.nowander.common.security.token.JwtConfig;
import com.nowander.common.security.token.TokenUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @author wtk
 * @since 2022-01-05
 */
@Service
@Slf4j
@AllArgsConstructor
public class UserService extends ServiceImpl<UserMapper, SysUser> {

    private final UserMapper userMapper;
    private final RedisTemplate<String, Object> redisTemplate;
    private final JwtConfig jwtConfig;

    public UserBriefDTO getBriefById(Integer userId) {
        SysUser user = userMapper.selectById(userId);
        return user.convert(UserBriefDTO.class);
    }

    public List<UserBriefDTO> getBatchBriefsByIds(Collection<Integer> userIds) {
        List<SysUser> users = userMapper.selectBatchIds(userIds);
        return users.stream()
                .map(user -> user.convert(UserBriefDTO.class))
                .collect(Collectors.toList());
    }

    public UserDetailDTO getDetailById(Integer userId) {
        SysUser user = userMapper.selectById(userId);
        return user.convert(UserDetailDTO.class);
    }

    public void logout(HttpServletRequest request) {
        JSONObject claims = TokenUtil.parse(request.getHeader(jwtConfig.getTokenHeader()));
        Long exp = claims.get(JWT.EXPIRES_AT, Long.class);
        String username = claims.get("username", String.class);
        Assert.notNull(exp);
        Assert.notBlank(username);
        // 加入黑名单，事token失效（严格来说，黑名单是username的黑名单，用户必须再次使用账号密码登录才能才黑名单移除）
        redisTemplate.opsForValue().set(RedisKeyPrefix.USER_TOKEN_BLACKLIST + username, "",
                exp - System.currentTimeMillis(), TimeUnit.MILLISECONDS);
        // 清空
        SecurityContextHolder.getContext().setAuthentication(null);
    }

    // TODO 头像、文件服务
}
