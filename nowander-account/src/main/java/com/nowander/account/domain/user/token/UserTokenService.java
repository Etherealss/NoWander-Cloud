package com.nowander.account.domain.user.token;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nowander.account.domain.user.SysUser;
import com.nowander.account.domain.user.UserMapper;
import com.nowander.account.domain.user.token.login.LoginAuthenticatorContext;
import com.nowander.account.domain.user.token.login.UserLoginCommand;
import com.nowander.common.core.exception.rest.ParamErrorException;
import com.nowander.common.security.UserCredential;
import com.nowander.common.security.service.ITokenService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

/**
 * @author wtk
 * @date 2022-08-30
 */
@Service
@Slf4j
@AllArgsConstructor
public class UserTokenService extends ServiceImpl<UserMapper, SysUser> {

    private final UserMapper userMapper;
    private final RedisTemplate<String, Object> redisTemplate;
    private final ITokenService tokenService;
    private final LoginAuthenticatorContext loginAuthenticatorContext;

    public UserCredential login(UserLoginCommand userLoginCommand) {
        String username = userLoginCommand.getUsername();
        SysUser user = loginAuthenticatorContext.doLogin(userLoginCommand);
        if (!user.getPassword().equals(userLoginCommand.getPassword())) {
            throw new ParamErrorException("登录密码错误");
        }
        // TODO 用户权限
        Set<String> permissions = new HashSet<>();
        Set<String> roles = new HashSet<>();
        UserCredential userCredential = new UserCredential(
                user.getId(),
                user.getUsername(),
                permissions,
                roles
        );
        return userCredential;
    }
}
