package com.nowander.common.security.exception;

import com.nowander.common.core.enums.ApiInfo;
import com.nowander.common.core.exception.ApiInfoGetter;
import com.nowander.common.core.exception.BaseException;

/**
 * @author wtk
 * @date 2022-08-30
 */
public class NotRoleException extends BaseException {
    public NotRoleException(ApiInfoGetter apiInfo) {
        super(apiInfo);
    }

    public NotRoleException(ApiInfoGetter apiInfo, String message) {
        super(apiInfo, message);
    }

    public NotRoleException(String... permissions) {
        this(ApiInfo.NOT_PERMISSION, "缺少角色身份：" + String.join(",", permissions));
    }
}
