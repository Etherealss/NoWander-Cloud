package com.nowander.common.security.aspect;

import com.nowander.common.core.exception.service.AuthenticationException;
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
    public static final String POINTCUR_REQUEST_MAPPING = ""
            + "@annotation(org.springframework.web.bind.annotation.GetMapping) || "
            + "@annotation(org.springframework.web.bind.annotation.PostMapping) || "
            + "@annotation(org.springframework.web.bind.annotation.DeleteMapping) || "
            + "@annotation(org.springframework.web.bind.annotation.PutMapping) || "
            + "@annotation(org.springframework.web.bind.annotation.RequestMapping)";

    public static final String POINTCUR_CONTROLLER = "execution(* com.nowander..controller..*.*(..))";
    /**
     * AOP签名
     */
    @Pointcut(POINTCUR_CONTROLLER)
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

        if (anonymousAccess != null) {
            log.trace("匿名访问接口，无需检验");
            return;
        }
        try {
            authService.requireToken();

            // 校验 @RequiresRoles 注解
            if (requiresRoles != null) {
                authService.checkRole(requiresRoles);
            }

            // 校验 @RequiresPermissions 注解
            if (requiresPermissions != null) {
                authService.checkPermi(requiresPermissions);
            }
        } catch (AuthenticationException e) {
            log.info("请求认证不通过：{}", e.getMessage());
            throw e;
        }
    }
}
