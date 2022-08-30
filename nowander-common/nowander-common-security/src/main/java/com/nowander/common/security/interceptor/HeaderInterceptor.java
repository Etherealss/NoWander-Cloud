package com.nowander.common.security.interceptor;

import com.nowander.common.security.SecurityContextHolder;
import com.nowander.common.security.UserCredential;
import com.nowander.common.security.config.TokenConfig;
import com.nowander.common.security.exception.TokenException;
import com.nowander.common.security.service.ITokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.AsyncHandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 获取 Token 并将相关信息存入 SecurityContextHolder 中
 * @author wtk
 * @date 2022-08-30
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class HeaderInterceptor implements AsyncHandlerInterceptor {

    private final ITokenService tokenService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }

        String token = request.getHeader(TokenConfig.HEADER_TOKEN);
        if (StringUtils.hasText(token)) {
            try {
                UserCredential userCredential = tokenService.versifyToken(token);
                SecurityContextHolder.set(userCredential);
            } catch (TokenException ignored) {}
        }
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
        SecurityContextHolder.remove();
    }
}
