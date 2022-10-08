package com.nowander.common.core.feign;

import cn.hutool.json.JSONNull;
import cn.hutool.json.JSONUtil;
import com.nowander.common.core.exception.service.ServiceFiegnException;
import com.nowander.common.core.pojo.Msg;
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
            Msg<?> result = JSONUtil.toBean(bodyStr, Msg.class);
            // hutool 会将空字段填充 JSONNull 对象，会与 Spring 默认的 jackson 冲突。导致异常
            // 最好的办法是不用 Hutool，但暂时没找到合适的替换方式
            if (result.getData() instanceof JSONNull) {
                result.setData(null);
            }
            return new ServiceFiegnException(result, response.status(), response.request());
        } catch (Exception ignored) {
            return defaultDecoder.decode(methodKey, response);
        }
    }
}
