package com.nowander.common.security.aspect;

import java.lang.reflect.Method;

/**
 * 可以通过重写该接口修改验证方案
 * @author wtk
 * @date 2022-10-07
 */
public interface IPreAuthHandler {

    boolean checkNeedAuth(Method method);

    /**
     * 对一个Method对象进行注解检查
     * 如果 auth 不通过，抛出 {@code AuthenticationException} 异常以拦截请求
     */
    void doAuth(Method method);
}
