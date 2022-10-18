package com.nowander.common.core.interceptor.pathvariable;

import java.lang.annotation.*;

/**
 * 路径参数存在性检查
 * @see PathVariableVerifyInterceptor
 * @author wtk
 * @date 2022-10-18
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface PathVariableValidated {

}
