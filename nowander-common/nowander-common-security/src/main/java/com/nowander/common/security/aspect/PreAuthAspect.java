package com.nowander.common.security.aspect;

import com.nowander.common.security.annotation.AnonymousAccess;
import com.nowander.common.security.annotation.RequiresPermissions;
import com.nowander.common.security.annotation.RequiresRoles;
import com.nowander.common.security.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * @author wtk
 * @date 2022-08-30
 */
@Aspect
@Component
@RequiredArgsConstructor
@Slf4j
public class PreAuthAspect {
    private final AuthService authService;
    /**
     * 定义AOP签名 (切入所有使用鉴权注解的方法)
     */
    public static final String POINTCUT_SIGN = " @annotation(com.ruoyi.common.security.annotation.RequiresLogin) || "
            + "@annotation(com.ruoyi.common.security.annotation.RequiresPermissions) || "
            + "@annotation(com.ruoyi.common.security.annotation.RequiresRoles)";

    /**
     * AOP签名
     */
    @Pointcut(POINTCUT_SIGN)
    public void pointcut() {
    }

    /**
     * @param joinPoint 切面对象
     * @return 底层方法执行后的返回值
     * @throws Throwable 底层方法抛出的异常
     */
    @Around("pointcut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        // 注解鉴权
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        checkMethodAnnotation(signature.getMethod());
        // 执行原有逻辑
        return joinPoint.proceed();
    }

    /**
     * 对一个Method对象进行注解检查
     */
    public void checkMethodAnnotation(Method method) {
        AnonymousAccess anonymousAccess = method.getAnnotation(AnonymousAccess.class);
        RequiresRoles requiresRoles = method.getAnnotation(RequiresRoles.class);
        RequiresPermissions requiresPermissions = method.getAnnotation(RequiresPermissions.class);

        if (anonymousAccess == null) {
            log.trace("匿名访问接口，无需检验");
            return;
        }
        authService.requireToken();

        // 校验 @RequiresRoles 注解
        if (requiresRoles != null) {
            authService.checkRole(requiresRoles);
        }

        // 校验 @RequiresPermissions 注解
        if (requiresPermissions != null) {
            authService.checkPermi(requiresPermissions);
        }
    }
}
