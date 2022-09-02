package com.nowander.common.core.exception.service;

import com.nowander.common.core.pojo.Msg;
import feign.Request;
import feign.codec.DecodeException;
import lombok.Getter;
import lombok.ToString;

/**
 * @author wtk
 * @date 2022-09-02
 */
@Getter
@ToString
public class ServiceFiegnException extends DecodeException {
    private final Msg<?> msg;

    public ServiceFiegnException(Msg<?> msg, int status, Request request) {
        super(status, msg.getMessage(), request);
        this.msg = msg;
    }
}
