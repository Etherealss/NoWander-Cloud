package com.nowander.common.exception.service;

import com.nowander.common.enums.ApiInfo;
import com.nowander.common.exception.BaseException;

/**
 * @author wtk
 * @date 2022-02-04
 */
public class NotAuthorException extends BaseException {
    public NotAuthorException() {
        super(ApiInfo.NOT_AUTHOR);
    }

    public NotAuthorException(String message) {
        super(ApiInfo.NOT_AUTHOR, message);
    }
}
