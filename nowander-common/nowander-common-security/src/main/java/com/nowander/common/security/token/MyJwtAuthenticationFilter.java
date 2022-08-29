package com.nowander.common.security.token;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.nowander.common.core.exception.service.TokenException;
import com.nowander.common.security.UserCredential;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 过滤已登陆（有token）和未登录的用户
 * @author wang tengkun
 * @date 2022/2/23
 */
@Component
@Slf4j
@AllArgsConstructor
public class MyJwtAuthenticationFilter extends OncePerRequestFilter {

    private UserDetailsService userDetailsService;

    private JwtConfig jwtConfig;

    private AuthTokenService tokenService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = request.getHeader(jwtConfig.getTokenHeader());
        log.debug("请求的token：{}", token);
        // 校验token是否为有效token
        if (!StrUtil.isBlank(token) && ObjectUtil.isNull(SecurityContextHolder.getContext().getAuthentication())) {
            // token有效，但SpringSecurity上下文没有User对象，则验证token，不通过时直接抛异常，通过了跳过登录
            UserCredential sysUser;
            try {
                sysUser = tokenService.requireUserByToken(token);
            } catch (TokenException e) {
                // 直接抛出异常会被Security捕获，获取不到。所以直接放在request里
                request.setAttribute("tokenException", e);
                throw e;
            }
            // token有效，跳过登录
            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(sysUser, null, sysUser.getAuthorities());
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            //将authentication放入SecurityContextHolder中
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        filterChain.doFilter(request, response);
    }
}