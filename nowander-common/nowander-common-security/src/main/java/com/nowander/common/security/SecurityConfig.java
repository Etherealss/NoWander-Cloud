package com.nowander.common.security;

import com.nowander.common.core.enums.ApiInfo;
import com.nowander.common.core.exception.service.TokenException;
import com.nowander.common.core.pojo.Msg;
import com.nowander.common.core.utils.ResponseUtil;
import com.nowander.common.security.anonymous.RequestMethodEnum;
import com.nowander.common.security.anonymous.annotation.AnonymousUrlUtil;
import com.nowander.common.security.token.MyJwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.firewall.HttpFirewall;
import org.springframework.security.web.firewall.StrictHttpFirewall;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.Map;
import java.util.Set;

/**
 * @author wtk
 * @description
 * @date 2021-09-05
 */
@Slf4j
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final RequestMappingHandlerMapping handlerMapping;
    private final MyJwtAuthenticationFilter jwtAuthenticationFilter;

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        // 获取可以匿名访问的URL
        Map<String, Set<String>> anonymousUrls = AnonymousUrlUtil.getAnonymousUrl(handlerMapping);
        // 将登录框关闭
        httpSecurity.formLogin().disable();
        httpSecurity
                .sessionManagement()
                // 基于token，所以不需要session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                // —————————————— 认证失败处理类  ——————————————
                .and()
                .exceptionHandling()
                .accessDeniedHandler(getAccessDeniedHandler())
                .authenticationEntryPoint(getAuthenticationEntryPoint())

                //  —————————————— 权限校验  ——————————————
                // 设置哪些路径可以直接访问，不需要认证
                .and()
                .authorizeRequests()
                .antMatchers("/swagger-ui.html",
                        "/swagger-resources/**",
                        "/webjars/**",
                        "/*/api-docs",
                        "/avatar/**",
                        "/file/**",
                        "/druid/**"
                ).permitAll()
                // 放行OPTIONS请求
                .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                // 自定义匿名访问所有url放行：允许匿名和带Token访问，细腻化到每个 Request 类型
                .antMatchers(HttpMethod.GET, anonymousUrls.get(RequestMethodEnum.GET.getType()).toArray(new String[0])).permitAll()
                .antMatchers(HttpMethod.POST, anonymousUrls.get(RequestMethodEnum.POST.getType()).toArray(new String[0])).permitAll()
                .antMatchers(HttpMethod.PUT, anonymousUrls.get(RequestMethodEnum.PUT.getType()).toArray(new String[0])).permitAll()
                .antMatchers(HttpMethod.DELETE, anonymousUrls.get(RequestMethodEnum.DELETE.getType()).toArray(new String[0])).permitAll()
                .antMatchers(HttpMethod.PATCH, anonymousUrls.get(RequestMethodEnum.PATCH.getType()).toArray(new String[0])).permitAll()
                // 所有类型的接口都放行
                .antMatchers(anonymousUrls.get(RequestMethodEnum.ALL.getType()).toArray(new String[0])).permitAll()
                // 除上面外的所有请求全部需要鉴权认证
                .anyRequest().authenticated()
//                .anyRequest().permitAll()

                // 关闭CSRF防护
                .and()
                .csrf().disable()

                // 跨域。不知道实际作用，先注释掉
//                .cors().disable()

                // 添加token过滤器
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
        ;
    }


    @Override
    public void configure(WebSecurity web) {
        // 前端静态文件跳过Security过滤链
        web.ignoring().antMatchers(HttpMethod.GET,
                "/index.html",
                "/lib/**",
                "/toastr/**",
                "/img/**",
                "/**/*.html",
                "/**/*.css",
                "/**/*.js",
                "/webSocket/**",
                "/config/**"
        );
    }

    @Bean
    public AuthenticationEntryPoint getAuthenticationEntryPoint() {
        return (request, response, authException) -> {
            Msg<Object> msg;
            Object e = request.getAttribute("tokenException");
            if (e == null) {
                log.debug("认证失败：{}", authException.getMessage());
                msg = new Msg<>(ApiInfo.AUTHORIZATION_FAILED);
                msg.setMessage(msg.getMessage() + authException.getMessage());
                response.setStatus(HttpStatus.BAD_REQUEST.value());
            } else {
                TokenException tokenException = (TokenException) e;
                log.debug("认证失败：{}", tokenException.getMessage());
                msg = new Msg<>();
                msg.setCode(tokenException.getCode());
                msg.setMessage(tokenException.getMessage());
                response.setStatus(HttpStatus.BAD_REQUEST.value());
            }
            ResponseUtil.send(response, msg);
        };
    }

    @Bean
    public AccessDeniedHandler getAccessDeniedHandler() {
        return (request, response, authException) -> {
            log.debug("拒绝访问：{}", authException.getMessage());
            Msg<Object> msg = new Msg<>(ApiInfo.FORBIDDEN_REQUEST);
            msg.setMessage(msg.getMessage() + authException.getMessage());
            response.setStatus(HttpStatus.BAD_REQUEST.value());
            ResponseUtil.send(response, msg);
        };
    }

    /**
     * 开启矩阵变量
     * @return
     */
    @Bean
    public HttpFirewall allowUrlSemicolonHttpFirewall() {
        StrictHttpFirewall firewall = new StrictHttpFirewall();
        firewall.setAllowSemicolon(true);
        return firewall;
    }
}
