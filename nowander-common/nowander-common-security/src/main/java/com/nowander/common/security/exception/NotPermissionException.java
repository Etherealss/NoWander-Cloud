package com.nowander.common.security.exception;

import com.nowander.common.core.enums.ApiInfo;
import com.nowander.common.core.exception.ApiInfoGetter;
import com.nowander.common.core.exception.service.AuthenticationException;

/**
 * @author wtk
 * @date 2022-08-30
 */
public class NotPermissionException extends AuthenticationException {
    public NotPermissionException(ApiInfoGetter apiInfo) {
        super(apiInfo);
    }

    public NotPermissionException(ApiInfoGetter apiInfo, String message) {
        super(apiInfo, message);
    }

    public NotPermissionException(String... permissions) {
        this(ApiInfo.NOT_PERMISSION, "缺少权限：" + String.join(",", permissions));
    }
}
