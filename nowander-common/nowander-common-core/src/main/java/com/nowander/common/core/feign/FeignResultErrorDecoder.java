package com.nowander.common.core.feign;

import com.nowander.common.core.exception.service.ServiceFiegnException;
import com.nowander.common.core.pojo.Msg;
import com.nowander.common.core.utils.JsonUtil;
import feign.Response;
import feign.Util;
import feign.codec.ErrorDecoder;
import lombok.RequiredArgsConstructor;

/**
 * @author wtk
 * @date 2022-10-08
 */
@RequiredArgsConstructor
public class FeignResultErrorDecoder implements ErrorDecoder {

    private final ErrorDecoder.Default defaultDecoder;

    @Override
    public Exception decode(String methodKey, Response response) {
        if (response.body() == null) {
            return defaultDecoder.decode(methodKey, response);
        }
        try {
            //对结果进行转换
            String bodyStr = Util.toString(response.body().asReader(Util.UTF_8));
            // hutool 会将空字段填充 JSONNull 对象，会与 Spring 默认的 jackson 冲突。导致异常
            // 解决办法是不用 Hutool，改用 jackson
            Msg<?> result = JsonUtil.toObject(bodyStr, Msg.class);
            if (result.getCode() == 0) {
                // bodyStr 读取出来的非 Msg 对象
                return defaultDecoder.decode(methodKey, response);
            }
            if (result.getData() == null) {
                result.setData(null);
            }
            return new ServiceFiegnException(result, response.status(), response.request());
        } catch (Exception ignored) {
            return defaultDecoder.decode(methodKey, response);
        }
    }
}
